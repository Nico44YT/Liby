package nazario.liby.mixin;

import nazario.liby.api.util.LibyIdentifier;
import nazario.liby.internal.injections.LibyIdentifierResolvable;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public abstract class BlockMixin implements LibyIdentifierResolvable {
    @Override
    public LibyIdentifier liby$getId() {
        return new LibyIdentifier(((Block)(Object) this).getRegistryEntry().getKey().get().getValue());
    }
}
