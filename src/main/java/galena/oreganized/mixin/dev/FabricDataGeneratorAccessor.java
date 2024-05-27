package galena.oreganized.mixin.dev;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.HolderLookup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.concurrent.CompletableFuture;

@Mixin(value = FabricDataGenerator.class, remap = false)
public interface FabricDataGeneratorAccessor {

    @Accessor("registriesFuture")
    CompletableFuture<HolderLookup.Provider> getRegistriesFuture();

}
