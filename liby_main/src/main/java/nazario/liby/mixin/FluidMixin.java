package nazario.liby.mixin;

import nazario.liby.api.util.LibyIdentifier;
import nazario.liby.internal.injections.LibyIdentifierResolvable;
import net.minecraft.fluid.Fluid;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Fluid.class)
public abstract class FluidMixin implements LibyIdentifierResolvable {
    @Override
    public LibyIdentifier liby$getId() {
        return new LibyIdentifier(((Fluid)(Object) this).getRegistryEntry().getKey().get().getValue());
    }
}
