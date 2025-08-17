package nazario.liby.api.world.v1.block.fluid_container;

import nazario.liby.api.world.v1.fluid_container.LibyFluidContainer;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public abstract class LibyFluidContainerBlockEntity extends BlockEntity {

    protected LibyFluidContainer fluidContainer;

    public LibyFluidContainerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, long capacity) {
        super(type, pos, state);

        this.fluidContainer = new LibyFluidContainer(this, capacity);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.put("fluidVariant", fluidContainer.getVariant().toNbt());
        nbt.putLong("amount", fluidContainer.getAmount());
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        fluidContainer.setVariant(FluidVariant.fromNbt(nbt.getCompound("fluidVariant")));
        fluidContainer.setAmount(nbt.getLong("amount"));
    }

    public LibyFluidContainer getFluidContainer() {
        return this.fluidContainer;
    }
}
