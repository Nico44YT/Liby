package nazario.liby.mixin;

import nazario.liby.api.util.LibyIdentifier;
import nazario.liby.internal.injections.LibyIdentifierResolvable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(World.class)
public abstract class WorldMixin implements LibyIdentifierResolvable {
    @Shadow public abstract RegistryKey<World> getRegistryKey();

    @Override
    public LibyIdentifier liby$getId() {
        return new LibyIdentifier(getRegistryKey().getValue());
    }
}
