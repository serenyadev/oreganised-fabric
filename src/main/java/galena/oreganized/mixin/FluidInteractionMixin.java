package galena.oreganized.mixin;

import galena.oreganized.utils.FluidInteractionRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(FlowingFluid.class)
public abstract class FluidInteractionMixin {

    @Inject(
            method = "spread",
            at = @At("HEAD")
    )
    public void onFluidInteraction(Level level, BlockPos pos, FluidState state, CallbackInfo ci) {
        if(!state.isEmpty()) {
            for(Direction direction : Direction.values()) {
                BlockPos relative = pos.relative(direction);

                FluidState otherState = level.getFluidState(relative);
                FluidInteractionRegistry.getFluidInteraction(state, otherState).ifPresent(result -> {
                    level.setBlock(pos, result, 3);
                    level.levelEvent(1501, relative, 0);
                });
            }
        }
    }


}
