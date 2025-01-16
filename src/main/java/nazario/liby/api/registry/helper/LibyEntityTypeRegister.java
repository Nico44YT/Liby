package nazario.liby.api.registry.helper;

import nazario.liby.registry.AbstractRegister;
import nazario.liby.registry.LibyClientEntityTypeList;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class LibyEntityTypeRegister extends AbstractRegister {
    public LibyEntityTypeRegister(String namespace) {
        super(namespace);
    }

    public EntityType<? extends Entity> registerEntityType(String name, FabricEntityTypeBuilder<? extends Entity> builder, EntityRendererFactory<? extends Entity> rendererFactory) {
        EntityType<? extends Entity> type = registerEntityType(name, builder);
        LibyClientEntityTypeList.entityRenderList.put(type, rendererFactory);
        return type;
    }

    public EntityType<? extends Entity> registerEntityType(String name, FabricEntityTypeBuilder<? extends Entity> builder) {
        EntityType<? extends Entity> type = Registry.register(Registries.ENTITY_TYPE, Identifier.of(this.namespace, name), builder.build());
        return type;
    }

    public EntityType<? extends LivingEntity> registerEntityType(String name, FabricEntityTypeBuilder<? extends LivingEntity> builder, DefaultAttributeContainer.Builder attributeBuilder) {
        EntityType<? extends LivingEntity> type = Registry.register(Registries.ENTITY_TYPE, Identifier.of(this.namespace, name), builder.build());
        FabricDefaultAttributeRegistry.register(type, attributeBuilder.build());
        return type;
    }

    public EntityType<? extends LivingEntity> registerEntityType(String name, FabricEntityTypeBuilder<? extends LivingEntity> builder, DefaultAttributeContainer.Builder attributeBuilder, EntityRendererFactory<? extends LivingEntity> rendererFactory) {
        EntityType<? extends LivingEntity> type = registerEntityType(name, builder, attributeBuilder);
        LibyClientEntityTypeList.entityRenderList.put(type, rendererFactory);
        return type;
    }
}
