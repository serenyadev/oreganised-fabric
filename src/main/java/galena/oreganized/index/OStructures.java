package galena.oreganized.index;

import com.mojang.serialization.Codec;
import galena.oreganized.Oreganized;
import galena.oreganized.world.gen.structure.BoulderStructure;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;


public class OStructures {

    public static final LazyRegistrar<StructureType<?>> STRUCTURES = LazyRegistrar.create(Registries.STRUCTURE_TYPE, Oreganized.MOD_ID);

    public static final RegistryObject<StructureType<BoulderStructure>> BOULDER = STRUCTURES.register("boulder", () -> explicitStructureTypeTyping(BoulderStructure.CODEC));

    private static <T extends Structure> StructureType<T> explicitStructureTypeTyping(Codec<T> structureCodec) {
        return () -> structureCodec;
    }
}