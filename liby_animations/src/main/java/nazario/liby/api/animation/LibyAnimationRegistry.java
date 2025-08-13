package nazario.liby.api.animation;

import nazario.liby.internal.LibyAnimationRuntimeErrors;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class LibyAnimationRegistry {
    private static LibyAnimationRegistry INSTANCE;

    private static synchronized LibyAnimationRegistry getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LibyAnimationRegistry();
        }
        return INSTANCE;
    }

    protected final HashMap<Identifier, LibyAnimation> ANIMATIONS;

    private LibyAnimationRegistry() {
        this.ANIMATIONS = new HashMap<>();
    }

    public static <T extends LibyAnimation> T register(Identifier id, T animation) {
        return register(id, animation, false);
    }

    public static <T extends LibyEntityAnimation> T register(Identifier id, T animation, Class<? extends LivingEntity> entityClass) {
        return register(id, animation, false, entityClass);
    }
    public static <T extends LibyAnimation> T register(Identifier id, T animation, boolean overwrite) {
        boolean contains = getInstance().ANIMATIONS.containsKey(id);

        if(contains && !overwrite) throw LibyAnimationRuntimeErrors.alreadyRegisteredAnimation(animation);
        getInstance().ANIMATIONS.put(id, animation);
        animation.setId(id);

        return animation;
    }

    public static <T extends LibyEntityAnimation> T register(Identifier id, T animation, boolean overwrite, Class<? extends LivingEntity> entityClass) {
        boolean contains = getInstance().ANIMATIONS.containsKey(id);

        if(contains && !overwrite) throw LibyAnimationRuntimeErrors.alreadyRegisteredAnimation(animation);
        getInstance().ANIMATIONS.put(id, animation);
        animation.setId(id);
        animation.setEntityClass(entityClass);

        return animation;
    }

    public static Optional<LibyAnimation> get(Identifier id) {
        return Optional.ofNullable(getInstance().ANIMATIONS.get(id));
    }

    public static Map<Identifier, LibyAnimation> getAllFromNamespace(String namespace) {
        return getInstance().ANIMATIONS.entrySet().stream().filter(entry -> namespace.equals(entry.getKey().getNamespace())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Map<Identifier, LibyAnimation> getAll() {
        return getInstance().ANIMATIONS;
    }
}
