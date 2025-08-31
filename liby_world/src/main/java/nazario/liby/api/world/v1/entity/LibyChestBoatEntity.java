package nazario.liby.api.world.v1.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public abstract class LibyChestBoatEntity extends ChestBoatEntity implements LibyBoat {
    public LibyChestBoatEntity(EntityType<? extends BoatEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
    }

    @Override
    public abstract LibyBoatEntity.LibyBoatType getBoatVariant();
    public abstract Item asItem();

    @Override
    @Deprecated
    public final Type getVariant() {
        return null;
    }
}
