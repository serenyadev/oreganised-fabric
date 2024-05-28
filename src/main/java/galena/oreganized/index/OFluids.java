package galena.oreganized.index;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import galena.oreganized.Oreganized;
import galena.oreganized.content.block.MoltenLeadBlock;
import galena.oreganized.content.fluid.MoltenLeadFluid;
import io.github.fabricators_of_create.porting_lib.fluids.FluidType;
import io.github.fabricators_of_create.porting_lib.fluids.PortingLibFluids;
import io.github.fabricators_of_create.porting_lib.fluids.sound.SoundActions;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import io.github.fabricators_of_create.porting_lib.util.SimpleFlowableFluid;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.function.Consumer;

public class OFluids {

    public static final LazyRegistrar<Fluid> FLUIDS = LazyRegistrar.create(Registries.FLUID, Oreganized.MOD_ID);
    public static final LazyRegistrar<FluidType> TYPES = LazyRegistrar.create(PortingLibFluids.FLUID_TYPE_REGISTRY, Oreganized.MOD_ID);

    public static final RegistryObject<FluidType> MOLTEN_LEAD_TYPE = TYPES.register("molten_lead", () -> new FluidType(FluidType.Properties.create()
            .descriptionId("block.oreganized.molten_lead")
            .motionScale(0)
            .canExtinguish(false)
            .supportsBoating(false)
            .lightLevel(8)
            .density(2000)
            .temperature(1300)
            .viscosity(10000)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)));

    public static final RegistryObject<MoltenLeadFluid> MOLTEN_LEAD = FLUIDS.register("molten_lead", () -> new MoltenLeadFluid(OFluids.MOLTEN_LEAD_PROPERTIES));

    public static final SimpleFlowableFluid.Properties MOLTEN_LEAD_PROPERTIES = new SimpleFlowableFluid.Properties(MOLTEN_LEAD, MOLTEN_LEAD).bucket(OItems.MOLTEN_LEAD_BUCKET).block(OBlocks.MOLTEN_LEAD);

}
