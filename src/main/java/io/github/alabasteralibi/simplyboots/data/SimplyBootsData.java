package io.github.alabasteralibi.simplyboots.data;

import io.github.alabasteralibi.simplyboots.SimplyBoots;
import io.github.alabasteralibi.simplyboots.data.provider.SimplyBootsAdvancementProvider;
import io.github.alabasteralibi.simplyboots.data.provider.SimplyBootsItemTagProvider;
import io.github.alabasteralibi.simplyboots.data.provider.SimplyBootsRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.jetbrains.annotations.Nullable;

public class SimplyBootsData implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(SimplyBootsAdvancementProvider::new);
		pack.addProvider(SimplyBootsItemTagProvider::new);
		pack.addProvider(SimplyBootsRecipeProvider::new);
	}

	@Override
	public @Nullable String getEffectiveModId() {
		return SimplyBoots.MOD_ID;
	}
}
