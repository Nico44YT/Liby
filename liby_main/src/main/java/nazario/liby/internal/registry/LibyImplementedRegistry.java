package nazario.liby.internal.registry;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import nazario.liby.api.annotations.Unimplemented;
import nazario.liby.api.recipe.LibyRecipe;
import nazario.liby.api.registry.helper.*;
import nazario.liby.api.tag.TagTypesList;
import nazario.liby.internal.LibyRuntimeErrors;
import nazario.liby.internal.injections.LibyIdentifierResolvable;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public class LibyImplementedRegistry implements LibyItemRegistry, LibyBlockRegistry, LibyBlockEntityRegistry, LibySoundRegistry, LibyEntityTypeRegistry, LibyTagRegistry, LibyRecipeRegistry {

	private static final ConcurrentHashMap<String, LibyImplementedRegistry> CACHE = new ConcurrentHashMap<>();
	private final String namespace;

	protected LibyImplementedRegistry(String namespace) {
		if(!Identifier.isNamespaceValid(namespace)) throw LibyRuntimeErrors.invalidRegistryNamespace(namespace);
		this.namespace = namespace;
	}

	public static @NotNull LibyItemRegistry ofItems(String namespace) {
		return cached(namespace);
	}
	public static @NotNull LibyBlockRegistry ofBlocks(String namespace) {
		return cached(namespace);
	}
	public static @NotNull LibyBlockEntityRegistry ofBlockEntityTypes(String namespace) {
		return cached(namespace);
	}
	public static @NotNull LibySoundRegistry ofSounds(String namespace) {
		return cached(namespace);
	}
	public static @NotNull LibyEntityTypeRegistry ofEntityTypes(String namespace) {
		return cached(namespace);
	}
	public static @NotNull LibyRecipeRegistry ofRecipes(String namespace){
		return cached(namespace);
	}
	public static @NotNull LibyTagRegistry ofTags(String namespace) {
		return cached(namespace);
	}

	public static @NotNull LibyImplementedRegistry ofGeneric(String namespace) {
		return cached(namespace);
	}

	private static @NotNull LibyImplementedRegistry cached(String namespace) {
		if (!Identifier.isNamespaceValid(namespace)) throw LibyRuntimeErrors.invalidRegistryNamespace(namespace);
		return CACHE.computeIfAbsent(namespace, k -> new LibyImplementedRegistry(namespace));
	}

	//region// * Items * //
	@Override
	public <T extends Item> T registerItem(Identifier identifier, T item) {
		LibyItemRegistry.items.put(identifier.getNamespace(), item);
		return Registry.register(Registries.ITEM, identifier, item);
	}
	@Override
	public <T extends Item> T registerItem(String name, T item) {
		return this.registerItem(Identifier.of(this.namespace, name), item);
	}

	@Override
	public <T extends Item> T registerItem(Identifier identifier, Function<Item.Settings, T> itemFunction) {
		return this.registerItem(identifier, itemFunction.apply(new Item.Settings()));
	}
	@Override
	public <T extends Item> T registerItem(String name, Function<Item.Settings, T> itemFunction) {
		return this.registerItem(Identifier.of(this.namespace, name), itemFunction);
	}

	@Override
	public <T extends Item> T registerItem(Identifier identifier, Supplier<T> itemSupplier) {
		return this.registerItem(identifier, itemSupplier.get());
	}
	@Override
	public <T extends Item> T registerItem(String name, Supplier<T> itemSupplier) {
		return this.registerItem(Identifier.of(this.namespace, name), itemSupplier);
	}

	@Override
	public void addAllToItemGroup(RegistryKey<ItemGroup> itemGroupRegistryKey) {
		ItemGroupEvents.modifyEntriesEvent(itemGroupRegistryKey)
				.register(content -> content.addAll(
						LibyItemRegistry.items.getList(this.namespace).stream().map(Item::getDefaultStack).toList()
				));
	}

	//endregion

	//region// * Blocks * //
	@Override
	public <T extends Block> T registerBlock(Identifier identifier, T block) {
		return Registry.register(Registries.BLOCK, identifier, block);
	}

	@Override
	public <T extends Block> T registerBlock(String name, T block) {
		return this.registerBlock(Identifier.of(this.namespace, name), block);
	}

	@Override
	public <T extends Block> T registerBlock(Identifier identifier, T block, Item.Settings itemSettings) {
		this.registerItem(identifier, new BlockItem(block, itemSettings));
		return this.registerBlock(identifier, block);
	}
	@Override
	public <T extends Block> T registerBlock(String name, T block, Item.Settings itemSettings) {
		return this.registerBlock(Identifier.of(this.namespace, name), block, itemSettings);
	}

	@Override
	public <T extends Block> T registerBlock(Identifier identifier, T block, BlockItem blockItem) {
		this.registerItem(identifier, blockItem);
		return this.registerBlock(identifier, block);
	}
	@Override
	public <T extends Block> T registerBlock(String name, T block, BlockItem blockItem) {
		return this.registerBlock(Identifier.of(this.namespace, name), block, blockItem);
	}

	@Override
	public <T extends Block> T registerBlock(Identifier identifier, T block, BiFunction<Block, Item.Settings, BlockItem> itemFactory) {
		return this.registerBlock(identifier, block, itemFactory.apply(block, new Item.Settings()));
	}
	@Override
	public <T extends Block> T registerBlock(String name, T block, BiFunction<Block, Item.Settings, BlockItem> itemFactory) {
		return this.registerBlock(Identifier.of(this.namespace, name), block, itemFactory);
	}
	//endregion

	//region// * BlockEntity * //
	@Override
	public <T extends BlockEntityType<?>> T registerBlockEntityType(Identifier id, T blockEntityType) {
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, blockEntityType);
	}

	@Override
	public <T extends BlockEntityType<?>> T registerBlockEntityType(String name, T blockEntityType) {
		return this.registerBlockEntityType(Identifier.of(this.namespace, name), blockEntityType);
	}

	@Override
	public <T extends BlockEntity> BlockEntityType<T> registerBlockEntityType(String name, FabricBlockEntityTypeBuilder.Factory<T> factory, Class<? extends BlockEntityProvider> blockClass) {
		return this.registerBlockEntityType(name, FabricBlockEntityTypeBuilder.create(factory).addBlocks(Registries.BLOCK.stream().filter(blockClass::isInstance).toArray(Block[]::new)).build());
	}

	@Override
	public <T extends BlockEntity> BlockEntityType<T> registerBlockEntityType(Identifier id, FabricBlockEntityTypeBuilder.Factory<T> factory, Class<? extends BlockEntityProvider> blockClass) {
		return this.registerBlockEntityType(id, FabricBlockEntityTypeBuilder.create(factory).addBlocks(Registries.BLOCK.stream().filter(blockClass::isInstance).toArray(Block[]::new)).build());
	}

	@Override
	public <T extends BlockEntity> BlockEntityType<T> registerBlockEntityType(String name, FabricBlockEntityTypeBuilder.Factory<T> factory, BlockEntityProvider... blocks) {
		return this.registerBlockEntityType(name, FabricBlockEntityTypeBuilder.create(factory).addBlocks((Block[])blocks).build());
	}

	@Override
	public <T extends BlockEntity> BlockEntityType<T> registerBlockEntityType(Identifier id, FabricBlockEntityTypeBuilder.Factory<T> factory, BlockEntityProvider... blocks) {
		return this.registerBlockEntityType(id, FabricBlockEntityTypeBuilder.create(factory).addBlocks((Block[])blocks).build());
	}
	//endregion

	//region// * SoundEvents *//
	@Override
	public SoundEvent registerSoundEvent(String name) {
		return this.registerSoundEvent(Identifier.of(this.namespace, name));
	}
	@Override
	public SoundEvent registerSoundEvent(Identifier id) {
		SoundEvent event = SoundEvent.of(id);
		LibySoundRegistry.sounds.put(id.getNamespace(), event);
		return Registry.register(Registries.SOUND_EVENT, id, event);
	}

	@Override
	public SoundEvent registerSoundEvent(String name, float distanceToTravel) {
		return this.registerSoundEvent(Identifier.of(this.namespace, name), distanceToTravel);
	}
	@Override
	public SoundEvent registerSoundEvent(Identifier id, float distanceToTravel) {
		SoundEvent event = SoundEvent.of(id, distanceToTravel);
		LibySoundRegistry.sounds.put(id.getNamespace(), event);
		return Registry.register(Registries.SOUND_EVENT, id, event);
	}

	@Unimplemented
	@Deprecated
	@Override
	public void registerAllForFile() {
		LibySoundRegistry.sounds.forEach(this.namespace, (event) -> {

		});
	}
	//endregion

	//region// * EntityTypes * //
	@Override
	public <T extends Entity> EntityType<T> registerEntityType(Identifier id, EntityType<T> type) {
		return Registry.register(Registries.ENTITY_TYPE, id, type);
	}
	@Override
	public <T extends Entity> EntityType<T> registerEntityType(String name, EntityType<T> type) {
		return this.registerEntityType(Identifier.of(this.namespace, name), type);
	}
	//endregion

	//region// * Tags * //
	@Override
	public TagKey<Item> registerItemTag(Identifier tagId, ItemConvertible... items) {
		LibyInternalTagRegistry.add(TagTypesList.ITEMS, tagId, this.namespace, Arrays.stream(items).map(itemConvertible -> TagEntry.create(itemConvertible.asItem().liby$getId())).toArray(TagEntry[]::new));
		return TagKey.of(RegistryKeys.ITEM, tagId);
	}

	@Override
	public TagKey<Block> registerBlockTag(Identifier tagId, Block... blocks) {
		LibyInternalTagRegistry.add(TagTypesList.BLOCKS, tagId, this.namespace, Arrays.stream(blocks).map(block -> TagEntry.create(block.liby$getId())).toArray(TagEntry[]::new));
		return TagKey.of(RegistryKeys.BLOCK, tagId);
	}

	@Override
	public TagKey<Fluid> registerFluidTag(Identifier tagId, Fluid... fluids) {
		LibyInternalTagRegistry.add(TagTypesList.FLUIDS, tagId, this.namespace, Arrays.stream(fluids).map(fluid -> TagEntry.create(fluid.liby$getId())).toArray(TagEntry[]::new));
		return TagKey.of(RegistryKeys.FLUID, tagId);
	}

	@Override
	public TagKey<EntityType<?>> registerEntityTypeTag(Identifier tagId, EntityType<?>... entities) {
		LibyInternalTagRegistry.add(TagTypesList.ENTITY_TYPES, tagId, this.namespace, Arrays.stream(entities).map(entityType -> TagEntry.create(entityType.getRegistryEntry().registryKey().getValue())).toArray(TagEntry[]::new));
		return TagKey.of(RegistryKeys.ENTITY_TYPE, tagId);
	}

	@Override
	public TagKey<SoundEvent> registerSoundEventTag(Identifier tagId, SoundEvent... soundEvents) {
		LibyInternalTagRegistry.add(TagTypesList.SOUND_EVENT, tagId, this.namespace, Arrays.stream(soundEvents).map(soundEvent -> TagEntry.create(soundEvent.getId())).toArray(TagEntry[]::new));
		return TagKey.of(RegistryKeys.SOUND_EVENT, tagId);
	}

	@Override
	public <T extends LibyIdentifierResolvable> TagKey<T> registerIdentifierResolvable(Identifier tagId, RegistryKey<? extends Registry<T>> registry, T... tagEntries) {
		LibyInternalTagRegistry.add(registry.getRegistry().toString(), tagId, this.namespace, Arrays.stream(tagEntries).map(tag -> TagEntry.create(tag.liby$getId())).toArray(TagEntry[]::new));
		return TagKey.of(registry, tagId);
	}
	//endregion

	//region// * Recipes * //
	@Override
	public <T extends LibyRecipe> T registerRecipe(Identifier recipeIdentifier, T recipe) {
		LibyInternalRecipeRegistry.register(recipeIdentifier, recipe);
		return recipe;
	}
	//endregion
}