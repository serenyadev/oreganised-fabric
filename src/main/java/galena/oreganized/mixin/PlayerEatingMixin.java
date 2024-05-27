package galena.oreganized.mixin;

import galena.oreganized.world.event.OPlayerEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerEatingMixin {

    @Inject(
            method = "eat",
            at = @At("RETURN")
    )
    public void onEating(Level level, ItemStack food, CallbackInfoReturnable<ItemStack> cir) {
        OPlayerEvents.onFinishEating((LivingEntity) (Object) this, food);
    }
}
