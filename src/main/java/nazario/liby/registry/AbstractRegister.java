package nazario.liby.registry;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public abstract class AbstractRegister {
    protected final String namespace;

    public AbstractRegister(String namespace) {
        this.namespace = namespace;
    }
}
