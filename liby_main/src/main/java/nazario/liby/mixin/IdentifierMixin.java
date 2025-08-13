package nazario.liby.mixin;

import nazario.liby.api.util.LibyIdentifier;
import nazario.liby.internal.injections.LibyIdentifierResolvable;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Identifier.class)
public abstract class IdentifierMixin implements LibyIdentifierResolvable {
    @Override
    public LibyIdentifier liby$getId() {
        return new LibyIdentifier((Identifier)(Object)this);
    }
}
