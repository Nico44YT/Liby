package nazario.liby.api.ui.v1.client;

/**
 * Defines how a widget's state is synchronized with the server:
 *
 * ON_UPDATE — Sends an update packet to the server immediately whenever the widget changes.
 * PASSIVE   — Includes the widget's state only in periodic or batch ScreenState updates.
 * NEVER     — The widget's state is never sent to the server.
 */
public enum WidgetSyncType {
    ON_UPDATE,
    PASSIVE,
    NEVER
}