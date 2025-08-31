package nazario.liby.api.world.v1.client.renderer.entity;

import nazario.liby.api.world.v1.entity.LibyBoatEntity;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class LibyWorldEntityModelLayers {
    public static EntityModelLayer createRaft(LibyBoatEntity.LibyBoatType type) {
        return create(Identifier.of(type.id().getNamespace(), "raft/" + type.id().getPath()), "main");
    }

    public static EntityModelLayer createChestRaft(LibyBoatEntity.LibyBoatType type) {
        return create(Identifier.of(type.id().getNamespace(), "chest_raft/" + type.id().getPath()), "main");
    }

    public static EntityModelLayer createBoat(LibyBoatEntity.LibyBoatType type) {
        return create(Identifier.of(type.id().getNamespace(), "boat/" + type.id().getPath()), "main");
    }

    public static EntityModelLayer createChestBoat(LibyBoatEntity.LibyBoatType type) {
        return create(Identifier.of(type.id().getNamespace(), "chest_boat/" + type.id().getPath()), "main");
    }

    private static EntityModelLayer create(Identifier id, String layer) {
        return new EntityModelLayer(id, layer);
    }
}
