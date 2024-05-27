package galena.oreganized.data;

import galena.oreganized.index.OFeatures;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

public class OreganizedData implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {

		ExistingFileHelper helper = ExistingFileHelper.withResourcesFromArg();
		FabricDataGenerator.Pack pack = generator.createPack();

		pack.addProvider(OBiomeTags::new);
		pack.addProvider(OBlockTags::new);
		pack.addProvider(OEntityTags::new);
		pack.addProvider(ODamageTags::new);
		pack.addProvider(providerWithHelper2(OFluidTags::new, helper));
		pack.addProvider((FabricDataGenerator.Pack.Factory<OLang>) OLang::new);
		pack.addProvider(ORegistries::new);
		pack.addProvider((FabricDataGenerator.Pack.Factory<ORecipes>) ORecipes::new);
		pack.addProvider((FabricDataGenerator.Pack.Factory<OLootTables>) OLootTables::new);
		pack.addProvider(providerWithHelper(OSoundDefinitions::new, helper));
		pack.addProvider(OAdvancements::new);
		pack.addProvider(OPaintingVariantTags::new);
		pack.addProvider(providerWithHelper(OBlockStates::new, helper));
		pack.addProvider(providerWithHelper(OItemModels::new, helper));

	}

	private <T extends DataProvider> FabricDataGenerator.Pack.Factory<T> providerWithHelper(BiFunction<PackOutput, ExistingFileHelper, T> factory, ExistingFileHelper helper) {
		return out -> factory.apply(out, helper);
	}

	private <T extends DataProvider> FabricDataGenerator.Pack.RegistryDependentFactory<T> providerWithHelper2(RegistryDependentFactoryWithHelper<T> factory, ExistingFileHelper helper) {
		return (out, registries) -> factory.create(out, registries, helper);
	}

	public interface RegistryDependentFactoryWithHelper<T extends DataProvider> {
		T create(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesLookup, @Nullable ExistingFileHelper helper);
	}
}
