package nazario.liby.api.world.v1.fluid_container;

import nazario.liby_networking.api.LibyBlockEntityNbtUpdateS2C;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;

@SuppressWarnings("all")
public class LibyFluidContainer extends SingleVariantStorage<FluidVariant> {

    protected long capacity;
    protected BlockEntity parent;

    public <T extends BlockEntity> LibyFluidContainer(T parent, long capacity) {
        this.parent = parent;
        this.capacity = capacity;
    }

    @Override
    protected FluidVariant getBlankVariant() {
        return FluidVariant.blank();
    }

    @Override
    protected long getCapacity(FluidVariant fluidVariant) {
        return this.capacity;
    }

    @Override
    protected void onFinalCommit() {
        parent.markDirty();

        if(!parent.getWorld().isClient()) {
            PlayerLookup.tracking(parent).forEach(player -> {
                ServerPlayNetworking.send(player, LibyBlockEntityNbtUpdateS2C.create(parent));
            });
        }
    }

    public FluidVariant getVariant() {
        return this.variant;
    }

    public void setVariant(FluidVariant variant) {
        this.variant = variant;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public FluidState getFluidState() {
        return this.variant.getFluid().getDefaultState();
    }

    public float getFillPrecentage() {
        return (float)this.getAmount() / (float)this.getCapacity();
    }
}
