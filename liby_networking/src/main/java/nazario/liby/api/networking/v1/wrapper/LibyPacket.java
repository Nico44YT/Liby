package nazario.liby.api.networking.v1.wrapper;

import nazario.liby.api.util.LibyIdentifier;
import nazario.liby.internal.injections.LibyIdentifierResolvable;
import net.minecraft.network.*;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.PacketType;

public interface LibyPacket<T extends LibyPacket<T>> extends CustomPayload, Packet<LibyPacket<T>>, PacketListener, LibyIdentifierResolvable {

    LibyPacketType<T> getPacketType();
    NetworkPhase getPhase();
    PacketCodec<? super PacketByteBuf, T> getCodec();

    void writeByteBuf(PacketByteBuf packetByteBuf);

    @Override
    default void onDisconnected(DisconnectionInfo info) {
        // no-op
    }

    @Override
    default boolean isConnectionOpen() {
        return false;
    }

    @Override
    default void apply(LibyPacket listener) {
        // no-op
    }

    @Override
    default NetworkSide getSide() {
        return getPacketType().getPacketType().side();
    }

    @Override
    default PacketType<T> getPacketId() {
        return getPacketType().getPacketType();
    }

    @Override
    default Id<T> getId() {
        return getPacketType().getPacketId();
    }

    @Override
    default LibyIdentifier liby$getId() {
        return getPacketType().getPacketId().id().liby$getId();
    }

}