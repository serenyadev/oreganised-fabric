package galena.oreganized.data;

import galena.oreganized.Oreganized;
import galena.oreganized.index.OSoundEvents;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import io.github.fabricators_of_create.porting_lib.data.SoundDefinitionsProvider;
import net.minecraft.data.PackOutput;

public class OSoundDefinitions extends SoundDefinitionsProvider {

    public OSoundDefinitions(PackOutput output, ExistingFileHelper helper) {
        super(output, Oreganized.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        this.add(OSoundEvents.MUSIC_DISC_STRUCTURE, definition().with(
                sound(Oreganized.MOD_ID + ":music/disc/structure").stream()
        ));

        this.add(OSoundEvents.SHRAPNEL_BOMB_PRIMED, definition().with(
                sound("minecraft:random/fuse")
        ).subtitle("subtitles.entity.shrapnel_bomb.primed"));
    }
}