package galena.oreganized.index;

import galena.oreganized.Oreganized;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;

public class OSoundEvents {

    public static final LazyRegistrar<SoundEvent> SOUNDS = LazyRegistrar.create(Registries.SOUND_EVENT, Oreganized.MOD_ID);

    public static final RegistryObject<SoundEvent> MUSIC_DISC_STRUCTURE = register("music.disc.structure");

    public static final RegistryObject<SoundEvent> SHRAPNEL_BOMB_PRIMED = register("entity.shrapnel_bomb.primed");

    private static RegistryObject<SoundEvent> register(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(Oreganized.id(name)));
    }
}
