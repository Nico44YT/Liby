package nazario.liby.api.world.v1.entity;

import net.minecraft.entity.EntityDimensions;

public interface LibyBoat {
    EntityDimensions BOAT_DIMENSIONS = EntityDimensions.fixed(1.375F, 0.5625F);
    LibyBoatEntity.LibyBoatType getBoatVariant();
}
