package company.sappho.mod_id;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;

public class ModID implements ModInitializer {

	@Override
	public void onInitialize() {
		Constants.LOGGER.info("Hello Fabric world!");
	}

    public static ResourceLocation asLocation(String id) {
        return new ResourceLocation(Constants.MOD_ID, id);
    }
}
