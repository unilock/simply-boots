package io.github.alabasteralibi.simplyboots.components;

import io.github.alabasteralibi.simplyboots.SimplyBootsHelpers;
import net.minecraft.entity.player.PlayerEntity;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistryV3;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;

public class BootComponents implements EntityComponentInitializer {
    public static final ComponentKey<RocketBootsComponent> ROCKET_BOOTS =
            ComponentRegistryV3.INSTANCE.getOrCreate(SimplyBootsHelpers.id("rocket_boots"), RocketBootsComponent.class);
    public static final ComponentKey<LavaBootsComponent> LAVA_BOOTS =
            ComponentRegistryV3.INSTANCE.getOrCreate(SimplyBootsHelpers.id("lava_boots"), LavaBootsComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(PlayerEntity.class, ROCKET_BOOTS, RocketBootsComponent::new);
        registry.registerFor(PlayerEntity.class, LAVA_BOOTS, LavaBootsComponent::new);
    }
}
