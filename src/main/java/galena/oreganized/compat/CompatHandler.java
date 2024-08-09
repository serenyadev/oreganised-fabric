package galena.oreganized.compat;

import galena.oreganized.compat.detailarmorbar.ODetailArmorBar;
import galena.oreganized.compat.farmersdelight.FarmersDelightCompat;
import galena.oreganized.compat.nethersdelight.NethersDelightCompat;
import net.fabricmc.loader.api.FabricLoader;

public class CompatHandler {

    public static final boolean FARMERS_DELIGHT = FabricLoader.getInstance().isModLoaded("farmersdelight");
    public static final boolean NETHERS_DELIGHT = FabricLoader.getInstance().isModLoaded("nethersdelight");
    public static final boolean DETAIL_ARMOR_BAR = FabricLoader.getInstance().isModLoaded("detailab");


    public static void init() {
        if(FARMERS_DELIGHT) FarmersDelightCompat.init();
        if(NETHERS_DELIGHT) NethersDelightCompat.init();
    }

    public static void initClient() {
        if(DETAIL_ARMOR_BAR) ODetailArmorBar.init();
    }
}
