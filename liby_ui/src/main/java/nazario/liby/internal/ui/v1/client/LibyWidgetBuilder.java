package nazario.liby.internal.ui.v1.client;

import nazario.liby.api.ui.v1.client.WidgetSyncType;

public interface LibyWidgetBuilder {
    LibyWidgetBuilder syncType(WidgetSyncType syncType);
    LibyWidget build();
}
