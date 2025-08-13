package nazario.liby.internal.ui.v1.registry;

import nazario.liby.api.ui.v1.LibyScreenAction;
import nazario.liby.api.ui.v1.LibyScreenState;
import nazario.liby.api.ui.v1.LibyWidgetAction;
import nazario.liby.api.ui.v1.registry.LibyScreenRegistry;
import nazario.liby.api.ui.v1.registry.LibyWidgetActionRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LibyImplementedUIRegistry implements LibyScreenRegistry, LibyWidgetActionRegistry {

    protected static Map<Identifier, LibyScreenAction> screenActionMap = new ConcurrentHashMap<>();
    protected static Map<Identifier, Map<Identifier, LibyWidgetAction>> widgetActionMap = new ConcurrentHashMap<>();

    protected String namespace;

    public LibyImplementedUIRegistry(String namespace) {
        super();

        this.namespace = namespace;
    }

    @Override
    public Identifier registerLibyScreenAction(String name, LibyScreenAction action) {
        Identifier id = new Identifier(namespace, name);
        screenActionMap.put(id, action);
        return id;
    }

    public static void executeAction(Identifier screenId, ServerWorld world, PlayerEntity player, LibyScreenState screenState) {
        screenActionMap.get(screenId).apply(world, player, screenState);
    }

    @Override
    public Identifier registerWidget(String screen, String widget, LibyWidgetAction action) {
        return null;
    }
}
