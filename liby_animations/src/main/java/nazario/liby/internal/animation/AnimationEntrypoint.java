package nazario.liby.internal.animation;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import net.minecraft.entity.LivingEntity;

public class AnimationEntrypoint implements EntityComponentInitializer {

    public static final ComponentKey<EntityAnimationComponent> ENTITY_ANIMATION_COMPONENT_KEY = ComponentRegistry.getOrCreate(EntityAnimationComponent.ID, EntityAnimationComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(LivingEntity.class, ENTITY_ANIMATION_COMPONENT_KEY, EntityAnimationComponent::new);
    }
}
