package nazario.liby.api.block;

import net.minecraft.util.Identifier;

public interface LibyBlockExtendedMethods {
    default Identifier liby$getId() {
        return null;
    }
}
