package galena.oreganized.data;

import galena.oreganized.index.OTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;

import java.util.concurrent.CompletableFuture;

public class OEntityTags extends EntityTypeTagsProvider {

    public OEntityTags(PackOutput output, CompletableFuture<HolderLookup.Provider> future) {
        super(output, future);
    }

    @Override
    public String getName() {
        return "Oreganized Entity Type Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Oreganized
        tag(OTags.Entities.LIGHTER_THAN_LEAD).add(EntityType.IRON_GOLEM);

        // Vanilla
        //tag(EntityTypeTags.IMPACT_PROJECTILES).add(OEntityTypes.LEAD_BOLT.get());
    }
}
