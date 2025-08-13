package nazario.liby.mixin;

import nazario.liby.api.item.LibyItemEntityHurtListeners;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Unique
    PlayerEntity lastPlayerDamage;
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void liby$damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity thisEntity = (LivingEntity) (Object) this;

        if(source.getAttacker() instanceof PlayerEntity player) {
            lastPlayerDamage = player;
            if(player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof LibyItemEntityHurtListeners itemMethods) itemMethods.onEntityDamage(thisEntity, source, amount, Hand.MAIN_HAND, cir);
            if(player.getStackInHand(Hand.OFF_HAND).getItem() instanceof LibyItemEntityHurtListeners itemMethods) itemMethods.onEntityDamage(thisEntity, source, amount, Hand.OFF_HAND, cir);
        }
    }

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;onDeath(Lnet/minecraft/entity/damage/DamageSource;)V"), cancellable = true)
    private void liby$deathDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity thisEntity = (LivingEntity)(Object)this;

        if(lastPlayerDamage == null) return;

        if(lastPlayerDamage.getStackInHand(Hand.MAIN_HAND).getItem() instanceof LibyItemEntityHurtListeners itemMethods) itemMethods.onEntityDeathDamage(thisEntity, source, amount, Hand.MAIN_HAND, cir);
        if(lastPlayerDamage.getStackInHand(Hand.OFF_HAND).getItem() instanceof LibyItemEntityHurtListeners itemMethods) itemMethods.onEntityDeathDamage(thisEntity, source, amount, Hand.OFF_HAND, cir);
    }
}
