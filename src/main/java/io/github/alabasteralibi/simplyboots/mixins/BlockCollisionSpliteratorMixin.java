package io.github.alabasteralibi.simplyboots.mixins;

import io.github.alabasteralibi.simplyboots.SimplyBootsHelpers;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockCollisionSpliterator;
import net.minecraft.world.BlockView;
import net.minecraft.world.CollisionView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.function.BiFunction;

@Mixin(BlockCollisionSpliterator.class)
public class BlockCollisionSpliteratorMixin<T> {
    @Unique
    private Entity entity;

    @Shadow
    @Final
    private BlockPos.Mutable pos;

    @Shadow
    @Final
    private VoxelShape boxShape;

    @Shadow
    @Final
    private BiFunction<BlockPos.Mutable, VoxelShape, T> resultFunction;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(CollisionView world, Entity entity, Box box, boolean forEntity, BiFunction<BlockPos.Mutable, VoxelShape, ?> resultFunction, CallbackInfo ci) {
        this.entity = entity;
    }

    // Intercepts the vanilla code for colliding with blocks, making it treat fluids as solid in the right circumstances.
    @Inject(method = "computeNext", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/BlockView;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void computeNext(CallbackInfoReturnable<T> cir, int i, int j, int k, int l, BlockView blockView) {
        if (this.entity == null || !(this.entity instanceof LivingEntity entity)) {
            return;
        }
        if (entity.getVelocity().getY() >= 0 || entity.updateMovementInFluid(FluidTags.LAVA, 0) || entity.updateMovementInFluid(FluidTags.WATER, 0) || entity.isSneaking()) {
            return;
        }
        if (!SimplyBootsHelpers.wearingBoots(entity, SimplyBootsTags.FLUID_WALKING_BOOTS)) {
            return;
        }

        FluidState fluidState = blockView.getFluidState(new BlockPos(i, j, k));
        if (fluidState.isIn(FluidTags.LAVA) && !SimplyBootsHelpers.wearingBoots(entity, SimplyBootsTags.HOT_FLUID_WALKING_BOOTS)) {
            return;
        }
        if (fluidState.isEmpty() || !blockView.getFluidState(new BlockPos(i, j + 1, k)).isEmpty()) {
            return;
        }

        if ((entity.getY() + (entity.isOnGround() ? entity.getStepHeight() : 0)) - (j + fluidState.getHeight()) >= -1E-6) { // Epsilon
            VoxelShape voxelShape = fluidState.getShape(blockView, this.pos).offset(i, j, k);
            if (VoxelShapes.matchesAnywhere(voxelShape, this.boxShape, BooleanBiFunction.AND)) {
                entity.fallDistance = 0.0F;
                cir.setReturnValue(this.resultFunction.apply(this.pos, voxelShape));
            }
        }
    }
}
