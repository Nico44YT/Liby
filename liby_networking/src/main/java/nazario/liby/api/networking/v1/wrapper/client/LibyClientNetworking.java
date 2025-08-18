package nazario.liby.api.networking.v1.wrapper.client;

import nazario.liby.api.networking.v1.wrapper.LibyPacket;
import nazario.liby.api.networking.v1.wrapper.LibyPacketType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.listener.ServerCommonPacketListener;
import net.minecraft.network.packet.Packet;

public class LibyClientNetworking {
    public static <T extends LibyPacket<T>, U extends LibyClientPlayReceiver<T>> boolean registerGlobalReceiver(LibyPacketType<T> libyPacket, U handler) {
        return ClientPlayNetworking.registerGlobalReceiver(libyPacket.getPacketId(), handler);
    }

    public static <T extends LibyPacket<T>> LibyClientPlayReceiver<T> unregisterGlobalReceiver(LibyPacketType<T> libyPacket) {
        return (LibyClientPlayReceiver<T>) ClientPlayNetworking.unregisterGlobalReceiver(libyPacket.liby$getId());
    }

    public static <T extends LibyPacket<T>> boolean registerReceiver(LibyPacketType<T> libyPacket, LibyClientPlayReceiver<T> handler) {
        return ClientPlayNetworking.registerReceiver(libyPacket.getPacketId(), handler);
    }

    public static <T extends LibyPacket<T>> LibyClientPlayReceiver<T> unregisterReceiver(LibyPacketType<T> libyPacket) {
        return (LibyClientPlayReceiver<T>) ClientPlayNetworking.unregisterReceiver(libyPacket.liby$getId());
    }

    public static boolean canSend(LibyPacket<?> packet) {
        return ClientPlayNetworking.canSend(packet.getPacketId().id());
    }

    public static boolean canSend(LibyPacketType<?> type) {
        return ClientPlayNetworking.canSend(type.getPacketId().id());
    }

    public static <T extends LibyPacket<T>> Packet<ServerCommonPacketListener> createC2SPacket(T packet) {
        return ClientPlayNetworking.createC2SPacket(packet);
    }

    public static void send(LibyPacket<?> packet) {
        ClientPlayNetworking.send(packet);
    }
}
