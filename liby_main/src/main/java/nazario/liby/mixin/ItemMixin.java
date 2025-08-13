package nazario.liby.mixin;

import nazario.liby.api.util.LibyIdentifier;
import nazario.liby.internal.injections.LibyIdentifierResolvable;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public abstract class ItemMixin implements LibyIdentifierResolvable {
    @Override
    public LibyIdentifier liby$getId() {
        return new LibyIdentifier(((Item) (Object) this).getRegistryEntry().getKey().get().getValue());
    }
}
