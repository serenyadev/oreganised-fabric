package galena.oreganized.data;

import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.damagesource.DamageType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ODamageTags extends TagsProvider<DamageType> {

    public ODamageTags(PackOutput output, CompletableFuture<HolderLookup.Provider> future) {
        super(output, Registries.DAMAGE_TYPE, future);
    }

    @Override
    public @NotNull String getName() {
        return "Oreganized DamageType Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
    }
}
