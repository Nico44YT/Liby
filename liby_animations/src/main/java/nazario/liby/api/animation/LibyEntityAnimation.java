package nazario.liby.api.animation;

import nazario.liby.api.annotations.Unimplemented;
import nazario.liby.api.util.rendering.LibyRenderingContext;
import net.minecraft.client.render.Frustum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public abstract class LibyEntityAnimation extends LibyAnimation {
    private Class<? extends LivingEntity> entityClass;

    public abstract void onRenderTick(LibyAnimationInstance instance, LivingEntity entity, LibyRenderingContext renderingContext);
    public abstract void onLogicTick(LibyAnimationInstance instance, LivingEntity entity);

    public boolean canEntityMoveVoluntarily(LibyAnimationInstance instance, LivingEntity entity) {
        return true;
    }

    public boolean canEntityMove(LibyAnimationInstance instance, LivingEntity entity) {
        return true;
    }

    public Float getJumpVelocity(LibyAnimationInstance instance, LivingEntity entity) {
        return null;
    }

    public boolean allowEntityPushing(LibyAnimationInstance instance, LivingEntity entity) {
        return true;
    }

    public boolean allowEntityDamage(LibyAnimationInstance instance, LivingEntity victim, Entity attacker, DamageSource damageSource, float damageAmount) {
        return true;
    }

    public int shouldEntityRender(LibyAnimationInstance libyAnimationInstance, Frustum frustum, double x, double y, double z) {
        return LibyAnimation.DEFAULT_RENDER;
    }

    @Unimplemented
    public boolean canLookAround(LibyAnimationInstance instance, LivingEntity entity) {
        return true;
    }

    LibyAnimation setEntityClass(Class<? extends LivingEntity> entityClass) {
        this.entityClass = entityClass;
        return this;
    }

    Class<? extends LivingEntity> getEntityClass() {
        if(this.entityClass == null) return LivingEntity.class;
        return this.entityClass;
    }

}
