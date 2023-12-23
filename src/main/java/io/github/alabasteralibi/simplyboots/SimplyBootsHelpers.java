package io.github.alabasteralibi.simplyboots;

import dev.emi.trinkets.api.TrinketsApi;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsItems;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsTags;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.atomic.AtomicBoolean;

public class SimplyBootsHelpers {
    public static boolean wearingBoots(LivingEntity entity, Item boots) {
        if (boots.equals(entity.getEquippedStack(EquipmentSlot.FEET).getItem())) {
            return true;
        } else if (!SimplyBootsHelpers.isTrinketsLoaded()) {
            return false;
        }
        AtomicBoolean bl = new AtomicBoolean(false);
        TrinketsApi.getTrinketComponent(entity).ifPresent(component -> bl.set(component.isEquipped(boots)));
        return bl.get();
    }

    public static boolean wearingBoots(LivingEntity entity, TagKey<Item> tag) {
        if (entity.getEquippedStack(EquipmentSlot.FEET).isIn(tag)) {
            return true;
        } else if (!SimplyBootsHelpers.isTrinketsLoaded()) {
            return false;
        }
        AtomicBoolean bl = new AtomicBoolean(false);
        TrinketsApi.getTrinketComponent(entity).ifPresent(component -> bl.set(component.isEquipped(trinket -> trinket.isIn(tag))));
        return bl.get();
    }

    public static boolean wearingLavaImmune(LivingEntity entity) {
        return SimplyBootsHelpers.wearingBoots(entity, SimplyBootsTags.HOT_FLUID_WALKING_BOOTS) ||
                entity.isHolding(SimplyBootsItems.LAVA_CHARM);
    }

    public static boolean isFireImmune(LivingEntity entity) {
        return SimplyBootsHelpers.wearingBoots(entity, SimplyBootsTags.FIRE_RESISTANT_BOOTS) ||
                entity.isHolding(SimplyBootsItems.LAVA_CHARM);
    }

    public static boolean isTrinketsLoaded() {
        return FabricLoader.getInstance().isModLoaded("trinkets");
    }

    public static Identifier id(String path) {
        return new Identifier("simplyboots", path);
    }
}
