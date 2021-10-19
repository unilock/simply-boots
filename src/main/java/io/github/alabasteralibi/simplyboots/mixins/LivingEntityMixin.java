package io.github.alabasteralibi.simplyboots.mixins;

import io.github.alabasteralibi.simplyboots.registry.SimplyBootsAttributes;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsItems;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsTags;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    private final LivingEntity entity = (LivingEntity) (Object) this;
    private int lavaImmunityTicks = 0;

    // TODO: Add rocket boot effects, frostspark effects (speed boost on ice with no sliding)
    @Inject(method = "tick", at = @At(value = "TAIL"))
    private void updateSpecialBootEffects(CallbackInfo ci) {
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);

        // Updates fire immunity
        if (boots.isIn(SimplyBootsTags.FIRE_RESISTANT_BOOTS)) {
            entity.extinguish();
        }

        // Updates lava immunity TODO: Add a HUD feature for lava immunity time
        if (entity.isInLava() && boots.isIn(SimplyBootsTags.HOT_FLUID_WALKING_BOOTS)) {
            if (lavaImmunityTicks > 0) {
                lavaImmunityTicks--;
            }
        } else if (lavaImmunityTicks < 100) {
            lavaImmunityTicks++;
        }
    }

    @Inject(method = "damage", at = @At(value = "HEAD"), cancellable = true)
    private void cancelLavaAndFireDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source == DamageSource.LAVA && lavaImmunityTicks > 0 && entity.getEquippedStack(EquipmentSlot.FEET).isIn(SimplyBootsTags.HOT_FLUID_WALKING_BOOTS)) {
            cir.setReturnValue(false);
        }
        if (source != DamageSource.LAVA && source.isFire() && entity.getEquippedStack(EquipmentSlot.FEET).isIn(SimplyBootsTags.FIRE_RESISTANT_BOOTS)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "createLivingAttributes", at = @At(value = "RETURN"))
    private static void addStepHeightAttribute(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        cir.getReturnValue().add(SimplyBootsAttributes.GENERIC_STEP_HEIGHT);
    }
}

