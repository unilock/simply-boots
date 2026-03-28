package net.neoforged.neoforge.event.entity.living;

import net.minecraft.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.EntityEvent;

public class LivingEvent extends EntityEvent {
	private final LivingEntity livingEntity;

	public LivingEvent(LivingEntity entity) {
		super(entity);
		livingEntity = entity;
	}

	@Override
	public LivingEntity getEntity() {
		return livingEntity;
	}
}
