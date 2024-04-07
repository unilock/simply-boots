package io.github.alabasteralibi.simplyboots.mixins;

import com.llamalad7.mixinextras.sugar.Local;
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
    @Inject(method = "computeNext", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/BlockView;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;"), cancellable = true)
    private void computeNext(CallbackInfoReturnable<T> cir, @Local BlockView blockView) {
        if (this.entity == null || !(this.entity instanceof LivingEntity living)) {
            return;
        }
        if (living.getVelocity().getY() >= 0 || living.isTouchingWater() || living.isInLava() || living.isSneaking()) {
            return;
        }
        if (!SimplyBootsHelpers.wearingBoots(living, SimplyBootsTags.FLUID_WALKING_BOOTS)) {
            return;
        }

        FluidState fluidState = blockView.getFluidState(this.pos);
        if (fluidState.isIn(FluidTags.LAVA) && !SimplyBootsHelpers.wearingBoots(living, SimplyBootsTags.HOT_FLUID_WALKING_BOOTS)) {
            return;
        }
        if (fluidState.isEmpty() || !blockView.getFluidState(this.pos.up()).isEmpty()) {
            return;
        }

        if ((living.getY() + (living.isOnGround() ? living.getStepHeight() : 0)) - (this.pos.getY() + fluidState.getHeight()) >= -1E-6) { // Epsilon
            VoxelShape voxelShape = fluidState.getShape(blockView, this.pos).offset(this.pos.getX(), this.pos.getY(), this.pos.getZ());
            if (VoxelShapes.matchesAnywhere(voxelShape, this.boxShape, BooleanBiFunction.AND)) {
                living.fallDistance = 0.0F;
                cir.setReturnValue(this.resultFunction.apply(this.pos, voxelShape));
            }
        }
    }
}
