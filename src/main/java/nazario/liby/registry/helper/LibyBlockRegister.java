package nazario.liby.registry.helper;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.function.BiFunction;

public class LibyBlockRegister extends LibyRegister {

    public LibyBlockRegister(String namespace) {
        super(namespace);
    }

    public Block registerBlock(String id, Block block) {
        return Registry.register(Registry.BLOCK, Identifier.of(namespace, id), block);
    }

    public Block registerBlock(String id, Block block, Item.Settings itemSettings) {
        Registry.register(Registry.ITEM, Identifier.of(namespace, id), new BlockItem(block, itemSettings));
        return registerBlock(id, block);
    }

    public Block registerBlock(String id, Block block, BlockItem item) {
        Registry.register(Registry.ITEM, Identifier.of(namespace, id), item);
        return registerBlock(id, block);
    }

    public Block registerBlock(String id, Block block, BiFunction<Block, Item.Settings, BlockItem> itemFactory) {
        Registry.register(Registry.ITEM, Identifier.of(namespace, id), itemFactory.apply(block, new Item.Settings()));
        return registerBlock(id, block);
    }
}
