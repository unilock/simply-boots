package net.neoforged.neoforge.event.entity;

import net.minecraft.entity.Entity;
import net.neoforged.bus.api.Event;

public class EntityEvent extends Event {
	private final Entity entity;

	public EntityEvent(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}
}
