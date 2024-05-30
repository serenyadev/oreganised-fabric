package galena.oreganized.client;

import galena.oreganized.Oreganized;
import galena.oreganized.OreganizedConfig;
import galena.oreganized.client.render.entity.ShrapnelBombMinecartRender;
import galena.oreganized.client.render.entity.ShrapnelBombRender;
import galena.oreganized.client.render.gui.OGui;
import galena.oreganized.index.*;
import io.github.fabricators_of_create.porting_lib.config.ConfigRegistry;
import io.github.fabricators_of_create.porting_lib.config.ConfigType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class OreganizedClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        OGui.registerEvents();
        registerBlockRenderers();
        registerEntityRenderers();
        OParticleTypes.registerParticleFactories();

        ConfigRegistry.registerConfig(Oreganized.MOD_ID, ConfigType.CLIENT, OreganizedConfig.CLIENT_SPEC);

        FluidRenderHandlerRegistry.INSTANCE.register(OFluids.MOLTEN_LEAD.get(), new SimpleFluidRenderHandler(
                Oreganized.id("block/fluid/molten_lead"),
                Oreganized.id("block/fluid/molten_lead_flowing"),
                Oreganized.id("block/fluid/molten_lead_flowing"),
                FastColor.ARGB32.color(255, 57, 25, 80)
        ));

        ItemProperties.register(OItems.SILVER_MIRROR.get(), new ResourceLocation("level"), (stack, world, entity, seed) -> {
            if (entity == null) {
                return 8;
            } else {
                return stack.getOrCreateTag().getInt("Level");
            }
        });
    }

    private static void render(Supplier<? extends Block> block, RenderType render) {
        BlockRenderLayerMap.INSTANCE.putBlock(block.get(), render);
    }

    public static void registerBlockRenderers() {
        RenderType cutout = RenderType.cutout();
        RenderType mipped = RenderType.cutoutMipped();
        RenderType translucent = RenderType.translucent();

        for (int i = 0; OBlocks.CRYSTAL_GLASS.size() > i; i++) {
            render(OBlocks.CRYSTAL_GLASS.get(i), translucent);
            render(OBlocks.CRYSTAL_GLASS_PANES.get(i), translucent);
        }
    }


    public static void registerEntityRenderers() {
        EntityRendererRegistry.register(OEntityTypes.SHRAPNEL_BOMB.get(), ShrapnelBombRender::new);
        EntityRendererRegistry.register(OEntityTypes.SHRAPNEL_BOMB_MINECART.get(), ShrapnelBombMinecartRender::new);
    }
}
