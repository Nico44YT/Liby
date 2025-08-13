package nazario.liby.internal;

import nazario.liby.api.animation.LibyAnimation;

public class LibyAnimationRuntimeErrors {
    public static IllegalArgumentException alreadyRegisteredAnimation(LibyAnimation animation) {
        return new IllegalArgumentException(String.format("Animation %s already registered, if you want to overwrite an animation set it to true", animation.getId()));
    }
}
