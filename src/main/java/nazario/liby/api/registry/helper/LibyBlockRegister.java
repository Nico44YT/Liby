package nazario.liby.api.registry.helper;

import nazario.liby.registry.AbstractRegister;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.BiFunction;

public class LibyBlockRegister extends AbstractRegister {

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

    public Block registerBlock(String id, Block block, BlockItem item) {
        Registry.register(Registries.ITEM, Identifier.of(namespace, id), item);
        return registerBlock(id, block);
    }

    public Block registerBlock(String id, Block block, BiFunction<Block, Item.Settings, BlockItem> itemFactory) {
        Registry.register(Registries.ITEM, Identifier.of(namespace, id), itemFactory.apply(block, new Item.Settings()));
        return registerBlock(id, block);
    }

    public <T extends BlockEntityType<?>> T registerBlockEntityType(String name, T blockEntityType) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(namespace, name), blockEntityType);
    }

}
