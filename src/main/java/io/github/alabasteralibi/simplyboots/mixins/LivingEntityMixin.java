package io.github.alabasteralibi.simplyboots.mixins;

import io.github.alabasteralibi.simplyboots.registry.SimplyBootsAttributes;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsTags;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    private final LivingEntity entity = (LivingEntity) (Object) this;

    @Inject(method = "createLivingAttributes", at = @At(value = "RETURN"))
    private static void addStepHeightAttribute(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        cir.getReturnValue().add(SimplyBootsAttributes.GENERIC_STEP_HEIGHT);
    }

    // Removes slipperiness on ice when having Frostspark Boots.
    @ModifyVariable(method = "travel", at = @At(value = "STORE"), index = 7)
    private float negateSlipperinessIfWearingFrostsparkBoots(float f) {
        if (f != 0.6F && entity.getEquippedStack(EquipmentSlot.FEET).isIn(SimplyBootsTags.ICE_SKATE_BOOTS)) {
            return 0.6F;
        }
        return f;
    }

    // Readds increased jump speed when jumping off ice while having Frostspark Boots. In testing, this is nearly
    // identical to how it normally boosts jumping.
    @ModifyConstant(method = "jump", constant = @Constant(floatValue = 0.2F))
    private float increaseJumpSpeedIfWearingFrostsparkBoots(float f) {
        BlockPos velocityAffectingPos = new BlockPos(entity.getX(), entity.getBoundingBox().minY - 0.5000001D, entity.getZ());
        float slipperiness = entity.world.getBlockState(velocityAffectingPos).getBlock().getSlipperiness();
        if (entity.getEquippedStack(EquipmentSlot.FEET).isIn(SimplyBootsTags.ICE_SKATE_BOOTS) && slipperiness != 0.6F) {
            return f + (slipperiness / 8);
        }
        return f;
    }
}
