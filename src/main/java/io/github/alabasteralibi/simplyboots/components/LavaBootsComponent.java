package io.github.alabasteralibi.simplyboots.components;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;

public class LavaBootsComponent implements ClampedBootIntComponent {
    private int lavaTicks = 0;
    private LivingEntity entity;
    public static final int MAX_VALUE = 60;
    public static final int MIN_VALUE = 0;

    public LavaBootsComponent(LivingEntity entity) {
        this.entity = entity;
    }

    @Override
    public int getValue() {
        return lavaTicks;
    }

    @Override
    public void increment() {
        lavaTicks++;
        if (lavaTicks > MAX_VALUE) {
            lavaTicks = MAX_VALUE;
        }
    }

    @Override
    public void decrement() {
        lavaTicks--;
        if (lavaTicks < MIN_VALUE) {
            lavaTicks = MIN_VALUE;
        }
    }

    @Override public void readFromNbt(NbtCompound tag) { lavaTicks = tag.getInt("lavaTicks"); }
    @Override public void writeToNbt(NbtCompound tag) { tag.putInt("lavaTicks", lavaTicks); }
}
