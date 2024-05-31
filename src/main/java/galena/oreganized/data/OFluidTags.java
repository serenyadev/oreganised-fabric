package galena.oreganized.data;

import galena.oreganized.index.OFluids;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import io.github.fabricators_of_create.porting_lib.data.PortingLibTagsProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static galena.oreganized.index.OTags.Fluids.MOLTEN_LEAD;

public class OFluidTags extends PortingLibTagsProvider<Fluid> {

    public OFluidTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> future, @Nullable ExistingFileHelper helper) {
        super(output, Registries.FLUID, future, helper);
    }

    @Override
    public @NotNull String getName() {
        return "Oreganized Fluid Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(MOLTEN_LEAD).add((ResourceKey) OFluids.MOLTEN_LEAD.getKey());
    }
}
