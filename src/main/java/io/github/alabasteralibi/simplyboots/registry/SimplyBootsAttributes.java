package io.github.alabasteralibi.simplyboots.registry;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.registry.Registry;

public class SimplyBootsAttributes {
    public static final EntityAttribute GENERIC_STEP_HEIGHT = register("generic.step_height", (new ClampedEntityAttribute("attribute.name.generic.step_height", 0.0D, -1024.0D, 1024.0D)).setTracked(true));

    public static float getStepHeight(LivingEntity entity) {
        return (float) (entity.stepHeight + (entity.isSneaking() ? 0 : entity.getAttributeInstance(GENERIC_STEP_HEIGHT).getValue()));
    }

    private static EntityAttribute register(String id, EntityAttribute attribute) {
        return Registry.register(Registry.ATTRIBUTE, id, attribute);
    }
}
