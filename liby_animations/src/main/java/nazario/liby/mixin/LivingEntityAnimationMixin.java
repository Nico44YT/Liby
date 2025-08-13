package nazario.liby.mixin;

import nazario.liby.api.animation.PlayState;
import nazario.liby.internal.animation.AnimationEntrypoint;
import nazario.liby.internal.animation.EntityAnimationComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(value = LivingEntity.class, priority = 1500)
public abstract class LivingEntityAnimationMixin {


    @Inject(method = "tick", at = @At("TAIL"))
    public void liby$tick(CallbackInfo ci) {
        LivingEntity thisEntity = (LivingEntity)(Object)this;
        Optional<EntityAnimationComponent> optional = AnimationEntrypoint.ENTITY_ANIMATION_COMPONENT_KEY.maybeGet(thisEntity);

        optional.ifPresent(component -> {
            if(component.getAnimationInstance() != null && component.getAnimationInstance().getAnimation() != null) {
                component.getAnimationInstance().logicTick(thisEntity);

                if(component.getPlayingState() == PlayState.STOPPED) component.clear();
            }
        });
    }

    @Inject(method = "applyMovementInput", at = @At("HEAD"), cancellable = true)
    public void liby$cancelMovement(Vec3d movementInput, float slipperiness, CallbackInfoReturnable<Vec3d> cir) {
        LivingEntity thisEntity = (LivingEntity)(Object)this;
        Optional<EntityAnimationComponent> optional = AnimationEntrypoint.ENTITY_ANIMATION_COMPONENT_KEY.maybeGet(thisEntity);

        optional.ifPresent(component -> {
            if(component.getAnimationInstance() == null || component.getAnimationInstance().getAnimation() == null) return;
            boolean bool = component.getAnimationInstance().getAnimation().canEntityMove(component.getAnimationInstance(), thisEntity);

            if(!bool) cir.setReturnValue(Vec3d.ZERO);
        });
    }

    @Inject(method = "isPushable", at = @At("HEAD"), cancellable = true)
    public void liby$isPushable(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity thisEntity = (LivingEntity)(Object)this;
        Optional<EntityAnimationComponent> optional = AnimationEntrypoint.ENTITY_ANIMATION_COMPONENT_KEY.maybeGet(thisEntity);

        optional.ifPresent(component -> {
            if(component.getAnimationInstance() == null || component.getAnimationInstance().getAnimation() == null) return;
            boolean bool = component.getAnimationInstance().getAnimation().allowEntityPushing(component.getAnimationInstance(), thisEntity);

            if(!bool) cir.setReturnValue(false);
        });
    }

    @Inject(method = "applyMovementInput", at = @At("HEAD"), cancellable = true)
    public void liby$cancelVoluntaryMovement(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity thisEntity = (LivingEntity)(Object)this;
        Optional<EntityAnimationComponent> optional = AnimationEntrypoint.ENTITY_ANIMATION_COMPONENT_KEY.maybeGet(thisEntity);

        optional.ifPresent(component -> {
            if(component.getAnimationInstance() == null || component.getAnimationInstance().getAnimation() == null) return;
            boolean bool = component.getAnimationInstance().getAnimation().canEntityMoveVoluntarily(component.getAnimationInstance(), thisEntity);

            if(!bool) cir.setReturnValue(false);
        });
    }

    @Inject(method = "getJumpVelocity", at = @At("HEAD"), cancellable = true)
    public void liby$cancelJump(CallbackInfoReturnable<Float> cir) {
        LivingEntity thisEntity = (LivingEntity)(Object)this;
        Optional<EntityAnimationComponent> optional = AnimationEntrypoint.ENTITY_ANIMATION_COMPONENT_KEY.maybeGet(thisEntity);

        optional.ifPresent(component -> {
            if(component.getAnimationInstance() == null || component.getAnimationInstance().getAnimation() == null) return;
            Float velocity = component.getAnimationInstance().getAnimation().getJumpVelocity(component.getAnimationInstance(), thisEntity);

            if(velocity != null) cir.setReturnValue(velocity);
        });
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void liby$damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity thisEntity = (LivingEntity) (Object) this;

        Optional<EntityAnimationComponent> optional = AnimationEntrypoint.ENTITY_ANIMATION_COMPONENT_KEY.maybeGet(thisEntity);
        optional.ifPresent(component -> {
            if(component.getAnimationInstance() == null || component.getAnimationInstance().getAnimation() == null) return;
            boolean bool = component.getAnimationInstance().getAnimation().allowEntityDamage(component.getAnimationInstance(), thisEntity, source.getAttacker(), source, amount);

            if(!bool) cir.setReturnValue(false);
        });
    }
}
