package nazario.liby.api.animation;

import nazario.liby.LibyMain;
import nazario.liby.internal.animation.EntityAnimationComponent;
import nazario.liby.internal.animation.AnimationEntrypoint;
import net.minecraft.entity.LivingEntity;

import java.util.Optional;

public class LibyAnimationHelper {

    public static void start(LivingEntity livingEntity, LibyEntityAnimation libyAnimation) {
        EntityAnimationComponent component = AnimationEntrypoint.ENTITY_ANIMATION_COMPONENT_KEY.get(livingEntity);

        if(!canStart(livingEntity, libyAnimation)) {
            LibyMain.LOGGER.warn(String.format(
                    "[Liby] Animation %s is not compatible for Entity %s, animation is for entity-type %s",
                    libyAnimation.getId().toString(),
                    livingEntity.getClass(),
                    libyAnimation.getClass()
            ));
            return;
        }

        component.start(libyAnimation);
        component.sync();

    }

    public static void pause(LivingEntity livingEntity) {
        EntityAnimationComponent component = AnimationEntrypoint.ENTITY_ANIMATION_COMPONENT_KEY.get(livingEntity);
        component.pause();
        component.sync();
    }

    public static void resume(LivingEntity livingEntity) {
        EntityAnimationComponent component = AnimationEntrypoint.ENTITY_ANIMATION_COMPONENT_KEY.get(livingEntity);
        component.resume();
        component.sync();
    }

    public static void stop(LivingEntity livingEntity) {
        EntityAnimationComponent component = AnimationEntrypoint.ENTITY_ANIMATION_COMPONENT_KEY.get(livingEntity);
        component.stop();
        component.sync();
    }

    public static <T extends LivingEntity> boolean canStart(T entity, LibyEntityAnimation libyEntityAnimation) {
        return libyEntityAnimation.getEntityClass().isInstance(entity);
    }

    public static PlayState getPlayState(LivingEntity livingEntity) {
        Optional<EntityAnimationComponent> optional = AnimationEntrypoint.ENTITY_ANIMATION_COMPONENT_KEY.maybeGet(livingEntity);

        if(optional.isPresent()) {
            return optional.get().getPlayingState();
        }

        return PlayState.STOPPED;
    }

    public static LibyAnimationInstance getAnimationInstance(LivingEntity livingEntity) {
        return AnimationEntrypoint.ENTITY_ANIMATION_COMPONENT_KEY.get(livingEntity).getAnimationInstance();
    }
}
