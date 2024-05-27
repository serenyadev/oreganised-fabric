package galena.oreganized.client.render.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import galena.oreganized.Oreganized;
import galena.oreganized.index.OEffects;
import galena.oreganized.index.OTags;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class OGui {

    protected static final ResourceLocation STUNNING_LOCATION = Oreganized.id("textures/misc/stunning_outline.png");
    protected static final ResourceLocation STUNNING_VIGNETTE_LOCATION = Oreganized.id( "textures/misc/stunning_overlay.png");

    public static void registerEvents() {
        HudRenderCallback.EVENT.register(OGui::render);
    }

    private static void render(GuiGraphics guiGraphics, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();

        if(minecraft.player == null) return;

        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();

        RenderSystem.enableBlend();

        if (minecraft.player.hasEffect(OEffects.STUNNING.get())) {
            renderTextureOverlay(guiGraphics, STUNNING_VIGNETTE_LOCATION, screenWidth, screenHeight, 1);
            renderTextureOverlay(guiGraphics, STUNNING_LOCATION, screenWidth, screenHeight,0.8F);
        }

        if (minecraft.player.isEyeInFluid(OTags.Fluids.MOLTEN_LEAD))
            renderTextureOverlay(guiGraphics, STUNNING_VIGNETTE_LOCATION, screenWidth, screenHeight, 1);

        RenderSystem.disableBlend();
    }

    private static void renderTextureOverlay(GuiGraphics guiGraphics, ResourceLocation loc, int screenWidth, int screenHeight, float alpha) {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, alpha);
        guiGraphics.blit(loc, 0, 0, -90, 0.0F, 0.0F, screenWidth, screenHeight, screenWidth, screenHeight);
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
