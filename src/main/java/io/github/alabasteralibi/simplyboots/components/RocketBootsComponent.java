package io.github.alabasteralibi.simplyboots.components;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ClientTickingComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsTags;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class RocketBootsComponent implements ClampedBootIntComponent, ClientTickingComponent, ServerTickingComponent, AutoSyncedComponent {
    private int rocketTicks = 0;
    private boolean spacePressedLastTick;
    private boolean onGroundLastTick;
    private boolean flyingLastTick;
    private boolean stillHoldingSpace;
    private boolean elytraStart;
    private final PlayerEntity player;
    public static final int MAX_VALUE = 32;
    public static final int MIN_VALUE = 0;

    public RocketBootsComponent(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public int getValue() {
        return rocketTicks;
    }

    @Override
    public void increment() {
        rocketTicks++;
        if (rocketTicks > MAX_VALUE) {
            rocketTicks = MAX_VALUE;
        }
        BootComponents.ROCKET_BOOTS.sync(this.player);
    }

    @Override
    public void decrement() {
        rocketTicks--;
        if (rocketTicks < MIN_VALUE) {
            rocketTicks = MIN_VALUE;
        }
        BootComponents.ROCKET_BOOTS.sync(player);
    }

    @Override public void readFromNbt(NbtCompound tag) { rocketTicks = tag.getInt("rocketTicks"); }
    @Override public void writeToNbt(NbtCompound tag) { tag.putInt("rocketTicks", rocketTicks); }

    @Override
    public void serverTick() {
        if (player.isOnGround()) {
            rocketTicks = MAX_VALUE;
        }
        BootComponents.ROCKET_BOOTS.sync(player);
    }

    @Override
    public void clientTick() {
        if (player.getEquippedStack(EquipmentSlot.FEET).isIn(SimplyBootsTags.ROCKET_BOOTS)) {
            boolean spacePressedThisTick = MinecraftClient.getInstance().options.keyJump.isPressed();
            boolean onGroundThisTick = player.isOnGround();
            boolean flyingThisTick = player.isFallFlying();

            boolean sentBoost = false;
            if (!spacePressedLastTick && spacePressedThisTick && !onGroundLastTick) {
                if (!flyingLastTick && flyingThisTick) {
                    elytraStart = true;
                } else {
                    ClientPlayNetworking.send(new Identifier("simplyboots", "rocket_boost"), PacketByteBufs.empty());

                    if (player.isOnGround()) { return; }

                    ClampedBootIntComponent rocketTicks = BootComponents.ROCKET_BOOTS.get(player);
                    if (rocketTicks.getValue() == 0) {
                        return;
                    }

                    if (player.isFallFlying()) {
                        Vec3d velocity = player.getVelocity();
                        Vec3d vec3d = player.getRotationVector();
                        player.addVelocity(vec3d.x * 0.1D + (vec3d.x * 1.5D - velocity.x) * 0.5D,
                                vec3d.y * 0.1D + (vec3d.y * 1.5D - velocity.y) * 0.5D,
                                vec3d.z * 0.1D + (vec3d.z * 1.5D - velocity.z) * 0.5D);
                    } else {
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 3, 5, true, false, false));
                    }

                    elytraStart = false;
                    sentBoost = true;
                }
                stillHoldingSpace = true;
            }
            if (spacePressedLastTick && (!spacePressedThisTick || onGroundThisTick)) {
                stillHoldingSpace = false;
            }
            if (stillHoldingSpace && !elytraStart && !sentBoost) {
                ClientPlayNetworking.send(new Identifier("simplyboots", "rocket_boost"), PacketByteBufs.empty());
                if (!player.getEquippedStack(EquipmentSlot.FEET).isIn(SimplyBootsTags.ROCKET_BOOTS)) { return; }
                if (player.isOnGround()) { return; }

                ClampedBootIntComponent rocketTicks = BootComponents.ROCKET_BOOTS.get(player);
                if (rocketTicks.getValue() == 0) {
                    return;
                }

                if (player.isFallFlying()) {
                    Vec3d velocity = player.getVelocity();
                    Vec3d vec3d = player.getRotationVector();
                    player.addVelocity(vec3d.x * 0.1D + (vec3d.x * 1.5D - velocity.x) * 0.5D,
                            vec3d.y * 0.1D + (vec3d.y * 1.5D - velocity.y) * 0.5D,
                            vec3d.z * 0.1D + (vec3d.z * 1.5D - velocity.z) * 0.5D);
                } else {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 3, 5, true, false, false));
                }
            }

            spacePressedLastTick = spacePressedThisTick;
            onGroundLastTick = onGroundThisTick;
            flyingLastTick = flyingThisTick;
        }
    }
}
