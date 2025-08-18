package nazario.liby.client;

import nazario.liby.api.networking.v1.packets.LibyBlockEntityUpdatePacketS2C;
import nazario.liby.api.networking.v1.wrapper.client.LibyClientNetworking;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class LibyNetworkingClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        LibyClientNetworking.registerGlobalReceiver(LibyBlockEntityUpdatePacketS2C.PACKET_TYPE, (p, c) -> p.receive(p, c));
    }
}
