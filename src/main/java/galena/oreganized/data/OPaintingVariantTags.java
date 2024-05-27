package galena.oreganized.data;

import galena.oreganized.index.OPaintingVariants;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PaintingVariantTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.PaintingVariantTags;
import net.minecraft.world.entity.decoration.PaintingVariant;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class OPaintingVariantTags extends PaintingVariantTagsProvider {

    public OPaintingVariantTags(PackOutput output, CompletableFuture<HolderLookup.Provider> future) {
        super(output, future);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        for (RegistryObject<? extends PaintingVariant> variant : OPaintingVariants.PAINTING_VARIANTS.getEntries()) {
            this.tag(PaintingVariantTags.PLACEABLE).add((ResourceKey<PaintingVariant>) variant.getKey());
        }
    }
}
