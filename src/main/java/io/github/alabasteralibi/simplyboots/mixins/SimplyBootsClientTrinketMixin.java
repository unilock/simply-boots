package io.github.alabasteralibi.simplyboots.mixins;

import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import io.github.alabasteralibi.simplyboots.SimplyBootsClient;
import io.github.alabasteralibi.simplyboots.client.TrinketBootRenderer;
import net.fabricmc.api.ClientModInitializer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static io.github.alabasteralibi.simplyboots.registry.SimplyBootsItems.*;

@Mixin(SimplyBootsClient.class)
public abstract class SimplyBootsClientTrinketMixin implements ClientModInitializer {
    @Inject(method = "onInitializeClient", at = @At("HEAD"), remap = false)
    private void registerTrinketRenderers(CallbackInfo ci) {
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
