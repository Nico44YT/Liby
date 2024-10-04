package nazario.liby.registry.helper;

import nazario.liby.item.LibyItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class LibyItemRegister extends LibyRegister {
    public LibyItemRegister(String namespace) {
        super(namespace);
    }

    public Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(namespace, name), item);
    }

    public Item registerItem(String name, Item item, LibyItemGroup group) {
        group.addItemStack(new ItemStack(item));
        return registerItem(name, item);
    }
}
