package nazario.liby.internal.animation.v2;

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

@SuppressWarnings("unchecked")
public class LibyInternalAnimationRegistry {
    static Map<Identifier, AnimationEntry<? extends LibyAnimation<? extends LibyAnimatable>, ? extends LibyAnimatable>> animations = new ConcurrentHashMap<>();

    public static <T extends LibyAnimation<U>, U extends LibyAnimatable> Identifier registerAnimation(LibyIdentifier identifier, LibyAnimation.Factory<T, U> factory, Predicate<U> predicate) {
        if (animations.containsKey(identifier)) {
            throw new RuntimeException(String.format("[LibyAnimations] Animation with id %s is already registered, if you want to overwrite an animation use .setAnimation", identifier));
        }
        animations.put(identifier, new AnimationEntry<>(factory, predicate));
        return identifier;
    }

    public static <T extends LibyAnimation<U>, U extends LibyAnimatable> Identifier setAnimation(LibyIdentifier identifier, LibyAnimation.Factory<T, U> factory, Predicate<U> predicate) {
        animations.put(identifier, new AnimationEntry<>(factory, predicate));

        return identifier;
    }


    public static <T extends LibyAnimation<U>, U extends LibyAnimatable> T createAnimation(Identifier identifier, U libyAnimatable) {
        AnimationEntry<T, U> entry = (AnimationEntry<T, U>) animations.get(identifier);
        if (entry == null) return null;

        T animation = entry.factory().apply(new LibyIdentifier(identifier), entry.predicate());
        if (animation.checkPredicate(libyAnimatable)) {
            return animation;
        }
        return null;
    }

    public static Map<Identifier, AnimationEntry<?, ?>> getAnimations() {
        return animations;
    }

    public static <T extends LibyAnimation<U>, U extends LibyAnimatable> Optional<AnimationEntry<T, U>> getAnimation(Identifier identifier) {
        return Optional.of((AnimationEntry<T, U>) animations.getOrDefault(identifier, null));
    }

    public static <T extends LibyAnimation<U>, U extends LibyAnimatable> Optional<Supplier<T>> getAnimationSupplier(Identifier identifier) {
        return Optional.of((Supplier<T>) animations.getOrDefault(identifier, null).supplier(new LibyIdentifier(identifier)));
    }

    public static Map<Identifier, AnimationEntry<?, ?>> getAllFromNamespace(String namespace) {
        Map<Identifier, AnimationEntry<?, ?>> filteredAnimations = new HashMap<>();

        animations.forEach((id, entry) -> {
            if(id.getNamespace().equals(namespace)) filteredAnimations.put(id, entry);
        });

        return filteredAnimations;
    }


    public record AnimationEntry<T extends LibyAnimation<U>, U extends LibyAnimatable>(
            LibyAnimation.Factory<T, U> factory,
            Predicate<U> predicate
    ) {
        Supplier<T> supplier(LibyIdentifier id) {
            return () -> factory.apply(id, predicate);
        }
    }
}
