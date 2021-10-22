package io.github.alabasteralibi.simplyboots.components;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class BootComponents implements EntityComponentInitializer {
    public static final ComponentKey<ClampedBootIntComponent> ROCKET_BOOTS =
            ComponentRegistry.getOrCreate(new Identifier("simplyboots", "rocket_boots"), ClampedBootIntComponent.class);
    public static final ComponentKey<ClampedBootIntComponent> LAVA_BOOTS =
            ComponentRegistry.getOrCreate(new Identifier("simplyboots", "lava_boots"), ClampedBootIntComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(PlayerEntity.class, ROCKET_BOOTS, RocketBootsComponent::new);
        registry.registerFor(LivingEntity.class, LAVA_BOOTS, LavaBootsComponent::new);
    }
}
