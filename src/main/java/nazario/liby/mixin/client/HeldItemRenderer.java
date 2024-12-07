package nazario.liby.mixin.client;

import nazario.liby.interfaces.LibyItemRenderOverrider;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.render.item.HeldItemRenderer.class)
public abstract class HeldItemRenderer {
    @Inject(method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V", at = @At("HEAD"))
    public void liby$renderItem(float tickDelta, MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumers, ClientPlayerEntity player, int light, CallbackInfo ci) {
        player.getHandItems().forEach(itemStack -> {
            if(itemStack.getItem() instanceof LibyItemRenderOverrider customItemPlayerRenderer) {
                customItemPlayerRenderer.renderItemInHand(tickDelta, matrices, vertexConsumers, player, light, ci);
            }
        });
    }
}
