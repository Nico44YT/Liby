package nazario.liby.registry.helper;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public abstract class LibyRegister {
    protected final String namespace;

    public LibyRegister(String namespace) {
        this.namespace = namespace;
    }
}
