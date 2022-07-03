package io.github.alabasteralibi.simplyboots.registry;

import net.fabricmc.fabric.impl.tag.extension.TagFactoryImpl;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class SimplyBootsTags {
    public static final Tag<Item> FLUID_WALKING_BOOTS = TagFactoryImpl.ITEM.create(new Identifier("simplyboots", "fluid_walking_boots"));
    public static final Tag<Item> HOT_FLUID_WALKING_BOOTS = TagFactoryImpl.ITEM.create(new Identifier("simplyboots", "hot_fluid_walking_boots"));
    public static final Tag<Item> FIRE_RESISTANT_BOOTS = TagFactoryImpl.ITEM.create(new Identifier("simplyboots", "fire_resistant_boots"));
    public static final Tag<Item> ROCKET_BOOTS = TagFactoryImpl.ITEM.create(new Identifier("simplyboots", "rocket_boots"));
    public static final Tag<Item> ICE_SKATE_BOOTS = TagFactoryImpl.ITEM.create(new Identifier("simplyboots", "ice_skate_boots"));
}
