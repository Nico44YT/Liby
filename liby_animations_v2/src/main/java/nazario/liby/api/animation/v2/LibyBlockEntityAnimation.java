package nazario.liby.api.animation.v2;

import nazario.liby.internal.animation.v2.networking.SyncAnimationS2C;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

import java.util.function.Predicate;

public abstract class LibyBlockEntityAnimation<T extends BlockEntity> extends LibyAnimation<T> {

    public LibyBlockEntityAnimation(Identifier identifier, Predicate<T> predicate) {
        super(identifier, predicate);
    }

    @Override
    public void syncAnimation(T animatable) {
        if(animatable.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.getPlayers().forEach(player -> {
                ServerPlayNetworking.send(player, SyncAnimationS2C.ID, SyncAnimationS2C.create(animatable).write(PacketByteBufs.create()));
            });
        }
    }
}
