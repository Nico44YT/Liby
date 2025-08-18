package nazario.liby.api.networking.v1.wrapper;

import nazario.liby.api.util.LibyIdentifier;
import nazario.liby.internal.injections.LibyIdentifierResolvable;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.packet.PacketType;
import net.minecraft.util.Identifier;

public class LibyPacketType<T extends LibyPacket<T>> implements LibyIdentifierResolvable {

    protected final PacketType<T> PACKET_TYPE;
    protected final CustomPayload.Id<T> PACKET_ID;

    protected LibyPacketType(PacketType<T> packetType, CustomPayload.Id<T> packetId) {
        this.PACKET_TYPE = packetType;
        this.PACKET_ID = packetId;
    }

    public static <T extends LibyPacket<T>> LibyPacketType<T> create(LibyPacketOrigin origin, Identifier identifier) {
        return new LibyPacketType<T>(
                new PacketType<T>(origin.getNetworkSide(), identifier),
                new CustomPayload.Id<T>(identifier)
        );
    }

    public PacketType<T> getPacketType() {
        return PACKET_TYPE;
    }

    public CustomPayload.Id<T> getPacketId() {
        return PACKET_ID;
    }

    @Override
    public LibyIdentifier liby$getId() {
        return PACKET_ID.id().liby$getId();
    }
}