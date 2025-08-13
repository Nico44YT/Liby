package nazario.liby.internal.injections;

import nazario.liby.api.util.LibyIdentifier;

public interface LibyIdentifierResolvable {
    default LibyIdentifier liby$getId() {
        return null;
    }
}
