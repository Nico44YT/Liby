package nazario.liby.api.block.multiblock;

import nazario.liby.api.util.nbt.LibyNbtCompoundBuilder;
import nazario.liby.api.util.nbt.LibyNbtCompoundReader;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;

public abstract class LibyMultiBlockEntity extends BlockEntity {

    public BlockPos parentPos;
    public boolean destroyed;

    public LibyMultiBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void setParentPos(BlockPos parentPos) {
        this.parentPos = parentPos;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public BlockPos getParentPos() {
        return this.parentPos;
    }

    public boolean isDestroyed() {
        return this.destroyed;
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        LibyNbtCompoundReader reader = LibyNbtCompoundReader.create(nbt);

        this.parentPos = reader.getBlockPos("parentPos");
        this.destroyed = reader.asCompound().getBoolean("destroyed");

        super.readNbt(nbt, registryLookup);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        LibyNbtCompoundBuilder builder = LibyNbtCompoundBuilder.create(nbt);

        builder.putBlockPos("parentPos", this.parentPos);
        builder.putBoolean("destroyed", this.destroyed);

        super.writeNbt(nbt, registryLookup);
    }
}
