package nazario.liby_networking.internal.packet;

import nazario.liby_networking.LibyNetworkingMain;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@ApiStatus.Experimental
public record LibyBlockEntitySyncPacket(BlockPos pos, NbtCompound nbtCompound) implements FabricPacket, ServerPlayNetworking.PlayPacketHandler<LibyBlockEntitySyncPacket>, ClientPlayNetworking.PlayPacketHandler<LibyBlockEntitySyncPacket> {

    public static final PacketType<LibyBlockEntitySyncPacket> PACKET_TYPE = PacketType.create(LibyNetworkingMain.id("sync_block_entity"), LibyBlockEntitySyncPacket::fromPacketByteBuf);

    private static LibyBlockEntitySyncPacket fromPacketByteBuf(PacketByteBuf packetByteBuf) {
        return new LibyBlockEntitySyncPacket(packetByteBuf.readBlockPos(), packetByteBuf.readNbt());
    }

    @Override
    public void write(PacketByteBuf packetByteBuf) {
        packetByteBuf.writeBlockPos(pos);
        packetByteBuf.writeNbt(nbtCompound);
    }

    @Override
    public PacketType<?> getType() {
        return PACKET_TYPE;
    }

    @Override
    public void receive(LibyBlockEntitySyncPacket packet, ServerPlayerEntity player, PacketSender packetSender) {

    }

    @Override
    public void receive(LibyBlockEntitySyncPacket packet, ClientPlayerEntity player, PacketSender packetSender) {

    }
}
