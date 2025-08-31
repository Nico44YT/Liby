package nazario.liby.mixin.world.v1.client;

import com.mojang.datafixers.util.Pair;
import nazario.liby.api.world.v1.client.renderer.entity.LibyBoatEntityRenderer;
import nazario.liby.api.world.v1.entity.LibyBoat;
import nazario.liby.api.world.v1.entity.LibyChestBoatEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(BoatEntityRenderer.class)
public abstract class BoatEntityRendererMixin {

    @Unique private BoatEntity liby$entity;
    @Unique private EntityRendererFactory.Context liby$context;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void liby$setInit(EntityRendererFactory.Context ctx, boolean chest, CallbackInfo ci) {
        this.liby$context = ctx;
    }

    @Inject(method = "render(Lnet/minecraft/entity/vehicle/BoatEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"))
    public void liby$setEntity(BoatEntity boatEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        this.liby$entity = boatEntity;
    }

    @Redirect(method = "render(Lnet/minecraft/entity/vehicle/BoatEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"))
    public Object liby$render(Map instance, Object key) {
        BoatEntityRenderer renderer = (BoatEntityRenderer)(Object)this;

        if(renderer instanceof LibyBoatEntityRenderer libyBoatRenderer && liby$entity instanceof LibyBoat libyBoat) {
            Pair<Identifier, CompositeEntityModel<BoatEntity>> pair = new Pair<>(
                    libyBoatRenderer.getTexture(liby$entity), //Texture
                    libyBoatRenderer.createModel(liby$context, libyBoat, this.liby$entity instanceof LibyChestBoatEntity) //Model
                    );
            return pair;
        }

        return instance.get(key);
    }
}
