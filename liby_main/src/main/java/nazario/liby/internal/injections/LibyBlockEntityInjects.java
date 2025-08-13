package nazario.liby.internal.injections;

import net.minecraft.block.BlockState;

public interface LibyBlockEntityInjects {
    default BlockState liby$getBlockState() {
        return null;
    }
}
