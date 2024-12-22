package nazario.liby.api.registry.runtime.tags;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagEntry;
import net.minecraft.registry.tag.TagGroupLoader;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LibyTagRegistry {

    @ApiStatus.Internal
    private static HashMap<String, HashMap<Identifier, List<TagGroupLoader.TrackedEntry>>> MAP = new HashMap<>();

    public static HashMap<String, HashMap<Identifier, List<TagGroupLoader.TrackedEntry>>> getMap() {
        return MAP;
    }

    public static void add(String tagType, Identifier tag, String source, TagEntry... tagEntries) {
        // Initialize a list to store tracked entries
        List<TagGroupLoader.TrackedEntry> list = new ArrayList<>();

        // Convert TagEntry objects to TrackedEntry objects and add to the list
        for (TagEntry tagEntry : tagEntries) {
            list.add(new TagGroupLoader.TrackedEntry(tagEntry, source));
        }

        // Retrieve existing entries for the given tagType and tag, if any
        HashMap<Identifier, List<TagGroupLoader.TrackedEntry>> tagMap = MAP.getOrDefault(tagType, new HashMap<>());
        List<TagGroupLoader.TrackedEntry> existingEntries = tagMap.getOrDefault(tag, new ArrayList<>());

        // Add existing entries to the list
        list.addAll(existingEntries);

        // Update the tagMap with the combined list
        tagMap.put(tag, list);

        // Update the main map with the updated tagMap
        MAP.put(tagType, tagMap);
    }

    public static void addBlocks(Identifier tag, String source, Block @NotNull ... blocks) {
        List<TagEntry> tagEntries = new ArrayList<>();

        for (Block block : blocks) {
            tagEntries.add(TagEntry.create(block.getRegistryEntry().getKey().get().getValue()));
        }

        LibyTagRegistry.add(TagTypes.BLOCKS, tag, source, tagEntries.toArray(TagEntry[]::new));
    }

    public static void addItems(Identifier tag, String source, Item @NotNull ... items) {
        List<TagEntry> tagEntries = new ArrayList<>();

        for (Item item : items) {
            tagEntries.add(TagEntry.create(item.liby$getId()));
        }

        LibyTagRegistry.add(TagTypes.ITEMS, tag, source, tagEntries.toArray(TagEntry[]::new));
    }

    public static final class TagTypes {
        public static final String WORLDGEN_STRUCTURE_SET = "tags/worldgen/structure_set";
        public static final String WORLDGEN_DENSITY_FUNCTION = "tags/worldgen/density_function";
        public static final String WORLDGEN_PLACED_FEATURE = "tags/worldgen/placed_feature";
        public static final String WORLDGEN_CONFIGURED_FEATURE = "tags/worldgen/configured_feature";
        public static final String DIMENSION_TYPE = "tags/dimension_type";
        public static final String WORLDGEN_NOISE = "tags/worldgen/noise";
        public static final String WORLDGEN_CONFIGURED_CARVER = "tags/worldgen/configured_carver";
        public static final String WORLDGEN_FLAT_LEVEL_GENERATOR_PRESET = "tags/worldgen/flat_level_generator_preset";
        public static final String WORLDGEN_WORLD_PRESET = "tags/worldgen/world_preset";
        public static final String WORLDGEN_STRUCTURE = "tags/worldgen/structure";
        public static final String WORLDGEN_BIOME = "tags/worldgen/biome";
        public static final String WORLDGEN_NOISE_SETTINGS = "tags/worldgen/noise_settings";
        public static final String SOUND_EVENT = "tags/sound_event";
        public static final String WORLDGEN_PROCESSOR_LIST = "tags/worldgen/processor_list";
        public static final String MOB_EFFECT = "tags/mob_effect";
        public static final String ENTITY_TYPES = "tags/entity_types";
        public static final String ITEMS = "tags/items";
        public static final String PARTICLE_TYPE = "tags/particle_type";
        public static final String POTION = "tags/potion";
        public static final String ENCHANTMENT = "tags/enchantment";
        public static final String FLUIDS = "tags/fluids";
        public static final String BLOCK_ENTITY_TYPE = "tags/block_entity_type";
        public static final String CUSTOM_STAT = "tags/custom_stat";
        public static final String RECIPE_SERIALIZER = "tags/recipe_serializer";
        public static final String ATTRIBUTE = "tags/attribute";
        public static final String BLOCKS = "tags/blocks";
        public static final String POSITION_SOURCE_TYPE = "tags/position_source_type";
        public static final String COMMAND_ARGUMENT_TYPE = "tags/command_argument_type";
        public static final String STAT_TYPE = "tags/stat_type";
        public static final String VILLAGER_TYPE = "tags/villager_type";
        public static final String VILLAGER_PROFESSION = "tags/villager_profession";
        public static final String RULE_TEST = "tags/rule_test";
        public static final String SENSOR_TYPE = "tags/sensor_type";
        public static final String SCHEDULE = "tags/schedule";
        public static final String ACTIVITY = "tags/activity";
        public static final String LOOT_POOL_ENTRY_TYPE = "tags/loot_pool_entry_type";
        public static final String LOOT_FUNCTION_TYPE = "tags/loot_function_type";
        public static final String LOOT_CONDITION_TYPE = "tags/loot_condition_type";
        public static final String LOOT_NUMBER_PROVIDER_TYPE = "tags/loot_number_provider_type";
        public static final String LOOT_NBT_PROVIDER_TYPE = "tags/loot_nbt_provider_type";
        public static final String LOOT_SCORE_PROVIDER_TYPE = "tags/loot_score_provider_type";
        public static final String FLOAT_PROVIDER_TYPE = "tags/float_provider_type";
        public static final String INT_PROVIDER_TYPE = "tags/int_provider_type";
        public static final String HEIGHT_PROVIDER_TYPE = "tags/height_provider_type";
        public static final String BLOCK_PREDICATE_TYPE = "tags/block_predicate_type";
        public static final String WORLDGEN_CARVER = "tags/worldgen/carver";
        public static final String WORLDGEN_FEATURE = "tags/worldgen/feature";
        public static final String WORLDGEN_STRUCTURE_PLACEMENT = "tags/worldgen/structure_placement";
        public static final String WORLDGEN_STRUCTURE_PIECE = "tags/worldgen/structure_piece";
        public static final String WORLDGEN_STRUCTURE_TYPE = "tags/worldgen/structure_type";
        public static final String WORLDGEN_PLACEMENT_MODIFIER_TYPE = "tags/worldgen/placement_modifier_type";
        public static final String WORLDGEN_BLOCK_STATE_PROVIDER_TYPE = "tags/worldgen/block_state_provider_type";
        public static final String WORLDGEN_FOLIAGE_PLACER_TYPE = "tags/worldgen/foliage_placer_type";
        public static final String WORLDGEN_TRUNK_PLACER_TYPE = "tags/worldgen/trunk_placer_type";
        public static final String WORLDGEN_ROOT_PLACER_TYPE = "tags/worldgen/root_placer_type";
        public static final String WORLDGEN_TREE_DECORATOR_TYPE = "tags/worldgen/tree_decorator_type";
        public static final String WORLDGEN_FEATURE_SIZE_TYPE = "tags/worldgen/feature_size_type";
        public static final String WORLDGEN_BIOME_SOURCE = "tags/worldgen/biome_source";
        public static final String MENU = "tags/menu";
        public static final String WORLDGEN_MATERIAL_CONDITION = "tags/worldgen/material_condition";
        public static final String RECIPE_TYPE = "tags/recipe_type";
        public static final String WORLDGEN_DENSITY_FUNCTION_TYPE = "tags/worldgen/density_function_type";
        public static final String WORLDGEN_STRUCTURE_PROCESSOR = "tags/worldgen/structure_processor";
        public static final String WORLDGEN_STRUCTURE_POOL_ELEMENT = "tags/worldgen/structure_pool_element";
        public static final String CAT_VARIANT = "tags/cat_variant";
        public static final String FROG_VARIANT = "tags/frog_variant";
        public static final String BANNER_PATTERN = "tags/banner_pattern";
        public static final String INSTRUMENT = "tags/instrument";
        public static final String PAINTING_VARIANT = "tags/painting_variant";
        public static final String WORLDGEN_MATERIAL_RULE = "tags/worldgen/material_rule";
        public static final String FUNCTIONS = "tags/functions";
        public static final String WORLDGEN_CHUNK_GENERATOR = "tags/worldgen/chunk_generator";
        public static final String POINT_OF_INTEREST_TYPE = "tags/point_of_interest_type";
        public static final String CHUNK_STATUS = "tags/chunk_status";
        public static final String GAME_EVENTS = "tags/game_events";
        public static final String POS_RULE_TEST = "tags/pos_rule_test";
        public static final String MEMORY_MODULE_TYPE = "tags/memory_module_type";
        public static final String WORLDGEN_TEMPLATE_POOL = "tags/worldgen/template_pool";
        public static final String CHAT_TYPE = "tags/chat_type";
    }

}
