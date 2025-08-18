package nazario.liby.api.item;

import net.minecraft.nbt.NbtCompound;

public interface LibyItemStackAdditions {
    void liby$setNbt(NbtCompound nbtCompound);
    NbtCompound liby$getNbt();
    void liby$readNbt(NbtCompound nbtCompound);
}
