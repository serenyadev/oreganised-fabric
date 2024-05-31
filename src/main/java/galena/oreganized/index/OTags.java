package galena.oreganized.index;

import galena.oreganized.Oreganized;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class OTags {

    public static class Items {

        public static final TagKey<Item> LEAD_SOURCE = tag("lead_source");
        public static final TagKey<Item> CRYSTAL_GLASS = tag("crystal_glass");
        public static final TagKey<Item> CRYSTAL_GLASS_PANES = tag("crystal_glass_panes");
        public static final TagKey<Item> LIGHTER_THAN_LEAD = tag("lighter_than_lead");
        public static final TagKey<Item> STONE_TYPES_GLANCE = tag("stone_types/glance");

        public static final TagKey<Item> RAW_MATERIALS_SILVER = commonTag("raw_silver");
        public static final TagKey<Item> RAW_MATERIALS_LEAD = commonTag("raw_lead");

        public static final TagKey<Item> INGOTS_SILVER = commonTag("silver_ingots");
        public static final TagKey<Item> INGOTS_LEAD = commonTag("lead_ingots");
        public static final TagKey<Item> INGOTS_ELECTRUM = commonTag("electrum_ingots");

        public static final TagKey<Item> NUGGETS_SILVER = commonTag("silver_nuggets");
        public static final TagKey<Item> NUGGETS_LEAD = commonTag("lead_nuggets");
        public static final TagKey<Item> NUGGETS_ELECTRUM = commonTag("electrum_nuggets");
        public static final TagKey<Item> NUGGETS_NETHERITE = commonTag("netherite_nuggetss");

        public static final TagKey<Item> ORES_SILVER = commonTag("silver_ores");
        public static final TagKey<Item> ORES_LEAD = commonTag("lead_ores");

        public static final TagKey<Item> STORAGE_BLOCKS_SILVER = commonTag("silver_blocks");
        public static final TagKey<Item> STORAGE_BLOCKS_LEAD = commonTag("lead_blocks");
        public static final TagKey<Item> STORAGE_BLOCKS_ELECTRUM = commonTag("electrum_blocks");

        public static final TagKey<Item> STORAGE_BLOCKS_RAW_SILVER = commonTag("raw_silver_blocks");
        public static final TagKey<Item> STORAGE_BLOCKS_RAW_LEAD = commonTag("raw_lead_blocks");
        public static final TagKey<Item> BUCKETS_MOLTEN_LEAD = commonTag("molten_lead_buckets");
        public static final TagKey<Item> TOOLS_BUSH_HAMMER = commonTag("tools/bush_hammer");

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, Oreganized.id(name));
        }
        private static TagKey<Item> commonTag(String name) {
            return TagKey.create(Registries.ITEM, new ResourceLocation("c", name));
        }
    }

    public static class Blocks {

        public static final TagKey<Block> MINEABLE_WITH_BUSH_HAMMER = tag("mineable/bush_hammer");
        public static final TagKey<Block> ENGRAVABLE = tag("engravable");
        public static final TagKey<Block> ENGRAVABLE_NEEDS_PLATE = tag("engravable/needs_plate");
        public static final TagKey<Block> FIRE_SOURCE = tag("fire_source");
        public static final TagKey<Block> CRYSTAL_GLASS = tag("crystal_glass");
        public static final TagKey<Block> CRYSTAL_GLASS_PANES = tag("crystal_glass_panes");
        public static final TagKey<Block> STONE_TYPES_GLANCE = tag("stone_types/glance");

        public static final TagKey<Block> ORES_SILVER = commonTag("silver_ores");
        public static final TagKey<Block> ORES_LEAD = commonTag("lead_ores");

        public static final TagKey<Block> STORAGE_BLOCKS_SILVER = commonTag("silver_blocks");
        public static final TagKey<Block> STORAGE_BLOCKS_LEAD = commonTag("lead_blocks");
        public static final TagKey<Block> STORAGE_BLOCKS_ELECTRUM = commonTag("electrum_blocks");

        public static final TagKey<Block> STORAGE_BLOCKS_RAW_SILVER = commonTag("raw_silver_blocks");

        public static final TagKey<Block> STORAGE_BLOCKS_RAW_LEAD = commonTag("raw_lead_blocks");

        public static final TagKey<Block> MELTS_LEAD = commonTag("melts_lead");

        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, Oreganized.id(name));
        }
        private static TagKey<Block> commonTag(String name) {
            return TagKey.create(Registries.BLOCK, new ResourceLocation("c", name));
        }
    }

    public static class Entities {

        public static final TagKey<EntityType<?>> LIGHTER_THAN_LEAD = tag("lighter_than_lead");

        private static TagKey<EntityType<?>> tag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, Oreganized.id(name));
        }
    }

    public static class Fluids {

        public static final TagKey<Fluid> MOLTEN_LEAD = commonTag("molten_lead");

        private static TagKey<Fluid> tag(String name) {
            return TagKey.create(Registries.FLUID, Oreganized.id(name));
        }

        private static TagKey<Fluid> commonTag(String name) {
            return TagKey.create(Registries.FLUID, new ResourceLocation("c", name));
        }
    }

    public static class Biomes {

        public static final TagKey<Biome> HAS_BOULDER = tag("has_structure/boulder");
        public static final TagKey<Biome> RICH_IN_LEAD_ORE = tag("rich_in_lead_ore");

        private static TagKey<Biome> tag(String name) {
            return TagKey.create(Registries.BIOME, Oreganized.id(name));
        }
    }
}
