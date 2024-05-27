package galena.oreganized.utils;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import io.github.fabricators_of_create.porting_lib.event.common.FluidPlaceBlockCallback;
import io.github.fabricators_of_create.porting_lib.fluids.FluidType;
import io.github.fabricators_of_create.porting_lib.fluids.PortingLibFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public final class FluidInteractionRegistry {

    private static final Multimap<FluidType, InteractionInformation> INTERACTIONS = MultimapBuilder.hashKeys().arrayListValues().build();
    private static final List<InteractionInformation> LAVA_INTERACTIONS = new ArrayList<>();

    private FluidInteractionRegistry() {}
    public static void addInteraction(FluidType fluidType, InteractionInformation interaction) {
        if(interaction.fluidType() == PortingLibFluids.LAVA_TYPE) {
            LAVA_INTERACTIONS.add(new InteractionInformation(fluidType, interaction.callback()));
        }

        INTERACTIONS.put(fluidType, interaction);
    }

    public record InteractionInformation(FluidType fluidType, Function<FluidState, BlockState> callback) {}

    public static Optional<Couple<FluidType>> getInteractionType(Fluid fluid1, Fluid fluid2) {
        FluidType type1 = fluidTypeOf(fluid1);
        FluidType type2 = fluidTypeOf(fluid2);

        if(type1 == PortingLibFluids.EMPTY_TYPE || type2 == PortingLibFluids.EMPTY_TYPE) return Optional.empty();

        if (INTERACTIONS.containsKey(type1)) {
            for (InteractionInformation info : INTERACTIONS.get(type1)) {
                if(info.fluidType() == type2) return Optional.of(Couple.create(type1, type2));
            }
        } else if (INTERACTIONS.containsKey(type2)) {
            for(InteractionInformation info : INTERACTIONS.get(type2)) {
                if(info.fluidType() == type1) return Optional.of(Couple.create(type2, type1));
            }
        }

        return Optional.empty();
    }

    public static boolean hasFluidInteraction(Fluid fluid1, Fluid fluid2) {
        return getInteractionType(fluid1, fluid2).isPresent();
    }

    public static Optional<BlockState> getFluidInteraction(FluidState fluid1, FluidState fluid2) {
        Optional<Couple<FluidType>> interactionType = getInteractionType(fluid1.getType(), fluid2.getType());

        if(interactionType.isPresent()) {
            FluidType type1 = interactionType.get().getFirst();
            FluidType type2 = interactionType.get().getSecond();

            if(fluidTypeOf(fluid1.getType()) == type2) fluid2 = fluid1;

            for(InteractionInformation info : INTERACTIONS.get(type1)) {
                if(info.fluidType() == type2) return Optional.of(info.callback().apply(fluid2));
            }
        }

        return Optional.empty();

    }


    private static FluidType fluidTypeOf(Fluid fluid) {
        try {
            if (fluid == Fluids.LAVA || fluid == Fluids.FLOWING_LAVA) {
                return PortingLibFluids.LAVA_TYPE;
            } else if (fluid == Fluids.WATER || fluid == Fluids.FLOWING_WATER) {
                return PortingLibFluids.WATER_TYPE;
            } else {
                return fluid.getFluidType();
            }
        } catch (RuntimeException ex) {
            return PortingLibFluids.EMPTY_TYPE;
        }
    }

    private static BlockState getLavaInteraction(LevelAccessor world, BlockPos pos, BlockState blockState) {
        FluidState fluidState = blockState.getFluidState();

        for(InteractionInformation i : LAVA_INTERACTIONS) {
            if(i.fluidType() == fluidTypeOf(fluidState.getType())) return i.callback().apply(fluidState);
        }
        return null;
    }

    public static void register() {
        FluidPlaceBlockCallback.EVENT.register(FluidInteractionRegistry::getLavaInteraction);
    }


}
