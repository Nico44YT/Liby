package nazario.liby.mixin.animation.v2;

import nazario.liby.api.animation.v2.LibyEntityAnimation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void libyAnimations$damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity)(Object)this;

        if(entity.isPlayingLibyAnimation()) {
            entity.getLibyAnimation().ifPresent(anim -> {
                boolean canTakeDamage = ((LibyEntityAnimation<?>)anim).canEntityTakeDamage(entity, source, amount);

                if(!canTakeDamage) {
                    cir.setReturnValue(false);
                    cir.cancel();
                }
            });
        }
    }

    @Inject(method = "applyMovementInput", at = @At("HEAD"), cancellable = true)
    public void libyAnimations$applyMovementInpu(Vec3d movementInput, float slipperiness, CallbackInfoReturnable<Vec3d> cir) {
        LivingEntity entity = (LivingEntity)(Object)this;

        if(entity.isPlayingLibyAnimation()) {
            entity.getLibyAnimation().ifPresent(anim -> {
                boolean canTakeDamage = ((LibyEntityAnimation<?>)anim).canEntityMove(entity, movementInput, slipperiness);

                if(!canTakeDamage) {
                    cir.setReturnValue(Vec3d.ZERO);
                    cir.cancel();
                }
            });
        }
    }
}
