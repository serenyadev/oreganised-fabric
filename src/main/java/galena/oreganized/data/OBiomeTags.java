package galena.oreganized.data;

import galena.oreganized.index.OTags;
import io.github.fabricators_of_create.porting_lib.tags.Tags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class OBiomeTags extends BiomeTagsProvider {

    public OBiomeTags(PackOutput output, CompletableFuture<HolderLookup.Provider> future) {
        super(output, future);
    }

    @Override
    public @NotNull String getName() {
        return "Oreganized Biome Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(OTags.Biomes.HAS_BOULDER)
                .addTag(OTags.Biomes.RICH_IN_LEAD_ORE)
                .addTag(Tags.Biomes.IS_PLAINS);
        tag(OTags.Biomes.RICH_IN_LEAD_ORE).addTag(BiomeTags.IS_SAVANNA);
    }
}
