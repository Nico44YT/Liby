package nazario.liby.api.util.nbt;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import java.util.List;
import java.util.UUID;

public class LibyNbtCompoundBuilder {

    NbtCompound nbtCompound;

    public static LibyNbtCompoundBuilder create() {
        return new LibyNbtCompoundBuilder();
    }

    public static LibyNbtCompoundBuilder create(NbtCompound nbtCompound) {
        return new LibyNbtCompoundBuilder(nbtCompound);
    }

    protected LibyNbtCompoundBuilder() {
        this.nbtCompound = new NbtCompound();
    }

    protected LibyNbtCompoundBuilder(NbtCompound nbtCompound) {
        this.nbtCompound = nbtCompound;
    }

    //region// * NbtCompound put method wrappers * //
    public LibyNbtCompoundBuilder putBoolean(String key, boolean value) {
        nbtCompound.putBoolean(key, value);
        return this;
    }

    public LibyNbtCompoundBuilder putByte(String key, byte value) {
        nbtCompound.putByte(key, value);
        return this;
    }

    public LibyNbtCompoundBuilder putShort(String key, short value) {
        nbtCompound.putShort(key, value);
        return this;
    }

    public LibyNbtCompoundBuilder putInt(String key, int value) {
        nbtCompound.putInt(key, value);
        return this;
    }

    public LibyNbtCompoundBuilder putLong(String key, long value) {
        nbtCompound.putLong(key, value);
        return this;
    }

    public LibyNbtCompoundBuilder putFloat(String key, float value) {
        nbtCompound.putFloat(key, value);
        return this;
    }

    public LibyNbtCompoundBuilder putDouble(String key, double value) {
        nbtCompound.putDouble(key, value);
        return this;
    }

    public LibyNbtCompoundBuilder putByteArray(String key, byte[] value) {
        nbtCompound.putByteArray(key, value);
        return this;
    }

    public LibyNbtCompoundBuilder putByteArray(String key, List<Byte> value) {
        nbtCompound.putByteArray(key, value);
        return this;
    }

    public LibyNbtCompoundBuilder putIntArray(String key, int[] value) {
        nbtCompound.putIntArray(key, value);
        return this;
    }

    public LibyNbtCompoundBuilder putIntArray(String key, List<Integer> value) {
        nbtCompound.putIntArray(key, value);
        return this;
    }

    public LibyNbtCompoundBuilder putLongArray(String key, long[] value) {
        nbtCompound.putLongArray(key, value);
        return this;
    }

    public LibyNbtCompoundBuilder putLongArray(String key, List<Long> value) {
        nbtCompound.putLongArray(key, value);
        return this;
    }

    public LibyNbtCompoundBuilder putString(String key, String value) {
        nbtCompound.putString(key, value);
        return this;
    }

    public LibyNbtCompoundBuilder putUUID(String key, UUID value) {
        nbtCompound.putUuid(key, value);
        return this;
    }

    public LibyNbtCompoundBuilder put(String key, NbtElement value) {
        nbtCompound.put(key, value);
        return this;
    }

    //Custom
    public LibyNbtCompoundBuilder putFloatArray(String key, float[] value) {
        NbtCompound array = new NbtCompound();
        array.putString("type", "floatArray");
        array.putInt("length", value.length);

        for (int i = 0; i < value.length; i++) {
            array.putFloat(String.valueOf(i), value[i]);
        }

        nbtCompound.put(key, array);
        return this;
    }

    public LibyNbtCompoundBuilder putFloatArray(String key, List<Float> value) {
        NbtCompound array = new NbtCompound();
        array.putString("type", "floatArray");
        array.putInt("length", value.size());

        for (int i = 0; i < value.size(); i++) {
            array.putFloat(String.valueOf(i), value.get(i));
        }

        nbtCompound.put(key, array);
        return this;
    }

    public LibyNbtCompoundBuilder putVec3d(String key, Vec3d value) {
        NbtCompound nbt = LibyNbtCompoundBuilder.create()
                .putString("type", "vec3d")
                .putDouble("x", value.getX())
                .putDouble("y", value.getY())
                .putDouble("z", value.getZ())
                .build();

        this.nbtCompound.put(key, nbt);

        return this;
    }

    public LibyNbtCompoundBuilder putVec3i(String key, Vec3i value) {
        NbtCompound nbt = LibyNbtCompoundBuilder.create()
                .putString("type", "vec3i")
                .putInt("x", value.getX())
                .putInt("y", value.getY())
                .putInt("z", value.getZ())
                .build();

        this.nbtCompound.put(key, nbt);

        return this;
    }

    public LibyNbtCompoundBuilder putVec2f(String key, Vec2f value) {
        NbtCompound nbt = LibyNbtCompoundBuilder.create()
                .putString("type", "vec2f")
                .putFloat("x", value.x)
                .putFloat("y", value.y)
                .build();

        this.nbtCompound.put(key, nbt);

        return this;
    }

    public LibyNbtCompoundBuilder putBlockPos(String key, BlockPos value) {
        NbtCompound nbt = LibyNbtCompoundBuilder.create()
                .putString("type", "blockpos")
                .putInt("x", value.getX())
                .putInt("y", value.getY())
                .putInt("z", value.getZ())
                .build();

        this.nbtCompound.put(key, nbt);

        return this;
    }

    public LibyNbtCompoundBuilder putUUIDArray(String key, UUID... value) {
        NbtCompound array = new NbtCompound();
        array.putString("type", "uuidArray");
        array.putInt("length", value.length);

        for (int i = 0; i < value.length; i++) {
            array.putUuid(String.valueOf(i), value[i]);
        }

        nbtCompound.put(key, array);
        return this;
    }

    public LibyNbtCompoundBuilder putEntityType(String key, EntityType<? extends Entity> entityType) {
        NbtCompound entityTypeCompound = new NbtCompound();
        entityTypeCompound.putString("type", "entity_type");
        entityTypeCompound.putString("entity_type", entityType.toString());

        return this;
    }

    //endregion

    public NbtCompound build() {
        return this.nbtCompound;
    }

    public LibyNbtCompoundReader toReader() {
        return LibyNbtCompoundReader.create(this.build());
    }

    public LibyNbtCompoundBuilder copy() {
        return LibyNbtCompoundBuilder.create(this.build());
    }
}