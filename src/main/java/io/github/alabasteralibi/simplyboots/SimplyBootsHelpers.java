package io.github.alabasteralibi.simplyboots;

import dev.emi.trinkets.api.TrinketsApi;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsItems;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsTags;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.atomic.AtomicBoolean;

public class SimplyBootsHelpers {
    public static boolean wearingBoots(LivingEntity entity, Item boots) {
        AtomicBoolean bl = new AtomicBoolean(false);
        TrinketsApi.getTrinketComponent(entity).ifPresent(component -> bl.set(component.isEquipped(boots)));
        return bl.get() || boots.equals(entity.getEquippedStack(EquipmentSlot.FEET).getItem());
    }

    public static boolean wearingBoots(LivingEntity entity, TagKey<Item> tag) {
        AtomicBoolean bl = new AtomicBoolean(false);
        TrinketsApi.getTrinketComponent(entity).ifPresent(component -> bl.set(component.isEquipped(trinket -> trinket.isIn(tag))));
        return bl.get() || entity.getEquippedStack(EquipmentSlot.FEET).isIn(tag);
    }

    public static boolean wearingLavaImmune(LivingEntity entity) {
        return SimplyBootsHelpers.wearingBoots(entity, SimplyBootsTags.HOT_FLUID_WALKING_BOOTS) ||
               entity.getEquippedStack(EquipmentSlot.MAINHAND).getItem() == SimplyBootsItems.LAVA_CHARM ||
               entity.getEquippedStack(EquipmentSlot.OFFHAND).getItem() == SimplyBootsItems.LAVA_CHARM;
    }

    public static boolean isFireImmune(LivingEntity entity) {
        return SimplyBootsHelpers.wearingBoots(entity, SimplyBootsTags.FIRE_RESISTANT_BOOTS) ||
               entity.getEquippedStack(EquipmentSlot.MAINHAND).getItem() == SimplyBootsItems.LAVA_CHARM ||
               entity.getEquippedStack(EquipmentSlot.OFFHAND).getItem() == SimplyBootsItems.LAVA_CHARM;
    }

    public static Identifier id(String path) {
        return new Identifier("simplyboots", path);
    }
}
