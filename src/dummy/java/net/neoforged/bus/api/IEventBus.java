package net.neoforged.bus.api;

import java.util.function.Consumer;

public interface IEventBus {
	<T extends Event> void addListener(Class<T> eventType, Consumer<T> consumer);
}
