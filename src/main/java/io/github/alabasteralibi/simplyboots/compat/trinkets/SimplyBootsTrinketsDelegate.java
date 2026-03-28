package io.github.alabasteralibi.simplyboots.compat.trinkets;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketsApi;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsTags;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import static io.github.alabasteralibi.simplyboots.items.BootItem.*;

public class SimplyBootsTrinketsDelegate {
    public static void registerItem(Item item) {
        TrinketsApi.registerTrinket(item, new Trinket() {
            // Prevents equipping boots if a pair is already equipped as armor.
            @Override
            public boolean canEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
                return !entity.getEquippedStack(EquipmentSlot.FEET).isIn(SimplyBootsTags.BOOTS);
            }

            @Override
            public Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, Identifier slotIdentifier) {
                Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> modifiers =  Trinket.super.getModifiers(stack, slot, entity, slotIdentifier);
                modifiers.put(EntityAttributes.GENERIC_STEP_HEIGHT, STEP_BOOST_MODIFIER);
                if (stack.isIn(SimplyBootsTags.SPEEDY_BOOTS)) {
                    modifiers.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, SPEEDY_MODIFIER);
                }
                if (stack.isIn(SimplyBootsTags.EXTRA_SPEEDY_BOOTS)) {
                    modifiers.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, EXTRA_SPEEDY_MODIFIER);
                }
                return modifiers;
            }
        });
    }
}
