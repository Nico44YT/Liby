package nazario.liby.nbt;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class NbtCompoundReader {

    NbtCompound nbtCompound;
    public static NbtCompoundReader create(NbtCompound nbtCompound) {
        return new NbtCompoundReader(nbtCompound);
    }
    protected NbtCompoundReader(NbtCompound nbtCompound) {
        this.nbtCompound = nbtCompound;
    }

    public Vec3d getVec3d(String key) {
        NbtCompound element = nbtCompound.getCompound(key);
        try{
            if(element.getString("type").equals("vec3d")) {
                return new Vec3d(element.getDouble("x"), element.getDouble("y"), element.getDouble("z"));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        return Vec3d.ZERO;
    }

    public Vec3i getVec3i(String key) {
        NbtCompound element = nbtCompound.getCompound(key);

        try{
            if(element.getString("type").equals("vec3i")) {
                return new Vec3i(element.getInt("x"), element.getInt("y"), element.getInt("z"));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        return Vec3i.ZERO;
    }

    public Vec2f getVec2f(String key){
        NbtCompound element = nbtCompound.getCompound(key);

        try{
            if(element.getString("type").equals("vec2f")) {
                return new Vec2f(element.getInt("x"), element.getInt("y"));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        return Vec2f.ZERO;
    }

    public BlockPos getBlockPos(String key) {
        NbtCompound element = nbtCompound.getCompound(key);

        try{
            if(element.getString("type").equals("blockpos")) {
                return new BlockPos(element.getInt("x"), element.getInt("y"), element.getInt("z"));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        return BlockPos.ORIGIN;
    }

    public float[] getFloatArray(String key) {
        NbtCompound element = nbtCompound.getCompound(key);

        try{
            if(element.getString("type").equals("floatArray")) {
                float[] array = new float[element.getInt("length")];
                for(int i = 0;i<array.length;i++) {
                    array[i] = element.getFloat(String.valueOf(i));
                }
                return array;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new float[]{};
    }

    public NbtCompound getCompound(String key) {
        return nbtCompound.getCompound(key);
    }

    public NbtCompoundReader getCompoundAsReader(String key) {
        return NbtCompoundReader.create(getCompound(key));
    }

    public NbtCompoundBuilder getCompoundAsBuilder(String key) {
        return NbtCompoundBuilder.create(getCompound(key));
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