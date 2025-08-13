package nazario.liby.api.util.nbt;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import java.util.UUID;

@Deprecated
public class LibyNbtCompoundReader {

    NbtCompound nbtCompound;

    public static LibyNbtCompoundReader create(NbtCompound nbtCompound) {
        return new LibyNbtCompoundReader(nbtCompound);
    }

    protected LibyNbtCompoundReader(NbtCompound nbtCompound) {
        this.nbtCompound = nbtCompound;
    }

    public Vec3d getVec3d(String key) {
        NbtCompound element = nbtCompound.getCompound(key);
        try {
            if (element.getString("type").equals("vec3d")) {
                return new Vec3d(element.getDouble("x"), element.getDouble("y"), element.getDouble("z"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Vec3d.ZERO;
    }

    public Vec3i getVec3i(String key) {
        NbtCompound element = nbtCompound.getCompound(key);

        try {
            if (element.getString("type").equals("vec3i")) {
                return new Vec3i(element.getInt("x"), element.getInt("y"), element.getInt("z"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Vec3i.ZERO;
    }

    public Vec2f getVec2f(String key) {
        NbtCompound element = nbtCompound.getCompound(key);

        try {
            if (element.getString("type").equals("vec2f")) {
                return new Vec2f(element.getInt("x"), element.getInt("y"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Vec2f.ZERO;
    }

    public BlockPos getBlockPos(String key) {
        NbtCompound element = nbtCompound.getCompound(key);

        try {
            if (element.getString("type").equals("blockpos")) {
                return new BlockPos(element.getInt("x"), element.getInt("y"), element.getInt("z"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return BlockPos.ORIGIN;
    }

    public float[] getFloatArray(String key) {
        NbtCompound element = nbtCompound.getCompound(key);

        try {
            if (element.getString("type").equals("floatArray")) {
                float[] array = new float[element.getInt("length")];
                for (int i = 0; i < array.length; i++) {
                    array[i] = element.getFloat(String.valueOf(i));
                }
                return array;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new float[]{};
    }

    public UUID[] getUUIDArray(String key) {
        NbtCompound element = nbtCompound.getCompound(key);

        try {
            if (element.getString("type").equals("uuidArray")) {
                UUID[] array = new UUID[element.getInt("length")];
                for (int i = 0; i < array.length; i++) {
                    array[i] = element.getUuid(String.valueOf(i));
                }
                return array;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new UUID[]{};
    }

    public EntityType<? extends Entity> getEntityType(String key) {
        NbtCompound element = nbtCompound.getCompound(key);

        try {
            if (element.getString("type").equals("entity_type")) {
                EntityType<? extends Entity> entityType = Registries.ENTITY_TYPE.stream()
                        .filter(entry -> entry.toString().equals(element.getString("entity_type")))
                        .findFirst()
                        .orElse(null);
                return entityType;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public NbtCompound getCompound(String key) {
        return nbtCompound.getCompound(key);
    }

    public LibyNbtCompoundReader getCompoundAsReader(String key) {
        return LibyNbtCompoundReader.create(getCompound(key));
    }

    public LibyNbtCompoundBuilder getCompoundAsBuilder(String key) {
        return LibyNbtCompoundBuilder.create(getCompound(key));
    }

    public boolean isPresent(String key) {
        return nbtCompound.contains(key);
    }

    public boolean isPresent(String key, int type) {
        return nbtCompound.contains(key, type);
    }

    public boolean isUuidPresent(String key) {
        return nbtCompound.containsUuid(key);
    }

    public NbtCompound asCompound() {
        return this.nbtCompound;
    }
}