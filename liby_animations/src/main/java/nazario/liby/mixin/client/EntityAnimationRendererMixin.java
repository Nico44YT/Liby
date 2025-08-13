package nazario.liby.mixin.client;

import nazario.liby.api.animation.LibyAnimation;
import nazario.liby.api.animation.PlayState;
import nazario.liby.api.client.renderer.LibyItemRenderingAdditions;
import nazario.liby.api.util.rendering.LibyRenderingContext;
import nazario.liby.internal.animation.AnimationEntrypoint;
import nazario.liby.internal.animation.EntityAnimationComponent;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(EntityRenderer.class)
public abstract class EntityAnimationRendererMixin {

    @Inject(method = "render", at = @At("HEAD"))
    public void liby$renderEntity(Entity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if(entity instanceof LivingEntity livingEntity) {
            Optional<EntityAnimationComponent> optional = AnimationEntrypoint.ENTITY_ANIMATION_COMPONENT_KEY.maybeGet(livingEntity);

            optional.ifPresent(component -> {
                if(component.getAnimationInstance() != null) component.getAnimationInstance().renderTick(livingEntity, new LibyRenderingContext(tickDelta, matrices, vertexConsumers, light));
            });
        }
    }

    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    private void liby$shouldRender(Entity entity, Frustum frustum, double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof LivingEntity livingEntity) {
            Optional<EntityAnimationComponent> optional = AnimationEntrypoint.ENTITY_ANIMATION_COMPONENT_KEY.maybeGet(livingEntity);

            optional.ifPresent(component -> {
                if(component.getAnimationInstance() != null) {
                    if(component.getPlayingState() == PlayState.PLAYING) {
                        int code = component.getAnimationInstance().shouldEntityRender(frustum, x, y, z);

                        switch(code) {
                            case LibyAnimation.ALWAYS_RENDER -> cir.setReturnValue(true);
                            case LibyAnimation.NEVER_RENDER -> cir.setReturnValue(false);
                            case LibyAnimation.DEFAULT_RENDER -> {/*nothing*/}
                            default -> {/*nothing*/}
                        }
                    }
                }
            });
        }
    }
}
