package nazario.liby.api.animation.v2;

import net.minecraft.util.Identifier;

import java.util.Optional;

public interface LibyAnimatable {
    default void startLibyAnimation(Identifier identifier) {

    }

    default void pauseLibyAnimation() {

    }

    default void resumeLibyAnimation() {

    }

    default void stopLibyAnimation() {

    }

    default <T extends LibyAnimatable> void restartLibyAnimation(LibyAnimation<T> newAnimation) {

    }

    default <U extends LibyAnimatable, T extends LibyAnimation<U>> Optional<T> getLibyAnimation() {
        return Optional.empty();
    }

    default LibyAnimationPlayState getLibyAnimationState() {
        return LibyAnimationPlayState.STOPPED;
    }

    default boolean isPlayingLibyAnimation() {
        return getLibyAnimationState() == LibyAnimationPlayState.PLAYING;
    }

    enum AnimatableType {
        BLOCK_ENTITY, ENTITY
    }
}
