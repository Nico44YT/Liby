package nazario.liby.api.animation.v2.registry;

import nazario.liby.internal.registry.LibyImplementableRegistry;
import nazario.liby.internal.registry.LibyImplementedRegistry;
import nazario.liby.api.animation.v2.LibyAnimatable;
import nazario.liby.api.animation.v2.LibyAnimation;
import net.minecraft.util.Identifier;

import java.util.function.Predicate;

public interface LibyAnimationRegistry extends LibyImplementableRegistry {
    static LibyAnimationRegistry of(String namespace) {
        return (LibyAnimationRegistry) LibyImplementedRegistry.ofGeneric(namespace);
    }

    default Identifier registerAnimation(String name, LibyAnimation.Factory animationFactory) {
        return null;
    }

    default Identifier registerAnimation(String name, LibyAnimation.Factory animationFactory, Class<LibyAnimatable> predicateClass) {
        return null;
    }

    default Identifier registerAnimation(String name, LibyAnimation.Factory animationFactory, Predicate<LibyAnimatable> predicate) {
        return null;
    }

    default Identifier setAnimation(String name, LibyAnimation.Factory animationFactory) {
        return null;
    }

    default Identifier setAnimation(String name, LibyAnimation.Factory animationFactory, Class<LibyAnimatable> predicateClass) {
        return null;
    }

    default Identifier setAnimation(String name, LibyAnimation.Factory animationFactory, Predicate<LibyAnimatable> predicate) {
        return null;
    }
}
