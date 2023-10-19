package io.github.alabasteralibi.simplyboots.registry;

import io.github.alabasteralibi.simplyboots.SimplyBootsHelpers;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class SimplyBootsTags {
    public static final TagKey<Item> FLUID_WALKING_BOOTS = TagKey.of(RegistryKeys.ITEM, SimplyBootsHelpers.id("fluid_walking_boots"));
    public static final TagKey<Item> HOT_FLUID_WALKING_BOOTS = TagKey.of(RegistryKeys.ITEM, SimplyBootsHelpers.id("hot_fluid_walking_boots"));
    public static final TagKey<Item> FIRE_RESISTANT_BOOTS = TagKey.of(RegistryKeys.ITEM, SimplyBootsHelpers.id("fire_resistant_boots"));
    public static final TagKey<Item> ROCKET_BOOTS = TagKey.of(RegistryKeys.ITEM, SimplyBootsHelpers.id("rocket_boots"));
    public static final TagKey<Item> ICE_SKATE_BOOTS = TagKey.of(RegistryKeys.ITEM, SimplyBootsHelpers.id("ice_skate_boots"));
}
