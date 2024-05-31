package galena.oreganized.mixin;

import galena.oreganized.utils.CustomTntBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TntBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TntBlock.class)
public class TntBlockMixin {

    @Inject(
            method = "explode(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/LivingEntity;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void redirectExplode(Level level, BlockPos pos, LivingEntity entity, CallbackInfo ci) {
        Block block = level.getBlockState(pos).getBlock();
        if(block instanceof CustomTntBlock tnt) {
            tnt.explode(level, pos, entity);
            ci.cancel();
        }
    }


}
