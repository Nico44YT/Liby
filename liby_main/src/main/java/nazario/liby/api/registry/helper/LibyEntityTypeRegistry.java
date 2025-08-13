package nazario.liby.api.registry.helper;

import nazario.liby.internal.registry.LibyImplementableRegistry;
import nazario.liby.internal.registry.LibyImplementedRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;

public interface LibyEntityTypeRegistry extends LibyImplementableRegistry {
    static LibyEntityTypeRegistry of(String name) {
        return LibyImplementedRegistry.ofEntityTypes(name);
    }

    <T extends Entity> EntityType<T> registerEntityType(Identifier id, EntityType<T> type);
    <T extends Entity> EntityType<T> registerEntityType(String name, EntityType<T> type);
}
