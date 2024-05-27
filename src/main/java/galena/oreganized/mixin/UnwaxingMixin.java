package galena.oreganized.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import galena.oreganized.world.event.OPlayerEvents;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(AxeItem.class)
public class UnwaxingMixin {

    @WrapOperation(
            method = "useOn",
            at = @At(value = "INVOKE", target = "Ljava/util/Optional;ofNullable(Ljava/lang/Object;)Ljava/util/Optional;")
    )
    public Optional<?> onUnwax(Object value, Operation<Optional<?>> original) {
        InteractionResultHolder<BlockState> result = OPlayerEvents.unwax((BlockState) value);
        if(result.getResult().consumesAction()) {
            return Optional.of(result.getObject());
        }

        return original.call(value);
    }

}
