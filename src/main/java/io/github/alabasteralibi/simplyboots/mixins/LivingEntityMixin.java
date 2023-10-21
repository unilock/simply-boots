package io.github.alabasteralibi.simplyboots.mixins;

import io.github.alabasteralibi.simplyboots.registry.SimplyBootsTags;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Unique
    private final LivingEntity entity = (LivingEntity) (Object) this;

    // Removes slipperiness on ice when having Frostspark Boots.
    @ModifyVariable(method = "travel", at = @At(value = "STORE"), index = 7)
    private float negateSlipperinessIfWearingFrostsparkBoots(float f) {
        if (f != 0.6F && entity.getEquippedStack(EquipmentSlot.FEET).isIn(SimplyBootsTags.ICE_SKATE_BOOTS)) {
            return 0.6F;
        }
        return f;
    }

    // Re-adds increased jump speed when jumping off ice while having Frostspark Boots.
    // In testing, this is nearly identical to how it normally boosts jumping.
    @ModifyConstant(method = "jump", constant = @Constant(floatValue = 0.2F))
    private float increaseJumpSpeedIfWearingFrostsparkBoots(float f) {
        BlockPos velocityAffectingPos = new BlockPos((int) entity.getX(), (int) (entity.getBoundingBox().minY - 0.5), (int) entity.getZ());
        float slipperiness = entity.getWorld().getBlockState(velocityAffectingPos).getBlock().getSlipperiness();
        if (entity.getEquippedStack(EquipmentSlot.FEET).isIn(SimplyBootsTags.ICE_SKATE_BOOTS) && slipperiness != 0.6F) {
            return f + (slipperiness / 8);
        }
        return f;
    }
}
