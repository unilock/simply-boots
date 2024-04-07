package io.github.alabasteralibi.simplyboots.mixins;

import io.github.alabasteralibi.simplyboots.registry.SimplyBootsItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
    // Allows spectre boots to upgrade into lightning boots, and makes lightning boots invincible to lightning (but not fire!)
    @Inject(method = "onStruckByLightning", at = @At(value = "HEAD"), cancellable = true)
    private void upgradeSpectreBootsOnLightningStrike(ServerWorld world, LightningEntity lightning, CallbackInfo ci) {
        if ((Object) this instanceof ItemEntity item) {
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

