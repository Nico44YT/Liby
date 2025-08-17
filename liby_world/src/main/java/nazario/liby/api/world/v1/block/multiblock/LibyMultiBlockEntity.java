package nazario.liby.api.world.v1.block.multiblock;

import nazario.liby.api.util.nbt.LibyNbtCompound;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public abstract class LibyMultiBlockEntity extends BlockEntity {

    protected BlockPos parentPos;
    protected boolean destroyed;

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
    public void readNbt(NbtCompound nbt) {
        LibyNbtCompound compound = new LibyNbtCompound(nbt);

        this.parentPos = compound.getBlockPos("parentPos");
        this.destroyed = compound.getBoolean("destroyed");

        super.readNbt(compound);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        LibyNbtCompound compound = new LibyNbtCompound(nbt);

        compound.putBlockPos("parentPos", this.parentPos);
        compound.putBoolean("destroyed", this.destroyed);

        super.writeNbt(compound);
    }
}