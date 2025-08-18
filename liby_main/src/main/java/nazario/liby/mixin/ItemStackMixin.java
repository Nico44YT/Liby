package nazario.liby.mixin;

import nazario.liby.api.item.LibyItemStackAdditions;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements LibyItemStackAdditions {

    @Override
    public void liby$setNbt(NbtCompound nbtCompound) {
        ItemStack stack = (ItemStack)(Object)this;

        //stack.set(DataComponentTypes.CUSTOM_DATA, nbtCompound);
    }

    @Override
    public NbtCompound liby$getNbt() {
        return null;
    }

    @Override
    public void liby$readNbt(NbtCompound nbtCompound) {

    }
}
