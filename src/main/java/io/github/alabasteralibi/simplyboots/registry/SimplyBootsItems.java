package io.github.alabasteralibi.simplyboots.registry;

import io.github.alabasteralibi.simplyboots.SimplyBoots;
import io.github.alabasteralibi.simplyboots.items.BootItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SimplyBootsItems {
    public static final Item HERMES_BOOTS = register("hermes_boots", new BootItems.SpeedyBootItem(ArmorMaterials.LEATHER, EquipmentSlot.FEET, new Item.Settings().group(SimplyBoots.MAIN_GROUP)));
    public static final Item ROCKET_BOOTS = register("rocket_boots", new BootItems.BaseBootItem(ArmorMaterials.LEATHER, EquipmentSlot.FEET, new Item.Settings().group(SimplyBoots.MAIN_GROUP)));
    public static final Item SPECTRE_BOOTS = register("spectre_boots", new BootItems.SpeedyBootItem(ArmorMaterials.LEATHER, EquipmentSlot.FEET, new Item.Settings().group(SimplyBoots.MAIN_GROUP)));
    public static final Item LIGHTNING_BOOTS = register("lightning_boots", new BootItems.ExtraSpeedyBootItem(ArmorMaterials.LEATHER, EquipmentSlot.FEET, new Item.Settings().group(SimplyBoots.MAIN_GROUP)));
    public static final Item ICE_SKATES = register("ice_skates", new BootItems.BaseBootItem(ArmorMaterials.LEATHER, EquipmentSlot.FEET, new Item.Settings().group(SimplyBoots.MAIN_GROUP)));
    public static final Item FROSTSPARK_BOOTS = register("frostspark_boots", new BootItems.ExtraSpeedyBootItem(ArmorMaterials.LEATHER, EquipmentSlot.FEET, new Item.Settings().group(SimplyBoots.MAIN_GROUP)));
    public static final Item WATER_WALKING_BOOTS = register("water_walking_boots", new BootItems.BaseBootItem(ArmorMaterials.LEATHER, EquipmentSlot.FEET, new Item.Settings().group(SimplyBoots.MAIN_GROUP)));
    public static final Item OBSIDIAN_WATER_WALKING_BOOTS = register("obsidian_water_walking_boots", new BootItems.BaseBootItem(ArmorMaterials.LEATHER, EquipmentSlot.FEET, new Item.Settings().group(SimplyBoots.MAIN_GROUP)));
    public static final Item LAVA_WADERS = register("lava_waders", new BootItems.BaseBootItem(ArmorMaterials.LEATHER, EquipmentSlot.FEET, new Item.Settings().group(SimplyBoots.MAIN_GROUP).fireproof()));
    public static final Item TERRASPARK_BOOTS = register("terraspark_boots", new BootItems.ExtraSpeedyBootItem(ArmorMaterials.LEATHER, EquipmentSlot.FEET, new Item.Settings().group(SimplyBoots.MAIN_GROUP).fireproof()));
    public static final Item LAVA_CHARM = register("lava_charm", new Item(new Item.Settings().group(SimplyBoots.MAIN_GROUP).fireproof()));

    private static Item register(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier("simplyboots", name), item);
    }
}
