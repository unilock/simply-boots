package io.github.alabasteralibi.simplyboots.mixins;

import com.google.common.collect.ImmutableList;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Entity.class)
public abstract class EntityMixin {
    // Makes vanilla perform the normal collisions in cases where it would normally perform the more broken single axis ones.
    // May break something, but I haven't noticed anything yet.
    @Inject(method = "adjustMovementForCollisions(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Box;Lnet/minecraft/world/World;Ljava/util/List;)Lnet/minecraft/util/math/Vec3d;", at = @At(value = "HEAD"), cancellable = true)
    private static void fixSingleAxisCollisions(Entity entity, Vec3d movement, Box entityBoundingBox, World world, List<VoxelShape> collisions, CallbackInfoReturnable<Vec3d> cir) {
        ImmutableList.Builder<VoxelShape> builder = ImmutableList.builder();
        builder.addAll(collisions).addAll(world.getBlockCollisions(entity, entityBoundingBox.stretch(movement)));
        ImmutableList<VoxelShape> collisions2 = builder.build();

        double d = movement.x;
        double e = movement.y;
        double f = movement.z;
        if (e != 0.0) {
            e = VoxelShapes.calculateMaxOffset(Direction.Axis.Y, entityBoundingBox, collisions2, e);
            if (e != 0.0) {
                entityBoundingBox = entityBoundingBox.offset(0.0, e, 0.0);
            }
        }

        boolean bl = Math.abs(d) < Math.abs(f);
        if (bl && f != 0.0) {
            f = VoxelShapes.calculateMaxOffset(Direction.Axis.Z, entityBoundingBox, collisions2, f);
            if (f != 0.0) {
                entityBoundingBox = entityBoundingBox.offset(0.0, 0.0, f);
            }
        }

        if (d != 0.0) {
            d = VoxelShapes.calculateMaxOffset(Direction.Axis.X, entityBoundingBox, collisions2, d);
            if (!bl && d != 0.0) {
                entityBoundingBox = entityBoundingBox.offset(d, 0.0, 0.0);
            }
        }

        if (!bl && f != 0.0) {
            f = VoxelShapes.calculateMaxOffset(Direction.Axis.Z, entityBoundingBox, collisions2, f);
        }

        cir.setReturnValue(new Vec3d(d, e, f));
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

