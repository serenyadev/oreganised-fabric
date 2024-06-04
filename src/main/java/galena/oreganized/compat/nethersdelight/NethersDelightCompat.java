package galena.oreganized.compat.nethersdelight;

import galena.oreganized.index.OItemTiers;
import galena.oreganized.index.OItems;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTab;
import umpaz.nethersdelight.common.item.MacheteItem;
import umpaz.nethersdelight.common.registry.NDCreativeTab;
import umpaz.nethersdelight.common.registry.NDItems;

public class NethersDelightCompat {
    public static final RegistryObject<MacheteItem> ELECTRUM_MACHETE = OItems.ITEMS.register("electrum_machete", () -> new MacheteItem(OItemTiers.ELECTRUM, 2, -2.4F, new FabricItemSettings().stacksTo(1)));

    public static void init() {
        RegistryObject<CreativeModeTab> nd_tab = (RegistryObject<CreativeModeTab>) NDCreativeTab.NETHERS_DELIGHT_TAB;
        ItemGroupEvents.modifyEntriesEvent(nd_tab.getKey()).register(entries -> {
            entries.addAfter(NDItems.NETHERITE_MACHETE.get(), ELECTRUM_MACHETE.get());
        });
    }
}
