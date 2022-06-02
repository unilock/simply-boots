package io.github.alabasteralibi.simplyboots;

import io.github.alabasteralibi.simplyboots.components.BootComponents;
import io.github.alabasteralibi.simplyboots.components.ClampedBootIntComponent;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsAttributes;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsItems;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsTags;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class SimplyBoots implements ModInitializer {
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
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 2, 5, true, false, false));
                    }
                });
    }
}
