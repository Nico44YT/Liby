package nazario.liby.api.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public interface LibyItemEntityHurtListeners {
    default void onEntityDamage(LivingEntity victim, DamageSource source, float amount, Hand hand, CallbackInfoReturnable<Boolean> cir) {};
    default void onEntityDeathDamage(LivingEntity victim, DamageSource source, float amount, Hand hand, CallbackInfoReturnable<Boolean> cir) {};
}
