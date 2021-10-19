package io.github.alabasteralibi.simplyboots;

import io.github.alabasteralibi.simplyboots.client.BootRenderer;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Item;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

@Environment(EnvType.CLIENT)
public class SimplyBootsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        GeckoLib.initialize();
        register(SimplyBootsItems.HERMES_BOOTS);
        register(SimplyBootsItems.ROCKET_BOOTS);
        register(SimplyBootsItems.SPECTRE_BOOTS);
        register(SimplyBootsItems.LIGHTNING_BOOTS);
        register(SimplyBootsItems.FROSTSPARK_BOOTS);
        register(SimplyBootsItems.WATER_WALKING_BOOTS);
        register(SimplyBootsItems.OBSIDIAN_WATER_WALKING_BOOTS);
        register(SimplyBootsItems.LAVA_WADERS);
        register(SimplyBootsItems.TERRASPARK_BOOTS);
    }

    private static void register(Item item) {
        GeoArmorRenderer.registerArmorRenderer(new BootRenderer(), item);
    }
}
