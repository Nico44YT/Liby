package nazario.liby.mixin.client;

import nazario.liby.api.item.LibyItemRenderOverrider;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin<T extends LivingEntity> extends AnimalModel<T> {

    @Shadow
    @Final
    public ModelPart rightArm;

    @Shadow
    @Final
    public ModelPart leftArm;

    @Shadow
    @Final
    public ModelPart head;

    @Inject(method = "animateArms", at = @At("HEAD"), cancellable = true)
    protected void liby$animateArms(T entity, float animationProgress, CallbackInfo ci) {
        if (entity.getMainHandStack().getItem() instanceof LibyItemRenderOverrider customHandAnimation) {
            customHandAnimation.liby$animateArmSwing(entity, (BipedEntityModel<? extends LivingEntity>) (Object) this, animationProgress, ci);
        }
    }

    @Inject(method = "positionRightArm", at = @At("HEAD"), cancellable = true)
    private void liby$positionRightArm(T entity, CallbackInfo ci) {
        if (entity.getMainHandStack().getItem() instanceof LibyItemRenderOverrider customHandAnimation) {
            customHandAnimation.liby$animateHoldingItem(rightArm, leftArm, head, (BipedEntityModel<? extends LivingEntity>) (Object) this, true, ci);
        }
    }

    @Inject(method = "positionLeftArm", at = @At("HEAD"), cancellable = true)
    private void liby$positionLeftArm(T entity, CallbackInfo ci) {
        if (entity.getMainHandStack().getItem() instanceof LibyItemRenderOverrider customHandAnimation) {
            customHandAnimation.liby$animateHoldingItem(rightArm, leftArm, head, (BipedEntityModel<? extends LivingEntity>) (Object) this, false, ci);
        }
    }


    @Inject(method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V", at = @At("TAIL"), cancellable = true)
    private void liby$setAngles(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        if (livingEntity.getMainHandStack().getItem() instanceof LibyItemRenderOverrider customHandAnimation) {
            customHandAnimation.liby$setPlayerModelAngles(livingEntity, f, g, h, i, j, (BipedEntityModel<? extends LivingEntity>) (Object) this, ci);
        }
    }
}
