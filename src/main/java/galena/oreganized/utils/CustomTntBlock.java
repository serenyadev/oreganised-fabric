package galena.oreganized.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public interface CustomTntBlock {
    void explode(Level world, BlockPos pos, @Nullable LivingEntity entity);
}
