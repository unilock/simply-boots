package io.github.alabasteralibi.simplyboots.components;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class BootComponents implements EntityComponentInitializer {
    public static final ComponentKey<RocketBootsComponent> ROCKET_BOOTS =
            ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier("simplyboots", "rocket_boots"), RocketBootsComponent.class);
    public static final ComponentKey<LavaBootsComponent> LAVA_BOOTS =
            ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier("simplyboots", "lava_boots"), LavaBootsComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(PlayerEntity.class, ROCKET_BOOTS, RocketBootsComponent::new);
        registry.registerFor(PlayerEntity.class, LAVA_BOOTS, LavaBootsComponent::new);
    }
}
