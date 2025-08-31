package nazario.liby.api.world.v1.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public abstract class LibyBoatEntity extends BoatEntity implements LibyBoat {
    public LibyBoatEntity(EntityType<? extends BoatEntity> entityType, World world) {
        super(entityType, world);
    }

    protected LibyBoatEntity(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public abstract LibyBoatType getBoatVariant();
    public abstract Item asItem();

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
    }

    @Override
    @Deprecated
    public final Type getVariant() {
        return null;
    }

    public record LibyBoatType(Block baseBlock, Identifier id, boolean raft) {

    }
}
