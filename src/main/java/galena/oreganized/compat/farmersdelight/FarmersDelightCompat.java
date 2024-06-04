package galena.oreganized.compat.farmersdelight;

import galena.oreganized.index.OItemTiers;
import galena.oreganized.index.OItems;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTab;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.item.KnifeItem;
import vectorwing.farmersdelight.common.registry.ModCreativeTabs;
import vectorwing.farmersdelight.common.registry.ModItems;

@SuppressWarnings("unused")
public class FarmersDelightCompat {

    public static final RegistryObject<KnifeItem> ELECTRUM_KNIFE = OItems.ITEMS.register("electrum_knife", () -> new KnifeItem(
            OItemTiers.ELECTRUM,
            0.5F,
            -1.8f,
            new FabricItemSettings().stacksTo(1)
    ));

    public static void init() {
        RegistryObject<CreativeModeTab> fd_tab = (RegistryObject<CreativeModeTab>) ModCreativeTabs.TAB_FARMERS_DELIGHT;
        ItemGroupEvents.modifyEntriesEvent(fd_tab.getKey()).register(items -> {
            items.addAfter(ModItems.NETHERITE_KNIFE.get(), ELECTRUM_KNIFE.get());
        });
    }
}
