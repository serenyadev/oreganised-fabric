package galena.oreganized.index;

import galena.oreganized.Oreganized;
import galena.oreganized.client.particle.CustomDrippingParticle;
import galena.oreganized.client.particle.LeadShrapnelParticle;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;

public class OParticleTypes {

    public static final LazyRegistrar<ParticleType<?>> PARTICLES = LazyRegistrar.create(Registries.PARTICLE_TYPE, Oreganized.MOD_ID);

    public static final RegistryObject<SimpleParticleType> DRIPPING_LEAD = PARTICLES.register("dripping_lead", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> FALLING_LEAD = PARTICLES.register("falling_lead", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> LANDING_LEAD = PARTICLES.register("landing_lead", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> LEAD_SHRAPNEL = PARTICLES.register( "lead_shrapnel", () -> new SimpleParticleType(true));



    public static void registerParticleFactories() {

        ParticleFactoryRegistry registry = ParticleFactoryRegistry.getInstance();

        registry.register(DRIPPING_LEAD.get(), CustomDrippingParticle.LeadHangProvider::new);
        registry.register(FALLING_LEAD.get(), CustomDrippingParticle.LeadFallProvider::new);
        registry.register(LANDING_LEAD.get(), CustomDrippingParticle.LeadLandProvider::new);
        registry.register(LEAD_SHRAPNEL.get(), LeadShrapnelParticle.Provider::new);
    }
}
