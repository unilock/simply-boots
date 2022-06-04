package io.github.alabasteralibi.simplyboots;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.alabasteralibi.simplyboots.components.BootComponents;
import io.github.alabasteralibi.simplyboots.components.ClampedBootIntComponent;
import io.github.alabasteralibi.simplyboots.components.LavaBootsComponent;
import io.github.alabasteralibi.simplyboots.components.RocketBootsComponent;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsAttributes;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsItems;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsTags;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.impl.client.texture.FabricSprite;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class SimplyBoots implements ModInitializer {
    public static final Identifier ICONS_TEXTURE = new Identifier("simplyboots", "textures/hud/icons.png");

    public static final ItemGroup MAIN_GROUP = FabricItemGroupBuilder.build(
            new Identifier("simplyboots", "main_group"),
            () -> new ItemStack(SimplyBootsItems.LAVA_WADERS));

    @Override
    public void onInitialize() {
        SimplyBootsItems.WATER_WALKING_BOOTS.toString();
        SimplyBootsAttributes.GENERIC_STEP_HEIGHT.toString();

        ServerPlayNetworking.registerGlobalReceiver(new Identifier("simplyboots", "rocket_boost"),
                (server, player, handler, buf, responseSender) -> {
                    if (!player.getEquippedStack(EquipmentSlot.FEET).isIn(SimplyBootsTags.ROCKET_BOOTS)) { return; }
                    if (player.isOnGround()) { return; }

                    ClampedBootIntComponent rocketTicks = BootComponents.ROCKET_BOOTS.get(player);
                    if (rocketTicks.getValue() > 0) {
                        rocketTicks.decrement();
                    } else {
                        return;
                    }

                    Vec3d velocity = player.getVelocity();
                    if (player.isFallFlying()) {
                        rocketTicks.decrement();
                        rocketTicks.decrement();
                        Vec3d vec3d = player.getRotationVector();
                        player.addVelocity(vec3d.x * 0.1D + (vec3d.x * 1.5D - velocity.x) * 0.5D,
                                vec3d.y * 0.1D + (vec3d.y * 1.5D - velocity.y) * 0.5D,
                                vec3d.z * 0.1D + (vec3d.z * 1.5D - velocity.z) * 0.5D);
                        player.velocityModified = true;
                    } else {
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 3, 5, true, false, false));
                    }
                });

        // Render HUD elements for rocket and lava timers
        HudRenderCallback.EVENT.register(((matrixStack, tickDelta) -> {
            PlayerEntity player = MinecraftClient.getInstance().player;

            if (player == null || player.isCreative()) {
                return;
            }

            int vehicle_hearts = 0;
            if (player.getVehicle() instanceof LivingEntity vehicle) {
                vehicle_hearts = (int)(vehicle.getMaxHealth() + 0.5F) / 2;
                if (vehicle_hearts > 30) {
                    vehicle_hearts = 30;
                }
            }

            int drawWidth = MinecraftClient.getInstance().getWindow().getScaledWidth() / 2 + 91;

            int drawHeight = MinecraftClient.getInstance().getWindow().getScaledHeight() - 59;
            drawHeight -= ((int)Math.ceil(vehicle_hearts / 10.0D) - 1) * 10;
            drawHeight -= player.getAir() < player.getMaxAir() ? 10 : 0;  // If air showing, move up

            drawHeight -= renderLavaImmunityBar(matrixStack, drawWidth, drawHeight) ? 10 : 0;
            drawHeight -= renderRocketBar(matrixStack, drawWidth, drawHeight) ? 10 : 0;
        }));
    }

    /**
     * Renders the lava immunity bar for the player above the hunger shanks.
     * @param matrixStack The MatrixStack to draw to.
     * @param drawWidth The width to draw the right side of the bar at.
     * @param drawHeight The height to draw the bar at.
     * @return Whether the bar was successfully rendered or not.
     */
    private boolean renderLavaImmunityBar(MatrixStack matrixStack, int drawWidth, int drawHeight) {
        PlayerEntity player = MinecraftClient.getInstance().player;

        // Don't display if full and not in lava (like breath bubbles)
        int lavaTicksLeft = BootComponents.LAVA_BOOTS.get(player).getValue();
        if (lavaTicksLeft == LavaBootsComponent.MAX_VALUE && !player.isSubmergedIn(FluidTags.LAVA)) {
            return false;
        }

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, ICONS_TEXTURE);
        int iconsToDraw = MathHelper.ceil(lavaTicksLeft * 10 / (double)LavaBootsComponent.MAX_VALUE);
        for(int i = 0; i < iconsToDraw; ++i) {
            DrawableHelper.drawTexture(matrixStack, drawWidth - i * 8 - 9, drawHeight, 0, 0, 9, 9, 18, 9);
        }
        return true;
    }

    /**
     * Renders the remaining rocket boost time above any other bars.
     * @param matrixStack The MatrixStack to draw to.
     * @param drawWidth The width to draw the right side of the bar at.
     * @param drawHeight The height to draw the bar at.
     * @return Whether the rocket bar was successfully rendered or not.
     */
    private boolean renderRocketBar(MatrixStack matrixStack, int drawWidth, int drawHeight) {
        PlayerEntity player = MinecraftClient.getInstance().player;

        // Don't display if full
        int rocketTicksLeft = BootComponents.ROCKET_BOOTS.get(player).getValue();
        if (rocketTicksLeft == RocketBootsComponent.MAX_VALUE || player.isOnGround()) {
            return false;
        }

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, ICONS_TEXTURE);
        int iconsToDraw = MathHelper.ceil(rocketTicksLeft * 10 / (double)RocketBootsComponent.MAX_VALUE);
        for(int i = 0; i < iconsToDraw; ++i) {
            DrawableHelper.drawTexture(matrixStack, drawWidth - i * 8 - 9, drawHeight, 9, 0, 9, 9, 18, 9);
        }
        return true;
    }
}
