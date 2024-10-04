package nazario.liby.nbt;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import java.util.List;
import java.util.UUID;

public class NbtCompoundBuilder {

    NbtCompound nbtCompound;
    public static NbtCompoundBuilder create() {
        return new NbtCompoundBuilder();
    }

    public static NbtCompoundBuilder create(NbtCompound nbtCompound) {
        return new NbtCompoundBuilder(nbtCompound);
    }

    protected NbtCompoundBuilder() {
        this.nbtCompound = new NbtCompound();
    }
    protected NbtCompoundBuilder(NbtCompound nbtCompound) {
        this.nbtCompound = nbtCompound;
    }

    //region// * NbtCompound put method wrappers * //
    public NbtCompoundBuilder putBoolean(String key, boolean value) {
        nbtCompound.putBoolean(key, value);
        return this;
    }

    public NbtCompoundBuilder putByte(String key, byte value) {
        nbtCompound.putByte(key, value);
        return this;
    }

    public NbtCompoundBuilder putShort(String key, short value) {
        nbtCompound.putShort(key, value);
        return this;
    }

    public NbtCompoundBuilder putInt(String key, int value) {
        nbtCompound.putInt(key, value);
        return this;
    }

    public NbtCompoundBuilder putLong(String key, long value) {
        nbtCompound.putLong(key, value);
        return this;
    }

    public NbtCompoundBuilder putFloat(String key, float value) {
        nbtCompound.putFloat(key, value);
        return this;
    }

    public NbtCompoundBuilder putDouble(String key, double value) {
        nbtCompound.putDouble(key, value);
        return this;
    }

    public NbtCompoundBuilder putByteArray(String key, byte[] value) {
        nbtCompound.putByteArray(key, value);
        return this;
    }

    public NbtCompoundBuilder putByteArray(String key, List<Byte> value) {
        nbtCompound.putByteArray(key, value);
        return this;
    }

    public NbtCompoundBuilder putIntArray(String key, int[] value) {
        nbtCompound.putIntArray(key, value);
        return this;
    }

    public NbtCompoundBuilder putIntArray(String key, List<Integer> value) {
        nbtCompound.putIntArray(key, value);
        return this;
    }

    public NbtCompoundBuilder putLongArray(String key, long[] value) {
        nbtCompound.putLongArray(key, value);
        return this;
    }

    public NbtCompoundBuilder putLongArray(String key, List<Long> value) {
        nbtCompound.putLongArray(key, value);
        return this;
    }

    public NbtCompoundBuilder putString(String key, String value) {
        nbtCompound.putString(key, value);
        return this;
    }

    public NbtCompoundBuilder putUUID(String key, UUID value) {
        nbtCompound.putUuid(key, value);
        return this;
    }

    public NbtCompoundBuilder put(String key, NbtElement value) {
        nbtCompound.put(key, value);
        return this;
    }

    //Custom
    public NbtCompoundBuilder putFloatArray(String key, float[] value) {
        NbtCompound array = new NbtCompound();
        array.putString("type", "floatArray");
        array.putInt("length", value.length);

        for(int i = 0; i < value.length; i++) {
            array.putFloat(String.valueOf(i), value[i]);
        }

        nbtCompound.put(key, array);
        return this;
    }

    public NbtCompoundBuilder putFloatArray(String key, List<Float> value) {
        NbtCompound array = new NbtCompound();
        array.putString("type","floatArray");
        array.putInt("length", value.size());

        for(int i = 0; i < value.size(); i++) {
            array.putFloat(String.valueOf(i), value.get(i));
        }

        nbtCompound.put(key, array);
        return this;
    }

    public NbtCompoundBuilder putVec3d(String key, Vec3d value) {
        NbtCompound nbt = NbtCompoundBuilder.create()
                .putString("type", "vec3d")
                .putDouble("x", value.getX())
                .putDouble("y", value.getY())
                .putDouble("z", value.getZ())
                .build();

        this.nbtCompound.put(key, nbt);

        return this;
    }

    public NbtCompoundBuilder putVec3i(String key, Vec3i value) {
        NbtCompound nbt = NbtCompoundBuilder.create()
                .putString("type", "vec3i")
                .putInt("x", value.getX())
                .putInt("y", value.getY())
                .putInt("z", value.getZ())
                .build();

        this.nbtCompound.put(key, nbt);

        return this;
    }

    public NbtCompoundBuilder putBlockPos(String key, BlockPos value) {
        NbtCompound nbt = NbtCompoundBuilder.create()
                .putString("type", "blockpos")
                .putInt("x", value.getX())
                .putInt("y", value.getY())
                .putInt("z", value.getZ())
                .build();

        this.nbtCompound.put(key, nbt);

        return this;
    }

    //endregion

    public NbtCompound build() {
        return this.nbtCompound;
    }
}