package io.github.alabasteralibi.simplyboots.mixins;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.alabasteralibi.simplyboots.SimplyBootsHelpers;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsTags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(PlayerScreenHandler.class)
public class PlayerScreenHandlerMixin {
    @Shadow
    @Final
    private PlayerEntity owner;

    @WrapOperation(method = "quickMove", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/PlayerScreenHandler;insertItem(Lnet/minecraft/item/ItemStack;IIZ)Z"), slice = @Slice(
            from = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EquipmentSlot;getEntitySlotId()I"),
            to = @At(value = "FIELD", target = "Lnet/minecraft/entity/EquipmentSlot;OFFHAND:Lnet/minecraft/entity/EquipmentSlot;", opcode = Opcodes.GETSTATIC)
    ))
    private boolean preventDoubleBootsEquip(PlayerScreenHandler instance, ItemStack stack, int startIndex, int endIndex, boolean fromLast, Operation<Boolean> original) {
        if (stack.isIn(SimplyBootsTags.BOOTS) && SimplyBootsHelpers.wearingTrinketBoots(owner, SimplyBootsTags.BOOTS)) {
            return false;
        } else {
            return original.call(instance, stack, startIndex, endIndex, fromLast);
        }
    }
}
