package nazario.liby.api.networking.v1.wrapper;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;

public class LibyPacketTypeRegistry {
    public static <T extends LibyPacket<T>> void registerS2CPlayPacket(LibyPacketType<T> packetType, PacketCodec<? super PacketByteBuf, T> codec) {
        PayloadTypeRegistry.playS2C().register(packetType.getPacketId(), codec);
    }

    public static <T extends LibyPacket<T>> void registerS2CConfigurePacket(LibyPacketType<T> packetType, PacketCodec<? super PacketByteBuf, T> codec) {
        PayloadTypeRegistry.configurationS2C().register(packetType.getPacketId(), codec);
    }

    public static <T extends LibyPacket<T>> void registerC2SPlayPacket(LibyPacketType<T> packetType, PacketCodec<? super PacketByteBuf, T> codec) {
        PayloadTypeRegistry.playC2S().register(packetType.getPacketId(), codec);
    }

    public static <T extends LibyPacket<T>> void registerC22ConfigurePacket(LibyPacketType<T> packetType, PacketCodec<? super PacketByteBuf, T> codec) {
        PayloadTypeRegistry.configurationC2S().register(packetType.getPacketId(), codec);
    }
}
