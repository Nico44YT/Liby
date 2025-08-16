package nazario.liby_networking.api;

import nazario.liby_networking.LibyNetworkingMain;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record LibyBlockEntityNbtUpdateS2C(Identifier worldId, BlockPos blockPos, NbtCompound nbtCompound) implements FabricPacket, ClientPlayNetworking.PlayPacketHandler<LibyBlockEntityNbtUpdateS2C> {

    public static Identifier ID = LibyNetworkingMain.id("block_entity_update_s2c");
    public static PacketType<LibyBlockEntityNbtUpdateS2C> PACKET_TYPE = PacketType.create(ID, LibyBlockEntityNbtUpdateS2C::fromPacketByteBuf);

    private static LibyBlockEntityNbtUpdateS2C fromPacketByteBuf(PacketByteBuf packetByteBuf) {
        return new LibyBlockEntityNbtUpdateS2C(packetByteBuf.readIdentifier(), packetByteBuf.readBlockPos(), packetByteBuf.readNbt());
    }

    public static LibyBlockEntityNbtUpdateS2C create(BlockEntity blockEntity) {
        return new LibyBlockEntityNbtUpdateS2C(
                blockEntity.getWorld().getRegistryKey().getValue(),
                blockEntity.getPos(),
                blockEntity.createNbt()
        );
    }

    @Override
    public void write(PacketByteBuf packetByteBuf) {
        packetByteBuf
                .writeIdentifier(worldId)
                .writeBlockPos(blockPos)
                .writeNbt(nbtCompound);
    }

    @Override
    public PacketType<?> getType() {
        return PACKET_TYPE;
    }

    @Override
    public void receive(LibyBlockEntityNbtUpdateS2C packet, ClientPlayerEntity clientPlayerEntity, PacketSender packetSender) {
        Identifier worldId = packet.worldId;

        if(clientPlayerEntity.getWorld().getRegistryKey().getValue().equals(worldId)) {
            BlockEntity block = clientPlayerEntity.getWorld().getBlockEntity(packet.blockPos);
            block.readNbt(packet.nbtCompound);
        }
    }
}
