package nazario.liby.block;

import nazario.liby.nbt.NbtCompoundBuilder;
import nazario.liby.nbt.NbtCompoundReader;
import nazario.liby.networking.LibyNetworker;
import nazario.liby.networking.LibySyncedValue;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;

public abstract class LibyMultiBlockEntity extends BlockEntity {

    @LibySyncedValue(BlockPos.class) public BlockPos parentPos;
    @LibySyncedValue(Boolean.class) public boolean destroyed;

    public LibyMultiBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void setParentPos(BlockPos parentPos) {
        this.parentPos = parentPos;
        //if(!world.isClient) LibyNetworker.syncBlockEntity(world, pos, (LibyMultiBlockEntity)this);
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
        //if(!world.isClient) LibyNetworker.syncBlockEntity(world, pos, (LibyMultiBlockEntity)this);
    }

    public BlockPos getParentPos() {
        if(!world.isClient) LibyNetworker.syncBlockEntity(world, pos, (LibyMultiBlockEntity)this);
        return this.parentPos;
    }

    public boolean isDestroyed() {
        //if(!world.isClient) LibyNetworker.syncBlockEntity(world, pos, (LibyMultiBlockEntity)this);
        return this.destroyed;
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        NbtCompoundReader reader = NbtCompoundReader.create(nbt);

        setParentPos(reader.getBlockPos("parentPos"));
        setDestroyed(reader.asCompound().getBoolean("destroyed"));

        super.readNbt(nbt, registryLookup);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        NbtCompoundBuilder builder = NbtCompoundBuilder.create(nbt);

        builder.putBlockPos("parentPos", getParentPos());
        builder.putBoolean("destroyed", isDestroyed());

        super.writeNbt(builder.build(), registryLookup);
    }

}
