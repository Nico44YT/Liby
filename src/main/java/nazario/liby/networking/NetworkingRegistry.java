package nazario.liby.networking;

import nazario.liby.networking.payloads.LibySyncPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@ApiStatus.AvailableSince("1.21.1-build-1.3")
public class NetworkingRegistry {

    public static void register() {
        PayloadTypeRegistry.playS2C().register(LibySyncPacket.ID, LibySyncPacket.CODEC);
    }

    public static void clientRegister() {
        ClientPlayNetworking.registerGlobalReceiver(LibySyncPacket.ID, LibySyncPacket::receive);
    }
}
