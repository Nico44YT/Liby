package nazario.liby.mixin.client;

import nazario.liby.api.item.LibyItemRenderOverrider;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin {

    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    private void liby$shouldRender(Entity entity, Frustum frustum, double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof PlayerEntity playerEntity) {
            if (playerEntity.getMainHandStack().getItem() instanceof LibyItemRenderOverrider customItemPlayerRenderer) {
                customItemPlayerRenderer.liby$shouldRenderPlayer((EntityRenderer<PlayerEntity>) (Object) this, playerEntity, frustum, x, y, z, cir);
            }
        }
    }
}
