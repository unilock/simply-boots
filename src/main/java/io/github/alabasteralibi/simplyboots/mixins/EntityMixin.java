package io.github.alabasteralibi.simplyboots.mixins;

import io.github.alabasteralibi.simplyboots.registry.SimplyBootsAttributes;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsItems;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.ReusableStream;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.stream.Stream;

@Mixin(Entity.class)
public abstract class EntityMixin {

    // Makes vanilla perform the normal collisions in cases where it would normally perform the more broken single axis
    // ones. May break something, but I haven't noticed anything yet.
    @Inject(method = "adjustMovementForCollisions(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Box;Lnet/minecraft/world/World;Lnet/minecraft/block/ShapeContext;Lnet/minecraft/util/collection/ReusableStream;)Lnet/minecraft/util/math/Vec3d;",
            at = @At(value = "HEAD"), cancellable = true)
    private static void fixSingleAxisCollisions(@Nullable Entity entity, Vec3d movement, Box entityBoundingBox, World world, ShapeContext context, ReusableStream<VoxelShape> collisions, CallbackInfoReturnable<Vec3d> cir) {
        ReusableStream<VoxelShape> reusableStream = new ReusableStream(Stream.concat(collisions.stream(), world.getBlockCollisions(entity, entityBoundingBox.stretch(movement))));
        double d = movement.x;
        double e = movement.y;
        double f = movement.z;
        if (e != 0.0D) {
            e = VoxelShapes.calculateMaxOffset(Direction.Axis.Y, entityBoundingBox, reusableStream.stream(), e);
            if (e != 0.0D) {
                entityBoundingBox = entityBoundingBox.offset(0.0D, e, 0.0D);
            }
        }

        boolean bl = Math.abs(d) < Math.abs(f);
        if (bl && f != 0.0D) {
            f = VoxelShapes.calculateMaxOffset(Direction.Axis.Z, entityBoundingBox, reusableStream.stream(), f);
            if (f != 0.0D) {
                entityBoundingBox = entityBoundingBox.offset(0.0D, 0.0D, f);
            }
        }

        if (d != 0.0D) {
            d = VoxelShapes.calculateMaxOffset(Direction.Axis.X, entityBoundingBox, reusableStream.stream(), d);
            if (!bl && d != 0.0D) {
                entityBoundingBox = entityBoundingBox.offset(d, 0.0D, 0.0D);
            }
        }

        if (!bl && f != 0.0D) {
            f = VoxelShapes.calculateMaxOffset(Direction.Axis.Z, entityBoundingBox, reusableStream.stream(), f);
        }

        cir.setReturnValue(new Vec3d(d, e, f));
    }

    // Makes the step height attribute work.
    @Redirect(method = "adjustMovementForCollisions(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;",
            at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lnet/minecraft/entity/Entity;stepHeight:F"))
    private float fixThatGarbage(Entity entity) {
        if (entity instanceof LivingEntity) {
            return SimplyBootsAttributes.getStepHeight((LivingEntity) entity);
        } else {
            return entity.stepHeight;
        }
    }

    // Allows spectre boots to upgrade into lightning boots, and makes lightning boots invincible to lightning (but not fire!)
    @Inject(method = "onStruckByLightning", at = @At(value = "HEAD"), cancellable = true)
    private void upgradeSpectreBootsOnLightningStrike(ServerWorld world, LightningEntity lightning, CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        if (entity instanceof ItemEntity item) {
            if (item.getStack().getItem() == SimplyBootsItems.SPECTRE_BOOTS) {
                ItemStack newStack = new ItemStack(SimplyBootsItems.LIGHTNING_BOOTS);
                newStack.setNbt(item.getStack().getNbt());
                world.spawnEntity(new ItemEntity(world, item.getX(), item.getY(), item.getZ(), newStack));
                item.remove(Entity.RemovalReason.DISCARDED);
                ci.cancel();
            }
            if (item.getStack().getItem() == SimplyBootsItems.LIGHTNING_BOOTS) {
                ci.cancel();
            }
        }
    }
}

