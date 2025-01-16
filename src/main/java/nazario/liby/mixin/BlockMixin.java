package nazario.liby.mixin;

import nazario.liby.api.block.LibyBlockExtendedMethods;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public abstract class BlockMixin implements LibyBlockExtendedMethods {
    @Override
    public Identifier liby$getId() {
        return ((Block)(Object) this).getRegistryEntry().getKey().get().getValue();
    }
}
