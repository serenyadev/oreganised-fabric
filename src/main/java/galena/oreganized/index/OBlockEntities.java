package galena.oreganized.index;

import galena.oreganized.Oreganized;
import galena.oreganized.content.entity.ExposerBlockEntity;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class OBlockEntities {

    public static final LazyRegistrar<BlockEntityType<?>> BLOCK_ENTITIES = LazyRegistrar.create(Registries.BLOCK_ENTITY_TYPE, Oreganized.MOD_ID);

    public static final RegistryObject<BlockEntityType<?>> EXPOSER = BLOCK_ENTITIES.register("exposer", () -> BlockEntityType.Builder.of(ExposerBlockEntity::new, OBlocks.EXPOSER.get()).build(null));

}
