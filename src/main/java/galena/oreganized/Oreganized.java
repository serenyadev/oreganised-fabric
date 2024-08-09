package galena.oreganized;

import com.google.common.collect.ImmutableBiMap;
import com.mojang.serialization.Codec;
import galena.oreganized.compat.CompatHandler;
import galena.oreganized.content.block.MoltenLeadCauldronBlock;
import galena.oreganized.content.effect.StunningEffect;
import galena.oreganized.index.*;
import galena.oreganized.utils.FluidInteractionRegistry;
import galena.oreganized.world.AddItemLootModifier;
import galena.oreganized.world.event.OPlayerEvents;
import io.github.fabricators_of_create.porting_lib.config.ConfigRegistry;
import io.github.fabricators_of_create.porting_lib.config.ConfigType;
import io.github.fabricators_of_create.porting_lib.fluids.PortingLibFluids;
import io.github.fabricators_of_create.porting_lib.loot.IGlobalLootModifier;
import io.github.fabricators_of_create.porting_lib.loot.PortingLibLoot;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntries;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Oreganized implements ModInitializer {

	public static final String MOD_ID = "oreganized";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static final ResourceLocation MANSION_CHEST_LOOT_LOC = new ResourceLocation("minecraft:chests/woodland_mansion");

	@Override
	public void onInitialize() {

		LazyRegistrar<?>[] registers = {
				OBlockEntities.BLOCK_ENTITIES,
				OBlocks.BLOCKS,
				OEffects.EFFECTS,
				OEntityTypes.ENTITIES,
				OFluids.FLUIDS,
				OFluids.TYPES,
				OItems.ITEMS,
				OParticleTypes.PARTICLES,
				OPotions.POTIONS,
				OSoundEvents.SOUNDS,
				OStructures.STRUCTURES,
				OFeatures.FEATURES,
				OPaintingVariants.PAINTING_VARIANTS,
				// LOOT_MODIFIERS,
		};

		CompatHandler.init();

		for (LazyRegistrar<?> register : registers) {
			register.register();
		}

		FluidInteractionRegistry.addInteraction(OFluids.MOLTEN_LEAD_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
				PortingLibFluids.WATER_TYPE,
				fluidState -> OBlocks.LEAD_BLOCK.get().defaultBlockState()
		));

		ConfigRegistry.registerConfig(MOD_ID, ConfigType.COMMON, OreganizedConfig.COMMON_SPEC);

		OPlayerEvents.register();
		StunningEffect.registerEvents();
		OFeatures.registerBiomeModifications();

		registerLootModification();
		registerCreativeTabModifiers();
		registerCauldronInteractions();
		registerWaxedBlocks();

		PotionBrewing.addMix(Potions.WATER, OItems.LEAD_INGOT.get(), OPotions.STUNNING.get());
		PotionBrewing.addMix(OPotions.STUNNING.get(), Items.REDSTONE, OPotions.LONG_STUNNING.get());
		PotionBrewing.addMix(OPotions.STUNNING.get(), Items.GLOWSTONE_DUST, OPotions.STRONG_STUNNING.get());

		FlammableBlockRegistry.getDefaultInstance().add(OBlocks.SHRAPNEL_BOMB.get(), 15, 100);

	}

	private void registerLootModification() {
		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if(MANSION_CHEST_LOOT_LOC.equals(id)) {
				LootPool pool = LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1f))
						.conditionally(LootItemRandomChanceCondition.randomChance(0.7f).build())
						.with(LootItem.lootTableItem(OItems.ELECTRUM_UPGRADE_SMITHING_TEMPLATE.get()).build())
						.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1f)))
						.build();
				tableBuilder.pool(pool);
			}
		});
	}

	private void registerCauldronInteractions() {
		Map<Item, CauldronInteraction> EMPTY = CauldronInteraction.EMPTY;
		Map<Item, CauldronInteraction> WATER = CauldronInteraction.WATER;
		Map<Item, CauldronInteraction> LAVA = CauldronInteraction.LAVA;
		Map<Item, CauldronInteraction> POWDER_SNOW = CauldronInteraction.POWDER_SNOW;
		Map<Item, CauldronInteraction> LEAD = MoltenLeadCauldronBlock.INTERACTION_MAP;

		EMPTY.put(OItems.MOLTEN_LEAD_BUCKET.get(), MoltenLeadCauldronBlock.FILL_MOLTEN_LEAD);
		WATER.put(OItems.MOLTEN_LEAD_BUCKET.get(), MoltenLeadCauldronBlock.FILL_MOLTEN_LEAD);
		LAVA.put(OItems.MOLTEN_LEAD_BUCKET.get(), MoltenLeadCauldronBlock.FILL_MOLTEN_LEAD);
		POWDER_SNOW.put(OItems.MOLTEN_LEAD_BUCKET.get(), MoltenLeadCauldronBlock.FILL_MOLTEN_LEAD);
		LEAD.put(OItems.MOLTEN_LEAD_BUCKET.get(), MoltenLeadCauldronBlock.FILL_MOLTEN_LEAD);

		EMPTY.put(OBlocks.LEAD_BLOCK.get().asItem(), MoltenLeadCauldronBlock.FILL_LEAD_BLOCK);
		WATER.put(OBlocks.LEAD_BLOCK.get().asItem(), MoltenLeadCauldronBlock.FILL_LEAD_BLOCK);
		LAVA.put(OBlocks.LEAD_BLOCK.get().asItem(), MoltenLeadCauldronBlock.FILL_LEAD_BLOCK);
		POWDER_SNOW.put(OBlocks.LEAD_BLOCK.get().asItem(), MoltenLeadCauldronBlock.FILL_LEAD_BLOCK);

		LEAD.put(Items.AIR, MoltenLeadCauldronBlock.EMPTY_LEAD_BLOCK);
		LEAD.put(Items.BUCKET, MoltenLeadCauldronBlock.EMPTY_MOLTEN_LEAD);

		CauldronInteraction.addDefaultInteractions(MoltenLeadCauldronBlock.INTERACTION_MAP);

	}

	private void registerWaxedBlocks() {
		OBlocks.WAXED_BLOCKS = new ImmutableBiMap.Builder<Block, Block>()
				.put(OBlocks.WAXED_SPOTTED_GLANCE.get(), OBlocks.SPOTTED_GLANCE.get())
				.put(OBlocks.WAXED_WHITE_CONCRETE_POWDER.get(), Blocks.WHITE_CONCRETE_POWDER)
				.put(OBlocks.WAXED_ORANGE_CONCRETE_POWDER.get(), Blocks.ORANGE_CONCRETE_POWDER)
				.put(OBlocks.WAXED_MAGENTA_CONCRETE_POWDER.get(), Blocks.MAGENTA_CONCRETE_POWDER)
				.put(OBlocks.WAXED_LIGHT_BLUE_CONCRETE_POWDER.get(), Blocks.LIGHT_BLUE_CONCRETE_POWDER)
				.put(OBlocks.WAXED_YELLOW_CONCRETE_POWDER.get(), Blocks.YELLOW_CONCRETE_POWDER)
				.put(OBlocks.WAXED_LIME_CONCRETE_POWDER.get(), Blocks.LIME_CONCRETE_POWDER)
				.put(OBlocks.WAXED_PINK_CONCRETE_POWDER.get(), Blocks.PINK_CONCRETE_POWDER)
				.put(OBlocks.WAXED_GRAY_CONCRETE_POWDER.get(), Blocks.GRAY_CONCRETE_POWDER)
				.put(OBlocks.WAXED_LIGHT_GRAY_CONCRETE_POWDER.get(), Blocks.LIGHT_GRAY_CONCRETE_POWDER)
				.put(OBlocks.WAXED_CYAN_CONCRETE_POWDER.get(), Blocks.CYAN_CONCRETE_POWDER)
				.put(OBlocks.WAXED_PURPLE_CONCRETE_POWDER.get(), Blocks.PURPLE_CONCRETE_POWDER)
				.put(OBlocks.WAXED_BLUE_CONCRETE_POWDER.get(), Blocks.BLUE_CONCRETE_POWDER)
				.put(OBlocks.WAXED_BROWN_CONCRETE_POWDER.get(), Blocks.BROWN_CONCRETE_POWDER)
				.put(OBlocks.WAXED_GREEN_CONCRETE_POWDER.get(), Blocks.GREEN_CONCRETE_POWDER)
				.put(OBlocks.WAXED_RED_CONCRETE_POWDER.get(), Blocks.RED_CONCRETE_POWDER)
				.put(OBlocks.WAXED_BLACK_CONCRETE_POWDER.get(), Blocks.BLACK_CONCRETE_POWDER)
				.build();
	}

	private void registerCreativeTabModifiers() {
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(entries -> {
			entries.addBefore(Items.DEEPSLATE, OBlocks.GLANCE.get());
			entries.addAfter( OBlocks.GLANCE.get(), OBlocks.SPOTTED_GLANCE.get());
			entries.addAfter( OBlocks.SPOTTED_GLANCE.get(), OBlocks.GLANCE_STAIRS.get());
			entries.addAfter( OBlocks.GLANCE_STAIRS.get(), OBlocks.GLANCE_SLAB.get());
			entries.addAfter( OBlocks.GLANCE_SLAB.get(), OBlocks.GLANCE_WALL.get());
			entries.addAfter( OBlocks.GLANCE_WALL.get(), OBlocks.CHISELED_GLANCE.get());
			entries.addAfter( OBlocks.CHISELED_GLANCE.get(), OBlocks.POLISHED_GLANCE.get());
			entries.addAfter( OBlocks.POLISHED_GLANCE.get(), OBlocks.POLISHED_GLANCE_STAIRS.get());
			entries.addAfter( OBlocks.POLISHED_GLANCE_STAIRS.get(), OBlocks.POLISHED_GLANCE_SLAB.get());
			entries.addAfter( OBlocks.POLISHED_GLANCE_SLAB.get(), OBlocks.GLANCE_BRICKS.get());
			entries.addAfter( OBlocks.GLANCE_BRICKS.get(), OBlocks.GLANCE_BRICK_STAIRS.get());
			entries.addAfter( OBlocks.GLANCE_BRICK_STAIRS.get(), OBlocks.GLANCE_BRICK_SLAB.get());
			entries.addAfter( OBlocks.GLANCE_BRICK_SLAB.get(), OBlocks.GLANCE_BRICK_WALL.get());
			entries.addAfter( OBlocks.GLANCE_BRICK_WALL.get(), OBlocks.WAXED_SPOTTED_GLANCE.get());
			entries.addBefore( Items.REDSTONE_BLOCK, OBlocks.SILVER_BLOCK.get());
			entries.addBefore( Items.NETHERITE_BLOCK, OBlocks.ELECTRUM_BLOCK.get());
			entries.addAfter( Items.WAXED_OXIDIZED_CUT_COPPER_SLAB, OBlocks.LEAD_BLOCK.get());
			entries.addAfter( OBlocks.LEAD_BLOCK.get(), OBlocks.CUT_LEAD.get());
			entries.addAfter( OBlocks.CUT_LEAD.get(), OBlocks.LEAD_BRICKS.get());
			entries.addAfter( OBlocks.LEAD_PILLAR.get(), OBlocks.CUT_LEAD.get());
			entries.addAfter( OBlocks.LEAD_BRICKS.get(), OBlocks.LEAD_PILLAR.get());
		});

		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COLORED_BLOCKS).register(entries -> {
			entries.addBefore( Items.SHULKER_BOX, OBlocks.WHITE_CRYSTAL_GLASS.get());
			entries.addAfter( OBlocks.WHITE_CRYSTAL_GLASS.get(), OBlocks.LIGHT_GRAY_CRYSTAL_GLASS.get());
			entries.addAfter( OBlocks.LIGHT_GRAY_CRYSTAL_GLASS.get(), OBlocks.GRAY_CRYSTAL_GLASS.get());
			entries.addAfter( OBlocks.GRAY_CRYSTAL_GLASS.get(), OBlocks.BLACK_CRYSTAL_GLASS.get());
			entries.addAfter( OBlocks.BLACK_CRYSTAL_GLASS.get(), OBlocks.BROWN_CRYSTAL_GLASS.get());
			entries.addAfter( OBlocks.BROWN_CRYSTAL_GLASS.get(), OBlocks.RED_CRYSTAL_GLASS.get());
			entries.addAfter( OBlocks.RED_CRYSTAL_GLASS.get(), OBlocks.ORANGE_CRYSTAL_GLASS.get());
			entries.addAfter( OBlocks.ORANGE_CRYSTAL_GLASS.get(), OBlocks.YELLOW_CRYSTAL_GLASS.get());
			entries.addAfter( OBlocks.YELLOW_CRYSTAL_GLASS.get(), OBlocks.LIME_CRYSTAL_GLASS.get());
			entries.addAfter( OBlocks.LIME_CRYSTAL_GLASS.get(), OBlocks.GREEN_CRYSTAL_GLASS.get());
			entries.addAfter( OBlocks.GREEN_CRYSTAL_GLASS.get(), OBlocks.CYAN_CRYSTAL_GLASS.get());
			entries.addAfter( OBlocks.CYAN_CRYSTAL_GLASS.get(), OBlocks.LIGHT_BLUE_CRYSTAL_GLASS.get());
			entries.addAfter( OBlocks.LIGHT_BLUE_CRYSTAL_GLASS.get(), OBlocks.BLUE_CRYSTAL_GLASS.get());
			entries.addAfter( OBlocks.BLUE_CRYSTAL_GLASS.get(), OBlocks.PURPLE_CRYSTAL_GLASS.get());
			entries.addAfter( OBlocks.PURPLE_CRYSTAL_GLASS.get(), OBlocks.MAGENTA_CRYSTAL_GLASS.get());
			entries.addAfter( OBlocks.MAGENTA_CRYSTAL_GLASS.get(), OBlocks.PINK_CRYSTAL_GLASS.get());
			entries.addAfter( OBlocks.PINK_CRYSTAL_GLASS.get(), OBlocks.WHITE_CRYSTAL_GLASS_PANE.get());
			entries.addAfter( OBlocks.WHITE_CRYSTAL_GLASS_PANE.get(), OBlocks.LIGHT_GRAY_CRYSTAL_GLASS_PANE.get());
			entries.addAfter( OBlocks.LIGHT_GRAY_CRYSTAL_GLASS_PANE.get(), OBlocks.GRAY_CRYSTAL_GLASS_PANE.get());
			entries.addAfter( OBlocks.GRAY_CRYSTAL_GLASS_PANE.get(), OBlocks.BLACK_CRYSTAL_GLASS_PANE.get());
			entries.addAfter( OBlocks.BLACK_CRYSTAL_GLASS_PANE.get(), OBlocks.BROWN_CRYSTAL_GLASS_PANE.get());
			entries.addAfter( OBlocks.BROWN_CRYSTAL_GLASS_PANE.get(), OBlocks.RED_CRYSTAL_GLASS_PANE.get());
			entries.addAfter( OBlocks.RED_CRYSTAL_GLASS_PANE.get(), OBlocks.ORANGE_CRYSTAL_GLASS_PANE.get());
			entries.addAfter( OBlocks.ORANGE_CRYSTAL_GLASS_PANE.get(), OBlocks.YELLOW_CRYSTAL_GLASS_PANE.get());
			entries.addAfter( OBlocks.YELLOW_CRYSTAL_GLASS_PANE.get(), OBlocks.LIME_CRYSTAL_GLASS_PANE.get());
			entries.addAfter( OBlocks.LIME_CRYSTAL_GLASS_PANE.get(), OBlocks.GREEN_CRYSTAL_GLASS_PANE.get());
			entries.addAfter( OBlocks.GREEN_CRYSTAL_GLASS_PANE.get(), OBlocks.CYAN_CRYSTAL_GLASS_PANE.get());
			entries.addAfter( OBlocks.CYAN_CRYSTAL_GLASS_PANE.get(), OBlocks.LIGHT_BLUE_CRYSTAL_GLASS_PANE.get());
			entries.addAfter( OBlocks.LIGHT_BLUE_CRYSTAL_GLASS_PANE.get(), OBlocks.BLUE_CRYSTAL_GLASS_PANE.get());
			entries.addAfter( OBlocks.BLUE_CRYSTAL_GLASS_PANE.get(), OBlocks.PURPLE_CRYSTAL_GLASS_PANE.get());
			entries.addAfter( OBlocks.PURPLE_CRYSTAL_GLASS_PANE.get(), OBlocks.MAGENTA_CRYSTAL_GLASS_PANE.get());
			entries.addAfter( OBlocks.MAGENTA_CRYSTAL_GLASS_PANE.get(), OBlocks.PINK_CRYSTAL_GLASS_PANE.get());
		});

		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> {
			entries.addAfter( Items.DEEPSLATE_COPPER_ORE, OBlocks.LEAD_ORE.get());
			entries.addAfter( OBlocks.LEAD_ORE.get(), OBlocks.DEEPSLATE_LEAD_ORE.get());
			entries.addAfter( Items.DEEPSLATE_GOLD_ORE, OBlocks.SILVER_ORE.get());
			entries.addAfter( OBlocks.SILVER_ORE.get(), OBlocks.DEEPSLATE_SILVER_ORE.get());
			entries.addAfter( Items.RAW_COPPER_BLOCK, OBlocks.RAW_LEAD_BLOCK.get());
			entries.addAfter( Items.RAW_GOLD_BLOCK, OBlocks.RAW_SILVER_BLOCK.get());
		});

		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.REDSTONE_BLOCKS).register(entries -> {
			entries.addBefore( Items.NOTE_BLOCK, OBlocks.EXPOSER.get());
			entries.addAfter( Items.TNT_MINECART, OItems.SHRAPNEL_BOMB_MINECART.get());
			entries.addAfter( Items.TNT, OBlocks.SHRAPNEL_BOMB.get());
			entries.addAfter( Blocks.REDSTONE_LAMP, OBlocks.LEAD_BULB.get());
		});

		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
			entries.addBefore( Items.NETHERITE_SHOVEL, OItems.ELECTRUM_SHOVEL.get());
			entries.addAfter( OItems.ELECTRUM_SHOVEL.get(), OItems.ELECTRUM_PICKAXE.get());
			entries.addAfter( OItems.ELECTRUM_PICKAXE.get(), OItems.ELECTRUM_AXE.get());
			entries.addAfter( OItems.ELECTRUM_AXE.get(), OItems.ELECTRUM_HOE.get());
			entries.addBefore( Items.MILK_BUCKET, OItems.MOLTEN_LEAD_BUCKET.get());
			entries.addBefore( Items.SPYGLASS, OItems.SILVER_MIRROR.get());
			entries.addAfter( Items.TNT_MINECART, OItems.SHRAPNEL_BOMB_MINECART.get());
			entries.addBefore( Items.MUSIC_DISC_5, OItems.MUSIC_DISC_STRUCTURE.get());
		});

		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(entries -> {
			entries.addBefore( Items.NETHERITE_SWORD, OItems.ELECTRUM_SWORD.get());
			entries.addBefore( Items.NETHERITE_HELMET, OItems.ELECTRUM_HELMET.get());
			entries.addAfter( OItems.ELECTRUM_HELMET.get(), OItems.ELECTRUM_CHESTPLATE.get());
			entries.addAfter( OItems.ELECTRUM_CHESTPLATE.get(), OItems.ELECTRUM_LEGGINGS.get());
			entries.addAfter( OItems.ELECTRUM_LEGGINGS.get(), OItems.ELECTRUM_BOOTS.get());
			entries.addAfter( Items.TNT, OBlocks.SHRAPNEL_BOMB.get());
		});

		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(entries -> {
			entries.addAfter( Items.RAW_COPPER, OItems.RAW_LEAD.get());
			entries.addAfter( Items.RAW_GOLD, OItems.RAW_SILVER.get());
			entries.addAfter( Items.IRON_NUGGET, OItems.LEAD_NUGGET.get());
			entries.addAfter( Items.GOLD_NUGGET, OItems.SILVER_NUGGET.get());
			entries.addBefore( Items.IRON_INGOT, OItems.ELECTRUM_NUGGET.get());
			entries.addAfter( Items.COPPER_INGOT, OItems.LEAD_INGOT.get());
			entries.addAfter( Items.GOLD_INGOT, OItems.SILVER_INGOT.get());
			entries.addBefore( Items.NETHERITE_SCRAP, OItems.ELECTRUM_INGOT.get());
			entries.addBefore( Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, OItems.ELECTRUM_UPGRADE_SMITHING_TEMPLATE.get());
		});
	}

    public static ResourceLocation id(String id) {
        return new ResourceLocation(MOD_ID, id);
    }
}
