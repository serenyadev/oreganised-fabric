package galena.oreganized.index;

import galena.oreganized.Oreganized;
import galena.oreganized.content.effect.StunningEffect;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;

public class OEffects {

    public static final LazyRegistrar<MobEffect> EFFECTS = LazyRegistrar.create(Registries.MOB_EFFECT, Oreganized.MOD_ID);

    public static final RegistryObject<MobEffect> STUNNING = EFFECTS.register("stunning", StunningEffect::new);
}
