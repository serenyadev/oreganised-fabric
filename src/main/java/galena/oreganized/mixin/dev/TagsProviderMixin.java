package galena.oreganized.mixin.dev;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.data.tags.TagsProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(TagsProvider.class)
public class TagsProviderMixin {

    @Redirect(
            method = "method_27046(Ljava/util/function/Predicate;Ljava/util/function/Predicate;Lnet/minecraft/data/CachedOutput;Ljava/util/Map$Entry;)Ljava/util/concurrent/CompletableFuture;",
            at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z")
    )
    public boolean returnTrue(List instance) {
        return true; // Bypass unnecessary check
    }

}
