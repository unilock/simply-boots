package io.github.alabasteralibi.simplyboots.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.AnvilScreenHandler;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static io.github.alabasteralibi.simplyboots.registry.SimplyBootsItems.*;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenHandlerMixin {
    private final List<Item[]> recipes = Arrays.asList(
            new Item[] { ROCKET_BOOTS, HERMES_BOOTS, SPECTRE_BOOTS },
            new Item[] { HERMES_BOOTS, ROCKET_BOOTS, SPECTRE_BOOTS },
            new Item[] { LIGHTNING_BOOTS, ICE_SKATES, FROSTSPARK_BOOTS },
            new Item[] { ICE_SKATES, LIGHTNING_BOOTS, FROSTSPARK_BOOTS },
            new Item[] { WATER_WALKING_BOOTS, Items.WITHER_SKELETON_SKULL, OBSIDIAN_WATER_WALKING_BOOTS },
            new Item[] { OBSIDIAN_WATER_WALKING_BOOTS, LAVA_CHARM, LAVA_WADERS },
            new Item[] { FROSTSPARK_BOOTS, LAVA_WADERS, TERRASPARK_BOOTS },
            new Item[] { LAVA_WADERS, FROSTSPARK_BOOTS, TERRASPARK_BOOTS }
    );
    private Item[] currentRecipe;

    @Inject(method = "updateResult", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/inventory/Inventory;getStack(I)Lnet/minecraft/item/ItemStack;", ordinal = 1))
    private void updateRecipe(CallbackInfo ci, @Local(ordinal = 1) ItemStack stack1, @Local(ordinal = 2) ItemStack stack2) {
        recipes.stream()
                .filter((recipe) -> recipe[0] == stack1.getItem() && recipe[1] == stack2.getItem())
                .findFirst()
                .ifPresentOrElse((recipe) -> currentRecipe = recipe, () -> currentRecipe = null);
    }

    @ModifyExpressionValue(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 1))
    private boolean allowSpecialAnvilRecipes(boolean original) {
        return currentRecipe != null || original;
    }

    @ModifyExpressionValue(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isDamageable()Z", ordinal = 1))
    private boolean bypassDamageCheck(boolean original) {
        return currentRecipe != null || original;
    }

    @Inject(method = "updateResult", at = @At(value = "FIELD", target = "Lnet/minecraft/screen/AnvilScreenHandler;levelCost:Lnet/minecraft/screen/Property;", opcode = Opcodes.GETFIELD, ordinal = 5))
    private void bypassLevelCheck(CallbackInfo ci, @Local(ordinal = 0) LocalIntRef i) {
        if (currentRecipe != null && i.get() == 0) {
            i.set(1);
        }
    }

    @Inject(method = "updateResult", at = @At(value = "FIELD", target = "Lnet/minecraft/screen/AnvilScreenHandler;output:Lnet/minecraft/inventory/CraftingResultInventory;", opcode = Opcodes.GETFIELD, ordinal = 4))
    private void modifyOutputItem(CallbackInfo ci, @Local(ordinal = 1) LocalRef<ItemStack> stack1) {
        if (currentRecipe != null) {
            ItemStack newStack = new ItemStack(currentRecipe[2]);
            newStack.setNbt(stack1.get().getNbt());
            stack1.set(newStack);
        }
    }
}
