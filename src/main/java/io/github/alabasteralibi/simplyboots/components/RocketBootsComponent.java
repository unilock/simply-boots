package io.github.alabasteralibi.simplyboots.components;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ClientTickingComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsTags;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public class RocketBootsComponent implements ClampedBootIntComponent, ClientTickingComponent, ServerTickingComponent, AutoSyncedComponent {
    private int rocketTicks = 0;
    private boolean spacePressedLastTick;
    private boolean onGroundLastTick;
    private boolean flyingLastTick;
    private boolean stillHoldingSpace;
    private boolean elytraStart;
    private final PlayerEntity entity;
    public static final int MAX_VALUE = 32;
    public static final int MIN_VALUE = 0;

    public RocketBootsComponent(PlayerEntity entity) {
        this.entity = entity;
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
        BootComponents.ROCKET_BOOTS.sync(this.entity);
    }

    @Override
    public void decrement() {
        rocketTicks--;
        if (rocketTicks < MIN_VALUE) {
            rocketTicks = MIN_VALUE;
        }
        BootComponents.ROCKET_BOOTS.sync(entity);
    }

    @Override public void readFromNbt(NbtCompound tag) { rocketTicks = tag.getInt("rocketTicks"); }
    @Override public void writeToNbt(NbtCompound tag) { tag.putInt("rocketTicks", rocketTicks); }

    @Override
    public void serverTick() {
        if (entity.isOnGround()) {
            rocketTicks = MAX_VALUE;
        }
        BootComponents.ROCKET_BOOTS.sync(entity);
    }

    @Override
    public void clientTick() {
        if (entity.getEquippedStack(EquipmentSlot.FEET).isIn(SimplyBootsTags.ROCKET_BOOTS)) {
            boolean spacePressedThisTick = MinecraftClient.getInstance().options.keyJump.isPressed();
            boolean onGroundThisTick = entity.isOnGround();
            boolean flyingThisTick = entity.isFallFlying();

            if (!spacePressedLastTick && spacePressedThisTick && !onGroundLastTick) {
                if (!flyingLastTick && flyingThisTick) {
                    elytraStart = true;
                } else {
                    ClientPlayNetworking.send(new Identifier("simplyboots", "rocket_boost"), PacketByteBufs.empty());
                    elytraStart = false;
                }
                stillHoldingSpace = true;
            }
            if (spacePressedLastTick && !spacePressedThisTick || onGroundThisTick) {
                stillHoldingSpace = false;
            }
            if (stillHoldingSpace && !elytraStart) {
                ClientPlayNetworking.send(new Identifier("simplyboots", "rocket_boost"), PacketByteBufs.empty());
            }

            spacePressedLastTick = spacePressedThisTick;
            onGroundLastTick = onGroundThisTick;
            flyingLastTick = flyingThisTick;
        }
    }
}
