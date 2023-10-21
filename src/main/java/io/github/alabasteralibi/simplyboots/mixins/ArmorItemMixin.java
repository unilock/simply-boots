package io.github.alabasteralibi.simplyboots.mixins;

import com.google.common.collect.ImmutableMultimap;
import dev.emi.stepheightentityattribute.StepHeightEntityAttributeMain;
import io.github.alabasteralibi.simplyboots.items.BootItems;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.EnumMap;
import java.util.UUID;

@Mixin(ArmorItem.class)
public class ArmorItemMixin {
    @Shadow
    @Final
    private static EnumMap<ArmorItem.Type, UUID> MODIFIERS;

    @Inject(method = "<init>",
            at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableMultimap$Builder;build()Lcom/google/common/collect/ImmutableMultimap;"),
            locals = LocalCapture.CAPTURE_FAILHARD,
            remap = false)
    private void addBootAttributes(ArmorMaterial material, ArmorItem.Type type, Item.Settings settings, CallbackInfo ci, ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder) {
        UUID uUID = MODIFIERS.get(type);
        if ((ArmorItem) (Object) this instanceof BootItems.BaseBootItem) {
            builder.put(StepHeightEntityAttributeMain.STEP_HEIGHT, new EntityAttributeModifier(uUID, "Step height", 0.4, EntityAttributeModifier.Operation.ADDITION));
        }
        if ((ArmorItem) (Object) this instanceof BootItems.SpeedyBootItem) {
            builder.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(uUID, "Movement speed", 0.075, EntityAttributeModifier.Operation.MULTIPLY_BASE));
        }
        if ((ArmorItem) (Object) this instanceof BootItems.ExtraSpeedyBootItem) {
            builder.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(uUID, "Movement speed", 0.15, EntityAttributeModifier.Operation.MULTIPLY_BASE));
        }
    }
}
