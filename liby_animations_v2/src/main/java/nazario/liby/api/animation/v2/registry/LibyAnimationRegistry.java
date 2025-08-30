package nazario.liby.api.animation.v2.registry;

import nazario.liby.api.animation.v2.LibyAnimatable;
import nazario.liby.api.animation.v2.LibyAnimation;
import nazario.liby.api.util.LibyIdentifier;
import nazario.liby.internal.animation.v2.LibyInternalAnimationRegistry;
import nazario.liby.internal.registry.LibyImplementableRegistry;
import net.minecraft.util.Identifier;

import java.util.Objects;
import java.util.function.Predicate;

public class LibyAnimationRegistry implements LibyImplementableRegistry {

    private final String namespace;

    protected LibyAnimationRegistry(String namespace) {
        this.namespace = namespace;
    }

    public static LibyAnimationRegistry of(String namespace) {
        return new LibyAnimationRegistry(namespace);
    }

    public <T extends LibyAnimation<U>, U extends LibyAnimatable> Identifier registerAnimation(String name, LibyAnimation.Factory<T, U> animationFactory, Predicate<U> predicate) {
        LibyIdentifier identifier = LibyIdentifier.of(this.namespace, name);
        if (LibyInternalAnimationRegistry.getAnimations().containsKey(identifier)) {
            throw new RuntimeException(
                    String.format("[LibyAnimations] Animation with id \"%s\" has already been registered", identifier));
        }

        LibyInternalAnimationRegistry.registerAnimation(identifier, animationFactory, predicate);
        return identifier;
    }

    public <T extends LibyAnimation<U>, U extends LibyAnimatable> Identifier registerAnimation(String name, LibyAnimation.Factory<T, U> animationFactory, Class<U> predicateClass) {
        return registerAnimation(name, animationFactory, predicateClass::isInstance);
    }

    public <T extends LibyAnimation<U>, U extends LibyAnimatable> Identifier registerAnimation(String name, LibyAnimation.Factory<T, U> animationFactory) {
        return registerAnimation(name, animationFactory, Objects::nonNull);
    }

    public <T extends LibyAnimation<U>, U extends LibyAnimatable> Identifier overwriteAnimation(String name, LibyAnimation.Factory<T, U> animationFactory, Predicate<U> predicate) {
        LibyIdentifier identifier = LibyIdentifier.of(this.namespace, name);
        LibyInternalAnimationRegistry.setAnimation(identifier, animationFactory, predicate);
        return identifier;
    }

    public <T extends LibyAnimation<U>, U extends LibyAnimatable> Identifier overwriteAnimation(String name, LibyAnimation.Factory<T, U> animationFactory, Class<U> predicateClass) {
        return overwriteAnimation(name, animationFactory, predicateClass::isInstance);
    }

    public <T extends LibyAnimation<U>, U extends LibyAnimatable> Identifier overwriteAnimation(String name, LibyAnimation.Factory<T, U> animationFactory) {
        return overwriteAnimation(name, animationFactory, Objects::nonNull);
    }
}
