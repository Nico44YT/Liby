package nazario.liby.api.registry.helper;

import nazario.liby.registry.AbstractRegister;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import java.util.function.Function;

public class LibyItemRegister extends AbstractRegister {
    public LibyItemRegister(String namespace) {
        super(namespace);
    }

    public Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(namespace, name), item);
    }

    public Item registerItem(String name, Function<Item.Settings, Item> itemFunction) {
        return registerItem(name, itemFunction.apply(new Item.Settings()));
    }
}
