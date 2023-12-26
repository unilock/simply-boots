package io.github.alabasteralibi.simplyboots.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.alabasteralibi.simplyboots.SimplyBootsHelpers;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsTags;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net/minecraft/screen/PlayerScreenHandler$1")
public abstract class PlayerScreenHandlerMixin extends Slot {
    public PlayerScreenHandlerMixin(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @ModifyReturnValue(method = "canInsert(Lnet/minecraft/item/ItemStack;)Z", at = @At("RETURN"))
    private boolean preventDoubleEquip(boolean original, ItemStack stack) {
        if (inventory instanceof PlayerInventory playerInventory &&
                stack.isIn(SimplyBootsTags.BOOTS) &&
                SimplyBootsHelpers.wearingTrinketBoots(playerInventory.player, SimplyBootsTags.BOOTS)) {
            return false;
        } else {
            return original;
        }
    }
}
