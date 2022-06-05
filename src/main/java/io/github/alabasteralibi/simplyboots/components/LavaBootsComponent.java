package io.github.alabasteralibi.simplyboots.components;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsTags;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;

public class LavaBootsComponent implements ClampedBootIntComponent, ServerTickingComponent, AutoSyncedComponent {
    private int lavaTicks = 0;
    private final LivingEntity entity;
    public static final int MAX_VALUE = 140;
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
        BootComponents.LAVA_BOOTS.sync(entity);
    }

    @Override
    public void decrement() {
        lavaTicks--;
        if (lavaTicks < MIN_VALUE) {
            lavaTicks = MIN_VALUE;
        }
        BootComponents.LAVA_BOOTS.sync(entity);
    }

    @Override public void readFromNbt(NbtCompound tag) { lavaTicks = tag.getInt("lavaTicks"); }
    @Override public void writeToNbt(NbtCompound tag) { tag.putInt("lavaTicks", lavaTicks); }

    @Override
    public void serverTick() {
        if (!entity.getEquippedStack(EquipmentSlot.FEET).isIn(SimplyBootsTags.HOT_FLUID_WALKING_BOOTS)) {
            lavaTicks = MIN_VALUE;
            BootComponents.LAVA_BOOTS.sync(entity);
            return;
        }
        if (entity.isInLava()) {
            decrement();
        } else {
            increment();
        }
    }
}
