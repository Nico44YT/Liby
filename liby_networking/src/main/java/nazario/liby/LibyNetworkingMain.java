package nazario.liby;

import nazario.liby.api.networking.v1.packets.LibyBlockEntityUpdatePacketS2C;
import nazario.liby.api.networking.v1.wrapper.LibyPacketTypeRegistry;
import net.fabricmc.api.ModInitializer;

public class LibyNetworkingMain implements ModInitializer {

    @Override
    public void onInitialize() {
        LibyPacketTypeRegistry.registerS2CPlayPacket(LibyBlockEntityUpdatePacketS2C.PACKET_TYPE, LibyBlockEntityUpdatePacketS2C.CODEC);
    }
}
