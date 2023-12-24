package io.github.alabasteralibi.simplyboots.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import dev.emi.stepheightentityattribute.StepHeightEntityAttributeMain;
import io.github.alabasteralibi.simplyboots.client.BootRenderer;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsTags;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BootItem extends ArmorItem implements GeoItem {
    // Randomly generated (is that how it's supposed to be done?)
    public static final UUID STEP_BOOST_UUID = UUID.fromString("724ED93B-066A-4199-A6A7-7763AE6EF399");
    public static final EntityAttributeModifier STEP_BOOST_MODIFIER = new EntityAttributeModifier(STEP_BOOST_UUID, "Step height", 0.75, EntityAttributeModifier.Operation.ADDITION);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public BootItem(ArmorMaterial material, Type type, Settings settings) {
        super(material, type, settings);

        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private BootRenderer renderer;

            @Override
            public BipedEntityModel<LivingEntity> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, BipedEntityModel<LivingEntity> original) {
                if (this.renderer == null) {
                    this.renderer = new BootRenderer();
                }

                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);

                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return this.renderProvider;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 20, state -> PlayState.STOP));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        Multimap<EntityAttribute, EntityAttributeModifier> modifiers = HashMultimap.create(super.getAttributeModifiers(slot));
        if (slot == this.type.getEquipmentSlot()) {
            UUID uUID = UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B");
            if (this.getRegistryEntry().isIn(SimplyBootsTags.BOOTS)) {
                modifiers.put(StepHeightEntityAttributeMain.STEP_HEIGHT, new EntityAttributeModifier(uUID, "Step height", 0.75, EntityAttributeModifier.Operation.ADDITION));
            }
            if (this.getRegistryEntry().isIn(SimplyBootsTags.SPEEDY_BOOTS)) {
                modifiers.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(uUID, "Movement speed", 0.075, EntityAttributeModifier.Operation.MULTIPLY_BASE));
            }
            if (this.getRegistryEntry().isIn(SimplyBootsTags.EXTRA_SPEEDY_BOOTS)) {
                modifiers.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(uUID, "Movement speed", 0.15, EntityAttributeModifier.Operation.MULTIPLY_BASE));
            }
        }
        return modifiers;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        Text text = Text.translatable(String.format("item.simplyboots.%s.tooltip", this));
        String[] lines = text.getString().split("\n");
        for (String line : lines) {
            tooltip.add(Text.of(line));
        }
    }
}
