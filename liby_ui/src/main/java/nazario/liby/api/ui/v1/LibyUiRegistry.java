package nazario.liby.api.ui.v1;

import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LibyUiRegistry {
    public static final Map<Identifier, Map<Identifier, LibyWidgetAction>> widgetMap = new ConcurrentHashMap<>();

    public static void registerWidget(Identifier screenId, Identifier widgetId, LibyWidgetAction action) {
        widgetMap.getOrDefault(screenId, new HashMap<>()).put(widgetId, action);
    }

    public static LibyWidgetAction getWidgetAction(Identifier screenId, Identifier widgetId) {
        return widgetMap.get(screenId).get(widgetId);
    }
}