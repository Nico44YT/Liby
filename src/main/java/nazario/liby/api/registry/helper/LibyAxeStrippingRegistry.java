package nazario.liby.api.registry.helper;

import net.minecraft.block.Block;

import java.util.HashMap;
import java.util.Map;

public class LibyAxeStrippingRegistry {
    protected static HashMap<Block, Block> STRIPPING_MAP = new HashMap<>();

    public static void register(Block logBlock, Block strippedLogBlock) {
        STRIPPING_MAP.put(logBlock, strippedLogBlock);
    }

    public static Map<Block, Block> getMap() {
        return new HashMap<>(STRIPPING_MAP);
    }
}
