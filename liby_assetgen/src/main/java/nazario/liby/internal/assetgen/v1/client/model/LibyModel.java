package nazario.liby.internal.assetgen.v1.client.model;

import nazario.liby.api.util.LibyIdentifier;
import nazario.liby.internal.injections.LibyIdentifierResolvable;
import net.minecraft.util.Identifier;

public interface LibyModel<T> extends LibyIdentifierResolvable {
    Identifier getId();
    T bake();

    @Override
    default LibyIdentifier liby$getId() {
        return new LibyIdentifier(getId());
    }
}
