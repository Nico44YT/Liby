package nazario.liby.api.ui.v1.registry;

import nazario.liby.api.ui.v1.LibyWidgetAction;
import nazario.liby.internal.ui.v1.registry.LibyImplementedUIRegistry;
import net.minecraft.util.Identifier;

public interface LibyWidgetActionRegistry {
    static LibyScreenRegistry of(String namespace) {
        return new LibyImplementedUIRegistry(namespace);
    }

    Identifier registerWidget(String screen, String widget, LibyWidgetAction action);
}
