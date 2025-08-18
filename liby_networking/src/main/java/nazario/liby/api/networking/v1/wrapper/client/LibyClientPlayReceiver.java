package nazario.liby.api.networking.v1.wrapper.client;

import nazario.liby.api.networking.v1.wrapper.LibyPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public interface LibyClientPlayReceiver<T extends LibyPacket<T>> extends ClientPlayNetworking.PlayPayloadHandler<T> {

    void receive(T packet, LibyClientPlayContext context);

    @Override
    default void receive(T packet, ClientPlayNetworking.Context context) {
        this.receive(packet, new LibyClientPlayContext(context));
    }
}
