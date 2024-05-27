package galena.oreganized.index;

import galena.oreganized.Oreganized;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class OPaintingVariants {

    public static final LazyRegistrar<PaintingVariant> PAINTING_VARIANTS = LazyRegistrar.create(Registries.PAINTING_VARIANT, Oreganized.MOD_ID);

    public static final RegistryObject<PaintingVariant> VINDICATING_BAD = PAINTING_VARIANTS.register("vindicating_bad", () -> new PaintingVariant(32,48));
}
