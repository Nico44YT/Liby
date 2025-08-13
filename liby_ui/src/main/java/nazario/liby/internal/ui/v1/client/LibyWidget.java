package nazario.liby.internal.ui.v1.client;

import nazario.liby.api.ui.v1.client.LibyScreen;
import nazario.liby.api.ui.v1.client.WidgetSyncType;
import nazario.liby.internal.injections.LibyIdentifierResolvable;
import nazario.liby.internal.ui.v1.LibyWidgetState;
import nazario.liby.internal.ui.v1.networking.packets.LibySendWidgetStateC2S;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public interface LibyWidget extends LibyIdentifierResolvable {
    WidgetSyncType getSyncType();
    LibyWidgetState getState();
    LibyScreen getParentScreen();

    default void send() {
        if(this.getSyncType() == WidgetSyncType.NEVER) return;

        ClientPlayNetworking.send(new LibySendWidgetStateC2S(getParentScreen().liby$getId(), this.liby$getId(), this.getState()));
    };
}
