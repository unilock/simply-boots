package io.github.alabasteralibi.simplyboots.mixins;

import io.github.alabasteralibi.simplyboots.components.BootComponents;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsTags;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    private final PlayerEntity player = (PlayerEntity) (Object) this;

    @Inject(method = "tick", at = @At(value = "TAIL"))
    private void updateSpecialBootEffects(CallbackInfo ci) {
        ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);

        // Updates fire immunity
        if (boots.isIn(SimplyBootsTags.FIRE_RESISTANT_BOOTS)) {
            player.extinguish();
        }
    }

    @Inject(method = "damage", at = @At(value = "HEAD"), cancellable = true)
    private void cancelLavaAndFireDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source == DamageSource.LAVA && BootComponents.LAVA_BOOTS.get(player).getValue() > 0 && player.getEquippedStack(EquipmentSlot.FEET).isIn(SimplyBootsTags.HOT_FLUID_WALKING_BOOTS)) {
            cir.setReturnValue(false);
        }
        if (source != DamageSource.LAVA && source.isFire() && player.getEquippedStack(EquipmentSlot.FEET).isIn(SimplyBootsTags.FIRE_RESISTANT_BOOTS)) {
            cir.setReturnValue(false);
        }
    }
}

