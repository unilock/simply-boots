package io.github.alabasteralibi.simplyboots.data.provider;

import io.github.alabasteralibi.simplyboots.SimplyBootsHelpers;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.TickCriterion;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class SimplyBootsAdvancementProvider extends FabricAdvancementProvider {
	public SimplyBootsAdvancementProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
		super(output, registryLookup);
	}

	@Override
	public void generateAdvancement(RegistryWrapper.WrapperLookup registryLookup, Consumer<AdvancementEntry> consumer) {
		consumer.accept(
				Advancement.Builder.create()
						.display(
								Items.LEATHER_BOOTS,
								Text.literal("Simply Boots"),
								Text.literal("Good luck finding all of the boots!"),
								null,
								AdvancementFrame.TASK,
								false,
								false,
								false
						)
						.criterion("tick", TickCriterion.Conditions.createTick())
						.build(SimplyBootsHelpers.id("root"))
		);
		// TODO: the rest
	}
}
