package io.github.alabasteralibi.simplyboots.data.provider;

import io.github.alabasteralibi.simplyboots.registry.SimplyBootsItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.SmithingTransformRecipe;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class SimplyBootsRecipeProvider extends FabricRecipeProvider {
	public SimplyBootsRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	public void generate(RecipeExporter exporter) {
		generateSmithingTable(exporter, SimplyBootsItems.ICE_SKATES, SimplyBootsItems.LIGHTNING_BOOTS, SimplyBootsItems.FROSTSPARK_BOOTS);
		generateSmithingTable(exporter, SimplyBootsItems.LAVA_CHARM, SimplyBootsItems.OBSIDIAN_WATER_WALKING_BOOTS, SimplyBootsItems.LAVA_WADERS);
		generateSmithingTable(exporter, SimplyBootsItems.WATER_WALKING_BOOTS, Items.WITHER_SKELETON_SKULL, SimplyBootsItems.OBSIDIAN_WATER_WALKING_BOOTS);
		generateSmithingTable(exporter, SimplyBootsItems.HERMES_BOOTS, SimplyBootsItems.ROCKET_BOOTS, SimplyBootsItems.SPECTRE_BOOTS);
		generateSmithingTable(exporter, SimplyBootsItems.FROSTSPARK_BOOTS, SimplyBootsItems.LAVA_WADERS, SimplyBootsItems.TERRASPARK_BOOTS);
	}

	private void generateSmithingTable(RecipeExporter exporter, Item inputA, Item inputB, Item result) {
		exporter.accept(
				Registries.ITEM.getId(result).withSuffixedPath("_a"),
				new SmithingTransformRecipe(
						Ingredient.EMPTY,
						Ingredient.ofItems(inputA),
						Ingredient.ofItems(inputB),
						result.getDefaultStack()
				),
				null
		);
		exporter.accept(
				Registries.ITEM.getId(result).withSuffixedPath("_b"),
				new SmithingTransformRecipe(
						Ingredient.EMPTY,
						Ingredient.ofItems(inputB),
						Ingredient.ofItems(inputA),
						result.getDefaultStack()
				),
				null
		);
	}
}
