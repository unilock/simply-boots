package io.github.alabasteralibi.simplyboots.mixins;

import io.github.alabasteralibi.simplyboots.registry.SimplyBootsAttributes;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    LivingEntity entity = (LivingEntity) (Object) this;

    // TODO: Add rocket boot effects, frostspark effects (speed boost on ice with no sliding)
    @Inject(method = "tick", at = @At(value = "TAIL"))
    private void updateSpecialBootEffects(CallbackInfo ci) {
        Item boots = entity.getEquippedStack(EquipmentSlot.FEET).getItem();

        // Updates fire and lava immunity
        if (boots == SimplyBootsItems.LAVA_WADERS) {
            if (!entity.isInLava()) {
                entity.extinguish();
                StatusEffectInstance entityFireResist = entity.getStatusEffect(StatusEffects.FIRE_RESISTANCE);
                if (entityFireResist == null || entityFireResist.getDuration() < 100) {
                    entity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 100, 0, false, false, true));
                }
            }
        }
    }

    @Inject(method = "createLivingAttributes", at = @At(value = "RETURN"))
    private static void addStepHeightAttribute(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        cir.getReturnValue().add(SimplyBootsAttributes.GENERIC_STEP_HEIGHT);
    }
}

