package nazario.liby.internal.animation.v2;

import nazario.liby.LibyMain;
import nazario.liby.api.animation.v2.LibyAnimatable;
import nazario.liby.api.animation.v2.LibyAnimation;
import nazario.liby.api.util.LibyIdentifier;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class LibyInternalAnimationRegistry {
    static Map<Identifier, Supplier<LibyAnimation<LibyAnimatable>>> animations = new ConcurrentHashMap<>();

    public static Identifier registerAnimation(LibyIdentifier identifier, LibyAnimation.Factory factory, Predicate<LibyAnimatable> predicate) {
        if(animations.containsKey(identifier)) {
            LibyMain.LOGGER.error(String.format("[LibyAnimations] Animation with id %s is already registered, if you want to overwrite an animation use .setAnimation.", identifier));
            throw new RuntimeException("Animation already registered");
        }

        animations.putIfAbsent(identifier, () -> factory.apply(identifier, predicate));

        return identifier;
    }

    public static Identifier setAnimation(LibyIdentifier identifier, LibyAnimation.Factory factory, Predicate<LibyAnimatable> predicate) {
        animations.put(identifier, () -> factory.apply(identifier, predicate));

        return identifier;
    }

    public static LibyAnimation<LibyAnimatable> createAnimation(Identifier identifier, LibyAnimatable libyAnimatable) {
        Supplier<LibyAnimation<LibyAnimatable>> supplier = animations.get(identifier);

        LibyAnimation<LibyAnimatable> animation = supplier.get();

        if(animation.checkPredicate(libyAnimatable)) {
            return animation;
        }

        LibyMain.LOGGER.warn(String.format("[LibyAnimations] Couldn't create animation, predicate did not pass. Animation-ID: %s", animation.liby$getId()));
        return null;
    }

    public static Map<Identifier, Supplier<LibyAnimation<LibyAnimatable>>> getAnimations() {
        return animations;
    }

    public static Optional<Supplier<LibyAnimation<LibyAnimatable>>> getAnimation(Identifier identifier) {
        return Optional.of(animations.getOrDefault(identifier, null));
    }

    public static Map<Identifier, Supplier<LibyAnimation<LibyAnimatable>>> getAllFromNamespace(String namespace) {
        Map<Identifier, Supplier<LibyAnimation<LibyAnimatable>>> filteredAnimations = new HashMap<>();

        animations.forEach((id, supplier) -> {
            if(id.getNamespace().equals(namespace)) filteredAnimations.put(id, supplier);
        });

        return filteredAnimations;
    }
}
