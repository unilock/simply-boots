package io.github.alabasteralibi.simplyboots.mixins;

import io.github.alabasteralibi.simplyboots.registry.SimplyBootsTags;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameOverlayRenderer.class)
@Environment(EnvType.CLIENT)
public class InGameOverlayRendererMixin {
    @Inject(method = "renderFireOverlay", at = @At(value = "HEAD"), cancellable = true)
    private static void cancelOverlayWhenImmune(MinecraftClient client, MatrixStack vertices, CallbackInfo ci) {
        if (client.player != null && client.player.getEquippedStack(EquipmentSlot.FEET).isIn(SimplyBootsTags.FIRE_RESISTANT_BOOTS)) {
            ci.cancel();
        }
    }
}
