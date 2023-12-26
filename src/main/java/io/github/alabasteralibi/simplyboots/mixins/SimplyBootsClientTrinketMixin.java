package io.github.alabasteralibi.simplyboots.mixins;

import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import io.github.alabasteralibi.simplyboots.SimplyBootsClient;
import io.github.alabasteralibi.simplyboots.client.BootTrinketRenderer;
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
