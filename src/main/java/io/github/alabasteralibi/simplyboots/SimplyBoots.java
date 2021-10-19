package io.github.alabasteralibi.simplyboots;

import io.github.alabasteralibi.simplyboots.registry.SimplyBootsAttributes;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class SimplyBoots implements ModInitializer {
    public static final ItemGroup MAIN_GROUP = FabricItemGroupBuilder.build(
            new Identifier("simplyboots", "main_group"),
            () -> new ItemStack(SimplyBootsItems.LAVA_WADERS));

    @Override
    public void onInitialize() {
        SimplyBootsItems.WATER_WALKING_BOOTS.toString();
        SimplyBootsAttributes.GENERIC_STEP_HEIGHT.toString();
    }
}
