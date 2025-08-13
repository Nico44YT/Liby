package nazario.liby.internal.ui.v1.networking.packets;

import nazario.liby.api.ui.v1.LibyUiRegistry;
import nazario.liby.internal.ui.v1.LibyWidgetState;
import nazario.liby.api.util.nbt.LibyNbtCompound;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

public record LibySendWidgetStateC2S(Identifier screenId, Identifier widgetId, LibyWidgetState state) implements FabricPacket, ServerPlayNetworking.PlayPacketHandler<LibySendWidgetStateC2S> {

    public static final Identifier ID = net.minecraft.util.Identifier.of("liby_ui", "send_widget_state");
    public static final PacketType<LibySendWidgetStateC2S> PACKET_TYPE = PacketType.create(ID, LibySendWidgetStateC2S::fromPacketByteBuf);

    private static LibySendWidgetStateC2S fromPacketByteBuf(PacketByteBuf packetByteBuf) {
        Identifier screenId = packetByteBuf.readIdentifier();
        Identifier widgetId = packetByteBuf.readIdentifier();

        LibyNbtCompound stateNbt = new LibyNbtCompound(packetByteBuf.readNbt());

        String stateClass = stateNbt.getString("class");

        try{
            Class<? extends LibyWidgetState> clazz = (Class<? extends LibyWidgetState>) Class.forName(stateClass);

            LibyWidgetState state = clazz.getDeclaredConstructor().newInstance(stateNbt);

            return new LibySendWidgetStateC2S(screenId, widgetId, state);
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public void write(PacketByteBuf packetByteBuf) {
        packetByteBuf.writeIdentifier(this.screenId);
        packetByteBuf.writeIdentifier(this.widgetId);
        LibyNbtCompound nbtCompound = new LibyNbtCompound();

        state.writeToNbt(nbtCompound);

        packetByteBuf.writeNbt(nbtCompound);
    }

    @Override
    public PacketType<LibySendWidgetStateC2S> getType() {
        return PACKET_TYPE;
    }

    @Override
    public void receive(LibySendWidgetStateC2S packet, ServerPlayerEntity player, PacketSender packetSender) {
        LibyUiRegistry.getWidgetAction(packet.screenId, packet.widgetId).apply((ServerWorld) player.getWorld(), player, packet.state);
    }
}
