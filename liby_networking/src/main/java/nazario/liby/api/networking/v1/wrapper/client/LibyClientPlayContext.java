package nazario.liby.api.networking.v1.wrapper.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.world.World;

import java.util.Optional;

public class LibyClientPlayContext implements ClientPlayNetworking.Context {

    protected MinecraftClient client;
    protected ClientPlayerEntity clientPlayerEntity;
    protected PacketSender responseSender;

    public LibyClientPlayContext(ClientPlayNetworking.Context context) {
        this.client = context.client();
        this.clientPlayerEntity = context.player();
        this.responseSender = context.responseSender();
    }

    @Override
    public MinecraftClient client() {
        return this.client;
    }

    @Override
    public ClientPlayerEntity player() {
        return this.clientPlayerEntity;
    }

    @Override
    public PacketSender responseSender() {
        return this.responseSender;
    }

    public Optional<World> world() {
        return Optional.ofNullable(clientPlayerEntity.getWorld());
    }
}
