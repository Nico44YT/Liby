package nazario.liby.api.ui.v1.registry;

import nazario.liby.api.ui.v1.LibyScreenAction;
import nazario.liby.internal.registry.LibyImplementableRegistry;
import nazario.liby.internal.ui.v1.registry.LibyImplementedUIRegistry;
import net.minecraft.util.Identifier;

public interface LibyScreenRegistry extends LibyImplementableRegistry {
    static LibyScreenRegistry of(String namespace) {
        return new LibyImplementedUIRegistry(namespace);
    }

    Identifier registerLibyScreenAction(String name, LibyScreenAction action);

}
