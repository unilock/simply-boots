package io.github.alabasteralibi.simplyboots;

import io.github.alabasteralibi.simplyboots.components.BootComponents;
import io.github.alabasteralibi.simplyboots.components.ClampedBootIntComponent;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsItems;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsTags;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.ResourceManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;

public class SimplyBoots implements ModInitializer {
    public static final ItemGroup MAIN_GROUP = Registry.register(
            Registries.ITEM_GROUP,
            SimplyBootsHelpers.id("main_group"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemGroup.simplyboots.main_group"))
                    .icon(SimplyBootsItems.TERRASPARK_BOOTS::getDefaultStack)
                    .build()
    );

    @Override
    public void onInitialize() {
        // Adds rocket boots trade to armorer villagers
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER, 1, factories -> factories.add(
                (entity, random) -> new TradeOffer(new ItemStack(Items.LEATHER_BOOTS), new ItemStack(Items.EMERALD, 10), new ItemStack(SimplyBootsItems.ROCKET_BOOTS), 4, 10, 0.05F)
        ));

        LootTableEvents.MODIFY.register(this::setupLootTableAdditions);

        // Registers a packet for when clients wish to activate their rocket boots
        ServerPlayNetworking.registerGlobalReceiver(SimplyBootsHelpers.id("rocket_boost"),
                (server, player, handler, buf, responseSender) -> {
                    if (!SimplyBootsHelpers.wearingBoots(player, SimplyBootsTags.ROCKET_BOOTS)) {
                        return;
                    }
                    if (player.isOnGround()) {
                        return;
                    }

                    ClampedBootIntComponent rocketTicks = BootComponents.ROCKET_BOOTS.get(player);
                    if (rocketTicks.getValue() > 0) {
                        rocketTicks.decrement();
                    } else {
                        return;
                    }

                    Vec3d velocity = player.getVelocity();
                    if (player.isFallFlying()) {
                        rocketTicks.decrement();
                        rocketTicks.decrement();
                        Vec3d vec3d = player.getRotationVector();
                        player.addVelocity(vec3d.x * 0.1D + (vec3d.x * 1.5D - velocity.x) * 0.5D,
                                vec3d.y * 0.1D + (vec3d.y * 1.5D - velocity.y) * 0.5D,
                                vec3d.z * 0.1D + (vec3d.z * 1.5D - velocity.z) * 0.5D);
                    } else {
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 3, 5, true, false, false));
                    }
                });
    }

    private void setupLootTableAdditions(ResourceManager resourceManager, LootManager lootManager, Identifier id, LootTable.Builder tableBuilder, LootTableSource source) {
        if (source.isBuiltin()) {
            if (id.equals(LootTables.SIMPLE_DUNGEON_CHEST)) {
                LootPool.Builder pool = LootPool.builder()
                        .with(ItemEntry.builder(SimplyBootsItems.HERMES_BOOTS).weight(1))
                        .with(ItemEntry.builder(ItemStack.EMPTY.getItem()).weight(2));

                tableBuilder.pool(pool);
            }

            if (id.equals(LootTables.BURIED_TREASURE_CHEST)) {
                LootPool.Builder pool = LootPool.builder()
                        .with(ItemEntry.builder(SimplyBootsItems.WATER_WALKING_BOOTS).weight(1))
                        .with(ItemEntry.builder(ItemStack.EMPTY.getItem()).weight(2));

                tableBuilder.pool(pool);
            }

            if (id.equals(LootTables.SHIPWRECK_TREASURE_CHEST)) {
                LootPool.Builder pool = LootPool.builder()
                        .with(ItemEntry.builder(SimplyBootsItems.WATER_WALKING_BOOTS).weight(1))
                        .with(ItemEntry.builder(ItemStack.EMPTY.getItem()).weight(19));

                tableBuilder.pool(pool);
            }

            if (id.equals(LootTables.IGLOO_CHEST_CHEST)) {
                LootPool.Builder pool = LootPool.builder()
                        .with(ItemEntry.builder(SimplyBootsItems.ICE_SKATES));

                tableBuilder.pool(pool);
            }

            if (id.equals(LootTables.BASTION_TREASURE_CHEST)) {
                LootPool.Builder pool = LootPool.builder()
                        .with(ItemEntry.builder(SimplyBootsItems.LAVA_CHARM).weight(1))
                        .with(ItemEntry.builder(ItemStack.EMPTY.getItem()).weight(2));

                tableBuilder.pool(pool);
            }
        }
    }
}