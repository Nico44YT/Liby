package nazario.liby.api.registry.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

import nazario.liby.api.util.LibyMultiMap;
import nazario.liby.internal.registry.LibyImplementableRegistry;
import nazario.liby.internal.registry.LibyImplementedRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public interface LibyItemRegistry extends LibyImplementableRegistry {
	static LibyMultiMap<String, Item> items = new LibyMultiMap<>(HashMap.class, ArrayList.class);

	static LibyItemRegistry of(String name) {
		return LibyImplementedRegistry.ofItems(name);
	}

	<T extends Item> T registerItem(String name, T item);
	<T extends Item> T registerItem(String name, Function<Item.Settings, T> itemFunction);
	<T extends Item> T registerItem(String name, Supplier<T> itemSupplier);

	<T extends Item> T registerItem(Identifier identifier, T item);
	<T extends Item> T registerItem(Identifier identifier, Function<Item.Settings, T> itemFunction);
	<T extends Item> T registerItem(Identifier identifier, Supplier<T> itemSupplier);
}