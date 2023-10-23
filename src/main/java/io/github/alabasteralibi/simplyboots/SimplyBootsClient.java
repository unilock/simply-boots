package io.github.alabasteralibi.simplyboots;

import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import io.github.alabasteralibi.simplyboots.client.TrinketBootRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import static io.github.alabasteralibi.simplyboots.registry.SimplyBootsItems.*;

@Environment(EnvType.CLIENT)
public class SimplyBootsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TrinketRendererRegistry.registerRenderer(HERMES_BOOTS, new TrinketBootRenderer());
        TrinketRendererRegistry.registerRenderer(ROCKET_BOOTS, new TrinketBootRenderer());
        TrinketRendererRegistry.registerRenderer(SPECTRE_BOOTS, new TrinketBootRenderer());
        TrinketRendererRegistry.registerRenderer(LIGHTNING_BOOTS, new TrinketBootRenderer());
        TrinketRendererRegistry.registerRenderer(ICE_SKATES, new TrinketBootRenderer());
        TrinketRendererRegistry.registerRenderer(FROSTSPARK_BOOTS, new TrinketBootRenderer());
        TrinketRendererRegistry.registerRenderer(WATER_WALKING_BOOTS, new TrinketBootRenderer());
        TrinketRendererRegistry.registerRenderer(OBSIDIAN_WATER_WALKING_BOOTS, new TrinketBootRenderer());
        TrinketRendererRegistry.registerRenderer(LAVA_WADERS, new TrinketBootRenderer());
        TrinketRendererRegistry.registerRenderer(TERRASPARK_BOOTS, new TrinketBootRenderer());
    }
}
