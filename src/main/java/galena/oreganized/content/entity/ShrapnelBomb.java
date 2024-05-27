package galena.oreganized.content.entity;

import galena.oreganized.OreganizedConfig;
import galena.oreganized.index.OEffects;
import galena.oreganized.index.OEntityTypes;
import galena.oreganized.index.OParticleTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;


public class ShrapnelBomb extends PrimedTnt {

    public ShrapnelBomb(EntityType<? extends PrimedTnt> entity, Level world) {
        super(entity, world);
    }

    public ShrapnelBomb(Level world, double x, double y, double z, @Nullable LivingEntity igniterEntity) {
        this(OEntityTypes.SHRAPNEL_BOMB.get(), world);
        this.setPos(x, y, z);
        double delta = world.random.nextDouble() * (double)((float)Math.PI * 2F);
        this.setDeltaMovement(-Math.sin(delta) * 0.02D, 0.2F, -Math.cos(delta) * 0.02D);
        this.setFuse(80);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.owner = igniterEntity;
    }

    @Override
    public void tick() {
        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.04, 0.0));
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(0.98));
        if (this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7, -0.5, 0.7));
        }

        int i = this.getFuse() - 1;
        this.setFuse(i);
        if (i <= 0) {
            this.discard();
            if (!this.level().isClientSide) {
                this.explode();
            }
        } else {
            this.updateInWaterStateAndDoFluidPushing();
            if (this.level().isClientSide) {
                this.level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.5, this.getZ(), 0.0, 0.0, 0.0);
            }
        }

    }

    public void explode() {
        this.level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 4.0F, Level.ExplosionInteraction.NONE);
        if (!this.level().isClientSide()) ((ServerLevel)this.level()).sendParticles(OParticleTypes.LEAD_SHRAPNEL.get(),
                this.getX(), this.getY(0.0625D) , this.getZ(), 100, 0.0D, 0.0D, 0.0D, 5);
        for (Entity entity : this.level().getEntities(this, new AABB(this.getX() - 30, this.getY() - 4, this.getZ() - 30,
                this.getX() + 30, this.getY() + 4, this.getZ() + 30))) {
            int random = (int) (Math.random() * 100);
            boolean shouldPoison = false;
            if (entity.distanceToSqr(this) <= 16) {
                shouldPoison = true;
            } else if (entity.distanceToSqr(this) <= 64) {
                if(random < 60) shouldPoison = true;
            } else if (entity.distanceToSqr(this) <= 225) {
                if (random < 30) shouldPoison = true;
            } else if (entity.distanceToSqr(this) <= 900) {
                if (random < 5) shouldPoison = true;
            }
            if (shouldPoison && entity instanceof LivingEntity living) {
                living.hurt(this.damageSources().magic(), 2);
                if (OreganizedConfig.stunningFromConfig()) living.addEffect(new MobEffectInstance(OEffects.STUNNING.get(), 800));
                living.addEffect(new MobEffectInstance(MobEffects.POISON, 260));
            }
        }
    }
}
