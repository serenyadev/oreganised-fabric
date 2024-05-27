package galena.oreganized;

import galena.oreganized.content.effect.StunningEffect;
import galena.oreganized.world.event.OPlayerEvents;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Oreganized implements ModInitializer {

	public static final String MOD_ID = "oreganized";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {


		OPlayerEvents.register();
		StunningEffect.registerEvents();
	}

    public static ResourceLocation id(String id) {
        return new ResourceLocation(MOD_ID, id);
    }
}
