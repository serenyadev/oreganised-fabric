package galena.oreganized.content.effect;

import galena.oreganized.Oreganized;
import galena.oreganized.index.OEffects;
import io.github.fabricators_of_create.porting_lib.client_events.event.client.MovementInputUpdateCallback;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents;
import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import net.fabricmc.api.EnvType;
import net.minecraft.client.player.Input;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class StunningEffect extends MobEffect {

    public static boolean flag = false; // Flag to check if entity should be paralysed
    public static int coolDown = 0;
    public StunningEffect() {
        super(MobEffectCategory.HARMFUL, 0x3B3B63);
    }

    public static void registerEvents() {
        LivingEntityEvents.LivingTickEvent.TICK.register(StunningEffect::applyStunnedPlayer);
        EnvExecutor.runWhenOn(EnvType.CLIENT, () -> () -> MovementInputUpdateCallback.EVENT.register(StunningEffect::applyStunnedPlayer));
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        if (coolDown <= 0) {
            // amplifier multiplies only the time frozen, making the relative gap larger the higher the level
            coolDown = entity.level().getRandom().nextInt(120 * (flag ? amplifier + 1 : 1)) + 20;
            flag = !flag; // Toggle flag, if should be paralysed flag = true, else flag = false
        }
        coolDown--; // cool down is decremented every in game tick
    }

    // applyStunned for Mobs
    public static void applyStunnedPlayer(LivingEntityEvents.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if ((!(entity instanceof Player)) && entity.hasEffect(OEffects.STUNNING.get()) && flag) {
            // Copied from LivingEntity.aiStep() when isImmobile() is true
            entity.setJumping(false);
            entity.xxa = 0.0F;
            entity.zza = 0.0F;
        }
    }

     // applyStunned for Players
    public static void applyStunnedPlayer(Player player, Input input) {
        if (player.hasEffect(OEffects.STUNNING.get()) && flag) {
            // Disable all movement related input by setting it to false or 0
            input.up = false;
            input.down = false;
            input.left = false;
            input.right = false;
            input.forwardImpulse = 0;
            input.leftImpulse = 0;
            input.jumping = false;
            input.shiftKeyDown = false;
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return this == OEffects.STUNNING.get();
    }
}
