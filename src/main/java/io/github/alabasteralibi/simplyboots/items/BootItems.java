package io.github.alabasteralibi.simplyboots.items;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

// This is a really inelegant solution, but it works for adding attributes so shrug
public class BootItems {
    public static class BaseBootItem extends ArmorItem implements IAnimatable {
        private AnimationFactory factory = new AnimationFactory(this);

        public BaseBootItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
            super(material, slot, settings);
        }

        private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event)
        {
            return PlayState.STOP;
        }

        @Override
        public void registerControllers(AnimationData data) {
            data.addAnimationController(new AnimationController(this, "controller", 20, this::predicate));
        }

        @Override
        public AnimationFactory getFactory() {
            return this.factory;
        }
    }

    public static class SpeedyBootItem extends BaseBootItem {
        public SpeedyBootItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
            super(material, slot, settings);
        }
    }

    public static class ExtraSpeedyBootItem extends BaseBootItem {
        public ExtraSpeedyBootItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
            super(material, slot, settings);
        }
    }
}
