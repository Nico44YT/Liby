package nazario.liby.api.networking.v1.wrapper;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.listener.ClientCommonPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class LibyServerNetworking {
    public static <T extends LibyPacket<T>, U extends LibyServerPlayReceiver<T>> boolean registerGlobalReceiver(T libyPacket, U handler) {
        return ServerPlayNetworking.registerGlobalReceiver(libyPacket.getId(), handler);
    }

    public static <T extends LibyPacket<T>> LibyServerPlayReceiver<T> unregisterGlobalReceiver(T libyPacket) {
        return (LibyServerPlayReceiver<T>) ServerPlayNetworking.unregisterGlobalReceiver(libyPacket.liby$getId());
    }

    public static <T extends LibyPacket<T>, U extends LibyServerPlayReceiver<T>> boolean registerReceiver(ServerPlayNetworkHandler networkHandler, T libyPacket, U handler) {
        return ServerPlayNetworking.registerReceiver(networkHandler, libyPacket.getId(), handler);
    }

    public static <T extends LibyPacket<T>> LibyServerPlayReceiver<T> unregisterReceiver(ServerPlayNetworkHandler networkHandler, T libyPacket) {
        return (LibyServerPlayReceiver<T>) ServerPlayNetworking.unregisterReceiver(networkHandler, libyPacket.liby$getId());
    }

    public static boolean canSend(ServerPlayerEntity serverPlayer, LibyPacket<?> packet) {
        return ServerPlayNetworking.canSend(serverPlayer, packet.getPacketId().id());
    }

    public static boolean canSend(ServerPlayerEntity serverPlayer, LibyPacketType<?> type) {
        return ServerPlayNetworking.canSend(serverPlayer, type.getPacketId().id());
    }

    public static <T extends LibyPacket<T>> Packet<ClientCommonPacketListener> createC2SPacket(T packet) {
        return ServerPlayNetworking.createS2CPacket(packet);
    }

    public static void send(ServerPlayerEntity serverPlayer, LibyPacket<?> packet) {
        ServerPlayNetworking.send(serverPlayer, packet);
    }
}
