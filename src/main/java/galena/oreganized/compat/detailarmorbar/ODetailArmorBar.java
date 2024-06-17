package galena.oreganized.compat.detailarmorbar;

import com.redlimerl.detailab.api.DetailArmorBarAPI;
import com.redlimerl.detailab.api.render.ArmorBarRenderManager;
import com.redlimerl.detailab.api.render.TextureOffset;
import galena.oreganized.Oreganized;
import galena.oreganized.index.OItems;
import net.minecraft.resources.ResourceLocation;

public class ODetailArmorBar {
    public static ResourceLocation ARMOR_ICON = Oreganized.id("textures/gui/armor_bar.png");
    public static void init() {
        DetailArmorBarAPI.customArmorBarBuilder()
                .armor(OItems.ELECTRUM_HELMET.get(),
                        OItems.ELECTRUM_CHESTPLATE.get(),
                        OItems.ELECTRUM_LEGGINGS.get(),
                        OItems.ELECTRUM_BOOTS.get())
                .render(stack -> new ArmorBarRenderManager(ARMOR_ICON, 18, 18,
                        new TextureOffset(9, 9), new TextureOffset(0, 9), new TextureOffset(9, 0), new TextureOffset(0, 0)))
                .register();
    }
}
