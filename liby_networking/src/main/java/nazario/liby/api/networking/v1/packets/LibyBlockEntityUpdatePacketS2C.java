package nazario.liby.api.networking.v1.packets;

import nazario.liby.LibyMain;
import nazario.liby.api.networking.v1.wrapper.LibyPacket;
import nazario.liby.api.networking.v1.wrapper.LibyPacketOrigin;
import nazario.liby.api.networking.v1.wrapper.LibyPacketType;
import nazario.liby.api.networking.v1.wrapper.client.LibyClientPlayContext;
import nazario.liby.api.networking.v1.wrapper.client.LibyClientPlayReceiver;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.NetworkPhase;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record LibyBlockEntityUpdatePacketS2C(BlockEntityType<?> type, BlockPos pos, NbtCompound nbtCompound) implements LibyPacket<LibyBlockEntityUpdatePacketS2C>, LibyClientPlayReceiver<LibyBlockEntityUpdatePacketS2C> {
    private static final Identifier ID = Identifier.of("liby_networking", "block_entity_update");
    public static final LibyPacketType<LibyBlockEntityUpdatePacketS2C> PACKET_TYPE = LibyPacketType.create(LibyPacketOrigin.SERVER_TO_CLIENT, ID);
    public static final PacketCodec<PacketByteBuf, LibyBlockEntityUpdatePacketS2C> CODEC = PacketCodec.of(LibyBlockEntityUpdatePacketS2C::writeByteBuf, LibyBlockEntityUpdatePacketS2C::createFromByteBuf);

    public static LibyBlockEntityUpdatePacketS2C createFromByteBuf(PacketByteBuf packetByteBuf) {
        BlockEntityType<?> type = Registries.BLOCK_ENTITY_TYPE.get(packetByteBuf.readIdentifier());
        return new LibyBlockEntityUpdatePacketS2C(type, packetByteBuf.readBlockPos(), packetByteBuf.readNbt());
    }

    @Override
    public void writeByteBuf(PacketByteBuf buf) {
        buf.writeIdentifier(type.getRegistryEntry().registryKey().getValue());
        buf.writeBlockPos(pos);
        buf.writeNbt(nbtCompound);
    }

    @Override
    public void receive(LibyBlockEntityUpdatePacketS2C packet, LibyClientPlayContext context) {
        context.world().ifPresentOrElse(world -> {
            world.getBlockEntity(packet.pos, packet.type).ifPresentOrElse(blockEntity -> {
                blockEntity.readComponentlessNbt(packet.nbtCompound, world.getRegistryManager());
            }, () -> LibyMain.LOGGER.warn("[LibyNetworking] BlockEntity was not found in Packet: {}", packet.liby$getId().toString()));
        }, () -> LibyMain.LOGGER.warn("[LibyNetworking] World was not present in Packet: {}", packet.liby$getId().toString()));
    }

    @Override
    public PacketCodec<? super PacketByteBuf, LibyBlockEntityUpdatePacketS2C> getCodec() {
        return CODEC;
    }

    @Override
    public LibyPacketType<LibyBlockEntityUpdatePacketS2C> getPacketType() {
        return PACKET_TYPE;
    }

    @Override
    public NetworkPhase getPhase() {
        return NetworkPhase.PLAY;
    }
}
