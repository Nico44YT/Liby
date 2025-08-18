package nazario.liby.api.networking.v1.wrapper;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.Optional;

public class LibyServerPlayContext implements ServerPlayNetworking.Context {

    protected MinecraftServer server;
    protected ServerPlayerEntity playerEntity;
    protected PacketSender responseSender;

    public LibyServerPlayContext(ServerPlayNetworking.Context context) {
        this.server = context.server();
        this.playerEntity = context.player();
        this.responseSender = context.responseSender();
    }

    @Override
    public MinecraftServer server() {
        return server;
    }

    @Override
    public ServerPlayerEntity player() {
        return playerEntity;
    }

    @Override
    public PacketSender responseSender() {
        return responseSender;
    }

    public Optional<World> world() {
        return Optional.ofNullable(playerEntity.getWorld());
    }
}
