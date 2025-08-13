package nazario.liby.api.registry.helper;

import nazario.liby.internal.registry.LibyImplementableRegistry;
import nazario.liby.internal.registry.LibyImplementedRegistry;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

import java.util.function.BiFunction;

public interface LibyBlockRegistry extends LibyImplementableRegistry {
	static LibyBlockRegistry of(String name) {
		return LibyImplementedRegistry.ofBlocks(name);
	}

	<T extends Block> T registerBlock(String name, T block);
	<T extends Block> T registerBlock(String name, T block, Item.Settings itemSettings);
	<T extends Block> T registerBlock(String name, T block, BlockItem blockItem);
	<T extends Block> T registerBlock(String name, T block, BiFunction<Block, Item.Settings, BlockItem> itemFactory);

	<T extends Block> T registerBlock(Identifier identifier, T block);
	<T extends Block> T registerBlock(Identifier identifier, T block, Item.Settings itemSettings);
	<T extends Block> T registerBlock(Identifier identifier, T block, BlockItem blockItem);
	<T extends Block> T registerBlock(Identifier identifier, T block, BiFunction<Block, Item.Settings, BlockItem> itemFactory);
}