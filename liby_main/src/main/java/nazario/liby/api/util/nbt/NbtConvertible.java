package nazario.liby.api.util.nbt;

public interface NbtConvertible {
    public void readFromNbt(LibyNbtCompound tag);
    public void writeToNbt(LibyNbtCompound tag);
}
