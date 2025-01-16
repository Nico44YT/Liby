package nazario.liby.mixin.client;

import nazario.liby.api.item.LibyItemRenderOverrider;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin {
    @Inject(method = "render(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
            at = @At("TAIL"))
    public void liby$render(AbstractClientPlayerEntity player, float tickDelta, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, CallbackInfo ci) {
        if (player.getMainHandStack().getItem() instanceof LibyItemRenderOverrider customItemPlayerRenderer) {
            customItemPlayerRenderer.liby$renderPlayer((PlayerEntityRenderer) (Object) this, player, tickDelta, g, matrixStack, vertexConsumerProvider, light, ci);
        }
    }
}
