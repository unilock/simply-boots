package io.github.alabasteralibi.simplyboots.components;

import dev.onyxstudios.cca.api.v3.component.tick.ClientTickingComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsTags;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

public class RocketBootsComponent implements ClampedBootIntComponent, ClientTickingComponent, ServerTickingComponent {
    private int rocketTicks = 0;
    private PlayerEntity entity;
    private boolean wasDecremented = false;
    public static final int MAX_VALUE = 60;
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
    }

    @Override
    public void decrement() {
        rocketTicks--;
        if (rocketTicks < MIN_VALUE) {
            rocketTicks = MIN_VALUE;
        }
        wasDecremented = true;
    }

    @Override public void readFromNbt(NbtCompound tag) { rocketTicks = tag.getInt("rocketTicks"); }
    @Override public void writeToNbt(NbtCompound tag) { tag.putInt("rocketTicks", rocketTicks); }

    @Override
    public void serverTick() {
        if (wasDecremented) {
            wasDecremented = false;
        } else {
            increment();
        }
    }

    @Override
    public void clientTick() {
        if (entity.getEquippedStack(EquipmentSlot.FEET).isIn(SimplyBootsTags.ROCKET_BOOTS)) {
            if (MinecraftClient.getInstance().options.keyJump.isPressed()) {
                ClientPlayNetworking.send(new Identifier("simplyboots", "rocket_boost"), PacketByteBufs.empty());
            }
        }
        entity.sendMessage(new LiteralText(String.valueOf(getValue())), false);
    }
}
