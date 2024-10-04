package nazario.liby.registry.helper;

import nazario.liby.item.LibyItemGroup;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class LibyBlockRegister extends LibyRegister {

    public LibyBlockRegister(String namespace) {
        super(namespace);
    }

    public Block registerBlock(String id, Block block) {
        return Registry.register(Registries.BLOCK, Identifier.of(namespace, id), block);
    }

    public Block registerBlock(String id, Block block, Item.Settings itemSettings) {
        Registry.register(Registries.ITEM, Identifier.of(namespace, id), new BlockItem(block, itemSettings));
        return registerBlock(id, block);
    }

    public Block registerBlock(String id, Block block, Item.Settings itemSettings, LibyItemGroup group) {
        Item item = Registry.register(Registries.ITEM, Identifier.of(namespace, id), new BlockItem(block, itemSettings));
        group.addItemStack(new ItemStack(item));
        return registerBlock(id, block);
    }
}
