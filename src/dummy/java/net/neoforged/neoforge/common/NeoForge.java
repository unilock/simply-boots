package net.neoforged.neoforge.common;

import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.IEventBus;

import java.util.function.Consumer;

public class NeoForge {
	public static final IEventBus EVENT_BUS = new IEventBus() {
		@Override
		public <T extends Event> void addListener(Class<T> eventType, Consumer<T> consumer) {
			throw new AssertionError();
		}
	};
}
