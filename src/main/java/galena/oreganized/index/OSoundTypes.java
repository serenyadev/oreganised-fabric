package galena.oreganized.index;

import io.github.fabricators_of_create.porting_lib.util.LazySoundType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;

public class OSoundTypes {
    public static final SoundType MOLTEN_LEAD = new LazySoundType(1.0F, 1.0F,  ()-> SoundEvents.BUCKET_FILL_LAVA, ()-> SoundEvents.LAVA_AMBIENT, ()-> SoundEvents.BUCKET_EMPTY_LAVA, ()-> SoundEvents.LAVA_AMBIENT, ()-> SoundEvents.LAVA_POP);
}
