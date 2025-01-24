package nazario.liby.api.item;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public interface LibyItemRenderOverrider {
    default void liby$renderItemInHand(float tickDelta, MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumers, ClientPlayerEntity player, int light, CallbackInfo ci) {
    }

    default void liby$renderPlayer(PlayerEntityRenderer playerEntityRenderer, AbstractClientPlayerEntity player, float tickDelta, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, CallbackInfo ci) {
    }

    default void liby$shouldRenderPlayer(EntityRenderer<PlayerEntity> playerEntityRenderer, PlayerEntity player, Frustum frustum, double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
    }

    default void liby$animateArmSwing(Entity entity, BipedEntityModel<? extends LivingEntity> model, float animationProgress, CallbackInfo ci) {
    }

    default void liby$setPlayerModelAngles(LivingEntity livingEntity, float f, float g, float h, float i, float j, BipedEntityModel<? extends LivingEntity> model, CallbackInfo ci) {
    }

    default void liby$animateHoldingItem(ModelPart holdingArm, ModelPart otherArm, ModelPart head, BipedEntityModel<? extends LivingEntity> model, boolean rightArmed, CallbackInfo ci) {
    }

    default void liby$renderItemHud(MatrixStack matrices, float tickDelta, ItemStack stack) {
    }
}
