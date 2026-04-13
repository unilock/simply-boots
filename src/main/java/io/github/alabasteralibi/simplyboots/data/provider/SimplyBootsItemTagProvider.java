package io.github.alabasteralibi.simplyboots.data.provider;

import io.github.alabasteralibi.simplyboots.registry.SimplyBootsItems;
import io.github.alabasteralibi.simplyboots.registry.SimplyBootsTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class SimplyBootsItemTagProvider extends FabricTagProvider.ItemTagProvider {
	public SimplyBootsItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
		super(output, completableFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
		getOrCreateTagBuilder(SimplyBootsTags.BOOTS)
				.add(SimplyBootsItems.HERMES_BOOTS)
				.add(SimplyBootsItems.ROCKET_BOOTS)
				.add(SimplyBootsItems.SPECTRE_BOOTS)
				.add(SimplyBootsItems.LIGHTNING_BOOTS)
				.add(SimplyBootsItems.ICE_SKATES)
				.add(SimplyBootsItems.FROSTSPARK_BOOTS)
				.add(SimplyBootsItems.WATER_WALKING_BOOTS)
				.add(SimplyBootsItems.OBSIDIAN_WATER_WALKING_BOOTS)
				.add(SimplyBootsItems.LAVA_WADERS)
				.add(SimplyBootsItems.TERRASPARK_BOOTS);
		getOrCreateTagBuilder(SimplyBootsTags.EXTRA_SPEEDY_BOOTS)
				.add(SimplyBootsItems.LIGHTNING_BOOTS)
				.add(SimplyBootsItems.FROSTSPARK_BOOTS)
				.add(SimplyBootsItems.TERRASPARK_BOOTS);
		getOrCreateTagBuilder(SimplyBootsTags.FIRE_RESISTANT_BOOTS)
				.add(SimplyBootsItems.OBSIDIAN_WATER_WALKING_BOOTS)
				.add(SimplyBootsItems.LAVA_WADERS)
				.add(SimplyBootsItems.TERRASPARK_BOOTS);
		getOrCreateTagBuilder(SimplyBootsTags.FLUID_WALKING_BOOTS)
				.add(SimplyBootsItems.WATER_WALKING_BOOTS)
				.add(SimplyBootsItems.OBSIDIAN_WATER_WALKING_BOOTS)
				.add(SimplyBootsItems.LAVA_WADERS)
				.add(SimplyBootsItems.TERRASPARK_BOOTS);
		getOrCreateTagBuilder(SimplyBootsTags.HOT_FLUID_WALKING_BOOTS)
				.add(SimplyBootsItems.LAVA_WADERS)
				.add(SimplyBootsItems.TERRASPARK_BOOTS);
		getOrCreateTagBuilder(SimplyBootsTags.ICE_SKATE_BOOTS)
				.add(SimplyBootsItems.ICE_SKATES)
				.add(SimplyBootsItems.FROSTSPARK_BOOTS)
				.add(SimplyBootsItems.TERRASPARK_BOOTS);
		getOrCreateTagBuilder(SimplyBootsTags.ROCKET_BOOTS)
				.add(SimplyBootsItems.ROCKET_BOOTS)
				.add(SimplyBootsItems.SPECTRE_BOOTS)
				.add(SimplyBootsItems.LIGHTNING_BOOTS)
				.add(SimplyBootsItems.FROSTSPARK_BOOTS)
				.add(SimplyBootsItems.TERRASPARK_BOOTS);
		getOrCreateTagBuilder(SimplyBootsTags.SPEEDY_BOOTS)
				.add(SimplyBootsItems.HERMES_BOOTS)
				.add(SimplyBootsItems.SPECTRE_BOOTS);
	}
}
