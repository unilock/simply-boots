package io.github.alabasteralibi.simplyboots;

import io.github.alabasteralibi.simplyboots.registry.SimplyBootsItems;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsTags;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class SimplyBootsHelpers {
    public static boolean wearingLavaImmune(LivingEntity entity) {
        return entity.getEquippedStack(EquipmentSlot.FEET).isIn(SimplyBootsTags.HOT_FLUID_WALKING_BOOTS) ||
               entity.getEquippedStack(EquipmentSlot.MAINHAND).getItem() == SimplyBootsItems.LAVA_CHARM ||
               entity.getEquippedStack(EquipmentSlot.OFFHAND).getItem() == SimplyBootsItems.LAVA_CHARM;
    }

    public static boolean isFireImmune(LivingEntity entity) {
        return entity.getEquippedStack(EquipmentSlot.FEET).isIn(SimplyBootsTags.FIRE_RESISTANT_BOOTS) ||
               entity.getEquippedStack(EquipmentSlot.MAINHAND).getItem() == SimplyBootsItems.LAVA_CHARM ||
               entity.getEquippedStack(EquipmentSlot.OFFHAND).getItem() == SimplyBootsItems.LAVA_CHARM;
    }

    public static Identifier id(String path) {
        return new Identifier("simplyboots", path);
    }
}
