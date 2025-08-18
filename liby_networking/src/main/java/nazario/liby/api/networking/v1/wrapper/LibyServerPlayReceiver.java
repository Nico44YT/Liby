package nazario.liby.api.networking.v1.wrapper;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public interface LibyServerPlayReceiver<T extends LibyPacket<T>> extends ServerPlayNetworking.PlayPayloadHandler<T> {

    void receive(T packet, LibyServerPlayContext context);

    @Override
    default void receive(T packet, ServerPlayNetworking.Context context) {
        this.receive(packet, new LibyServerPlayContext(context));
    }
}
