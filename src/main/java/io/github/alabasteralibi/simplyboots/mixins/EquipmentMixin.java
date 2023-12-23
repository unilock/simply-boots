package io.github.alabasteralibi.simplyboots.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.alabasteralibi.simplyboots.SimplyBootsHelpers;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsTags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Equipment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Equipment.class)
public interface EquipmentMixin {
    @Inject(method = "equipAndSwap", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/player/PlayerEntity;getEquippedStack(Lnet/minecraft/entity/EquipmentSlot;)Lnet/minecraft/item/ItemStack;"), cancellable = true)
    private void stopDoubleEquip(Item item, World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir, @Local(ordinal = 0) ItemStack handStack) {
        if (handStack.isIn(SimplyBootsTags.BOOTS) && SimplyBootsHelpers.wearingBoots(user, SimplyBootsTags.BOOTS)) {
            cir.setReturnValue(TypedActionResult.fail(handStack));
        }
    }
}
