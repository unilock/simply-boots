package io.github.alabasteralibi.simplyboots;

import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import io.github.alabasteralibi.simplyboots.client.BootTrinketRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import static io.github.alabasteralibi.simplyboots.registry.SimplyBootsItems.*;

@Environment(EnvType.CLIENT)
public class SimplyBootsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TrinketRendererRegistry.registerRenderer(HERMES_BOOTS, new BootTrinketRenderer());
        TrinketRendererRegistry.registerRenderer(ROCKET_BOOTS, new BootTrinketRenderer());
        TrinketRendererRegistry.registerRenderer(SPECTRE_BOOTS, new BootTrinketRenderer());
        TrinketRendererRegistry.registerRenderer(LIGHTNING_BOOTS, new BootTrinketRenderer());
        TrinketRendererRegistry.registerRenderer(ICE_SKATES, new BootTrinketRenderer());
        TrinketRendererRegistry.registerRenderer(FROSTSPARK_BOOTS, new BootTrinketRenderer());
        TrinketRendererRegistry.registerRenderer(WATER_WALKING_BOOTS, new BootTrinketRenderer());
        TrinketRendererRegistry.registerRenderer(OBSIDIAN_WATER_WALKING_BOOTS, new BootTrinketRenderer());
        TrinketRendererRegistry.registerRenderer(LAVA_WADERS, new BootTrinketRenderer());
        TrinketRendererRegistry.registerRenderer(TERRASPARK_BOOTS, new BootTrinketRenderer());
    }
}
