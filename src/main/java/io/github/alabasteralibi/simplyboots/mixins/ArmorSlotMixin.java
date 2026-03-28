package io.github.alabasteralibi.simplyboots.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.alabasteralibi.simplyboots.SimplyBootsHelpers;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net/minecraft/screen/slot/ArmorSlot")
public class ArmorSlotMixin {
	@Shadow
	@Final
	private LivingEntity entity;

	@ModifyReturnValue(method = "canInsert(Lnet/minecraft/item/ItemStack;)Z", at = @At("RETURN"))
	private boolean preventDoubleBootsEquip(boolean original, ItemStack stack) {
		if (stack.isIn(SimplyBootsTags.BOOTS) && SimplyBootsHelpers.wearingTrinketBoots(entity, SimplyBootsTags.BOOTS)) {
			return false;
		} else {
			return original;
		}
	}
}
