package io.github.alabasteralibi.simplyboots.items;

import io.github.alabasteralibi.simplyboots.SimplyBootsHelpers;
import io.github.alabasteralibi.simplyboots.client.BootArmorRenderer;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsTags;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

public class BootItem extends ArmorItem implements GeoItem {
    public static final EntityAttributeModifier STEP_BOOST_MODIFIER = new EntityAttributeModifier(SimplyBootsHelpers.id("step_height"), 0.75, EntityAttributeModifier.Operation.ADD_VALUE);
    public static final EntityAttributeModifier SPEEDY_MODIFIER = new EntityAttributeModifier(SimplyBootsHelpers.id("speedy"), 0.075, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);
    public static final EntityAttributeModifier EXTRA_SPEEDY_MODIFIER = new EntityAttributeModifier(SimplyBootsHelpers.id("extra_speedy"), 0.15, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public BootItem(RegistryEntry<ArmorMaterial> material, Type type, Settings settings) {
        super(material, type, settings);

        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private BootArmorRenderer renderer;

            @Override
            public @Nullable <T extends LivingEntity> BipedEntityModel<?> getGeoArmorRenderer(@Nullable T livingEntity, ItemStack itemStack, @Nullable EquipmentSlot equipmentSlot, @Nullable BipedEntityModel<T> original) {
                if (this.renderer == null) {
                    this.renderer = new BootArmorRenderer();
                }

                // TODO: deprecation?
                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);

                return this.renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 20, state -> PlayState.STOP));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public AttributeModifiersComponent getAttributeModifiers() {
        AttributeModifiersComponent modifiers = super.getAttributeModifiers();
        if (this.getDefaultStack().isIn(SimplyBootsTags.BOOTS)) {
            modifiers.with(EntityAttributes.GENERIC_STEP_HEIGHT, STEP_BOOST_MODIFIER, AttributeModifierSlot.FEET);
        }
        if (this.getDefaultStack().isIn(SimplyBootsTags.SPEEDY_BOOTS)) {
            modifiers.with(EntityAttributes.GENERIC_MOVEMENT_SPEED, SPEEDY_MODIFIER, AttributeModifierSlot.FEET);
        }
        if (this.getDefaultStack().isIn(SimplyBootsTags.EXTRA_SPEEDY_BOOTS)) {
            modifiers.with(EntityAttributes.GENERIC_MOVEMENT_SPEED, EXTRA_SPEEDY_MODIFIER, AttributeModifierSlot.FEET);
        }
        return modifiers;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        Text text = Text.translatable(String.format("item.simplyboots.%s.tooltip", Registries.ITEM.getId(this).getPath()));
        String[] lines = text.getString().split("\n");
        for (String line : lines) {
            tooltip.add(Text.of(line));
        }
    }
}
