package galena.oreganized.index;

import galena.oreganized.Oreganized;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

public class OPotions {

    public static final LazyRegistrar<Potion> POTIONS = LazyRegistrar.create(Registries.POTION, Oreganized.MOD_ID);

    public static final RegistryObject<Potion> STUNNING = POTIONS.register("stunning", () -> new Potion("stunning", new MobEffectInstance(OEffects.STUNNING.get(), 900)));
    public static final RegistryObject<Potion> LONG_STUNNING = POTIONS.register("long_stunning", () -> new Potion("stunning", new MobEffectInstance(OEffects.STUNNING.get(), 1800)));
    public static final RegistryObject<Potion> STRONG_STUNNING = POTIONS.register("strong_stunning", () -> new Potion("stunning", new MobEffectInstance(OEffects.STUNNING.get(), 900, 1)));
}
