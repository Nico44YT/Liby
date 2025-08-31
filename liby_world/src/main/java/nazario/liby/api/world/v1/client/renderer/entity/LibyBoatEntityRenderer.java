package nazario.liby.api.world.v1.client.renderer.entity;

import nazario.liby.api.world.v1.entity.LibyBoat;
import nazario.liby.api.world.v1.entity.LibyBoatEntity;
import nazario.liby.api.world.v1.entity.LibyChestBoatEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;

public class LibyBoatEntityRenderer extends BoatEntityRenderer {

    public LibyBoatEntityRenderer(EntityRendererFactory.Context ctx, boolean chest) {
        super(ctx, chest);
    }

    public static Identifier getTexture(LibyBoatEntity.LibyBoatType type, boolean chest) {
        String namespace = type.id().getNamespace();
        String path = "textures/entity/";
        String file = type.id().getPath();
        String chestStr = chest ? "chest_boat/" : "boat/";

        return Identifier.of(namespace,path + chestStr + file + ".png");
    }

    @Override
    public Identifier getTexture(BoatEntity boatEntity) {
        if(boatEntity instanceof LibyBoat boat) {
            return getTexture(boat.getBoatVariant(), boat instanceof LibyChestBoatEntity);
        }
        throw new RuntimeException("LibyBoatEntityRenderer registered for non LibyBoat" + boatEntity.getClass());
    }

    public CompositeEntityModel<BoatEntity> createModel(EntityRendererFactory.Context context, LibyBoat boat, boolean chest) {
        EntityModelLayer entityModelLayer = chest ? EntityModelLayers.createChestBoat(BoatEntity.Type.OAK) : EntityModelLayers.createBoat(BoatEntity.Type.OAK);
        ModelPart modelPart = context.getPart(entityModelLayer);
        if (boat.getBoatVariant().raft()) {
            return chest ? new ChestRaftEntityModel(modelPart) : new RaftEntityModel(modelPart);
        } else {
            return chest ? new ChestBoatEntityModel(modelPart) : new BoatEntityModel(modelPart);
        }
    }
}
