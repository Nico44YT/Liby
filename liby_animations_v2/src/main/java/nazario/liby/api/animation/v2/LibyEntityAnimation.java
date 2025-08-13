package nazario.liby.api.animation.v2;

import nazario.liby.api.util.LibyIdentifier;
import nazario.liby.internal.animation.v2.networking.SyncAnimationS2C;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

import java.util.function.Predicate;

public abstract class LibyEntityAnimation<T extends Entity> extends LibyAnimation<T> {

    public LibyEntityAnimation(LibyIdentifier identifier, Predicate<T> predicate) {
        super(identifier, predicate);
    }


    @Environment(EnvType.CLIENT)
    public RenderType shouldRender(T entity, Frustum frustum, double x, double y, double z) {
        return RenderType.DEFAULT_RENDER;
    }

    @Environment(EnvType.CLIENT)
    abstract public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light);

    public <U extends LivingEntity> boolean canEntityTakeDamage(U entity, DamageSource source, float amount) {
        return true;
    }

    public <U extends LivingEntity> boolean canEntityMove(U entity, Vec3d movementInput, float slipperiness) {
        return true;
    }

    @Override
    public void syncAnimation(T animatable) {
        if(animatable.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.getPlayers().forEach(player -> {
                ServerPlayNetworking.send(player, SyncAnimationS2C.create(animatable));
            });
        }
    }

    public enum RenderType {
        ALWAYS_RENDER,
        DEFAULT_RENDER,
        NEVER_RENDER;
    }
}
