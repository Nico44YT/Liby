package nazario.liby.api.util.nbt;

import nazario.liby.api.util.math.Vec2i;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

public class LibyNbtCompound extends NbtCompound {

    public LibyNbtCompound(NbtCompound nbt) {
        this.copyFrom(nbt);
    }

    //region//Get Methods
    public ItemStack getItemStack(String key) {
        NbtCompound nbt = this.getCompound(key);

        if(!nbt.getString("type").equals("itemstack")) return null;

        return ItemStack.fromNbt(nbt.getCompound("value"));
    }

    public Identifier getIdentifier(String key) {
        NbtCompound idNbt = this.getCompound(key);
        String path = idNbt.getString("path");
        String namespace = idNbt.getString("namespace");

        if(idNbt.getString("type").equals("modelIdentifier")) {
            String variant = idNbt.getString("variant");
            return new ModelIdentifier(Identifier.of(namespace, path), variant);
        } else if(idNbt.getString("type").equals("identifier")){
            return Identifier.of(namespace, path);
        }

        return null;
    }

    public Vec2i getVec2i(String key) {
        NbtCompound element = this.getCompound(key);
        try {
            if (element.getString("type").equals("vec2i")) {
                return new Vec2i(element.getInt("x"), element.getInt("y"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Vec3d getVec3d(String key) {
        NbtCompound element = this.getCompound(key);
        try {
            if (element.getString("type").equals("vec3d")) {
                return new Vec3d(element.getDouble("x"), element.getDouble("y"), element.getDouble("z"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Vec3i getVec3i(String key) {
        NbtCompound element = this.getCompound(key);

        try {
            if (element.getString("type").equals("vec3i")) {
                return new Vec3i(element.getInt("x"), element.getInt("y"), element.getInt("z"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Vec2f getVec2f(String key) {
        NbtCompound element = this.getCompound(key);

        try {
            if (element.getString("type").equals("vec2f")) {
                return new Vec2f(element.getInt("x"), element.getInt("y"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Quaternion getQuaternion(String key) {
        NbtCompound element = this.getCompound(key);

        try {
            if (element.getString("type").equals("quaternion")) {
                return new Quaternion(element.getFloat("x"), element.getFloat("y"), element.getFloat("z"), element.getFloat("w"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public Vector4f getVector4f(String key) {
        NbtCompound element = this.getCompound(key);

        try {
            if (element.getString("type").equals("vector4f")) {
                return new Vector4f(element.getFloat("x"), element.getFloat("y"), element.getFloat("z"), element.getFloat("w"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public BlockPos getBlockPos(String key) {
        NbtCompound element = this.getCompound(key);

        try {
            if (element.getString("type").equals("blockpos")) {
                return new BlockPos(element.getInt("x"), element.getInt("y"), element.getInt("z"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public float[] getFloatArray(String key) {
        NbtCompound element = this.getCompound(key);

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
        return null;
    }

    public UUID[] getUUIDArray(String key) {
        NbtCompound element = this.getCompound(key);

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

        return null;
    }

    public String[] getStringArray(String key) {
        NbtCompound element = this.getCompound(key);

        try {
            if (element.getString("type").equals("stringArray")) {
                String[] array = new String[element.getInt("length")];
                for (int i = 0; i < array.length; i++) {
                    array[i] = element.getString(String.valueOf(i));
                }
                return array;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public EntityType<? extends Entity> getEntityType(String key) {
        NbtCompound element = this.getCompound(key);

        try {
            if (element.getString("type").equals("entity_type")) {
                EntityType<? extends Entity> entityType = Registry.ENTITY_TYPE.stream()
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
    //endregion

    //region//Put Methods

    public void putQuaternion(String key, Quaternion value) {
        NbtCompound nbt = LibyNbtCompoundBuilder.create()
                .putString("type", "quaternion")
                .putFloat("x", value.getX())
                .putFloat("y", value.getY())
                .putFloat("z", value.getZ())
                .putFloat("w", value.getW())
                .build();

        this.put(key, nbt);
    }

    public void putItemStack(String key, ItemStack value) {
        NbtCompound nbt = new NbtCompound();

        nbt.putString("type", "itemstack");
        nbt.put("value", value.writeNbt(new NbtCompound()));

        this.put(key, nbt);
    }

    public void putIdentifier(String key, Identifier value) {
        NbtCompound idNbt = new NbtCompound();
        idNbt.putString("path", value.getPath());
        idNbt.putString("namespace", value.getNamespace());

        if(value instanceof ModelIdentifier modelIdentifier) {
            idNbt.putString("variant", modelIdentifier.getVariant());
            idNbt.putString("type", "modelIdentifier");
        } else {
            idNbt.putString("type", "identifier");
        }

        this.put(key, idNbt);
    }

    public void putFloatArray(String key, float[] value) {
        NbtCompound array = new NbtCompound();
        array.putString("type", "floatArray");
        array.putInt("length", value.length);

        for (int i = 0; i < value.length; i++) {
            array.putFloat(String.valueOf(i), value[i]);
        }

        this.put(key, array);
    }

    public void putFloatArray(String key, List<Float> value) {
        NbtCompound array = new NbtCompound();
        array.putString("type", "floatArray");
        array.putInt("length", value.size());

        for (int i = 0; i < value.size(); i++) {
            array.putFloat(String.valueOf(i), value.get(i));
        }

        this.put(key, array);
    }

    public void putVec2i(String key, Vec2i value) {
        NbtCompound nbt = LibyNbtCompoundBuilder.create()
                .putString("type", "vec2i")
                .putInt("x", value.x)
                .putInt("y", value.y)
                .build();

        this.put(key, nbt);
    }

    public void putVec3d(String key, Vec3d value) {
        NbtCompound nbt = LibyNbtCompoundBuilder.create()
                .putString("type", "vec3d")
                .putDouble("x", value.getX())
                .putDouble("y", value.getY())
                .putDouble("z", value.getZ())
                .build();

        this.put(key, nbt);
    }

    public void putVec3i(String key, Vec3i value) {
        NbtCompound nbt = LibyNbtCompoundBuilder.create()
                .putString("type", "vec3i")
                .putInt("x", value.getX())
                .putInt("y", value.getY())
                .putInt("z", value.getZ())
                .build();

        this.put(key, nbt);
    }

    public void putVec2f(String key, Vec2f value) {
        NbtCompound nbt = LibyNbtCompoundBuilder.create()
                .putString("type", "vec2f")
                .putFloat("x", value.x)
                .putFloat("y", value.y)
                .build();

        this.put(key, nbt);
    }

    public void putVector4f(String key, Vector4f value) {
        NbtCompound nbt = LibyNbtCompoundBuilder.create()
                .putString("type", "vector4f")
                .putFloat("x", value.getX())
                .putFloat("y", value.getY())
                .putFloat("z", value.getZ())
                .putFloat("w", value.getW())
                .build();

        this.put(key, nbt);
    }

    public void putBlockPos(String key, BlockPos value) {
        NbtCompound nbt = LibyNbtCompoundBuilder.create()
                .putString("type", "blockpos")
                .putInt("x", value.getX())
                .putInt("y", value.getY())
                .putInt("z", value.getZ())
                .build();

        this.put(key, nbt);
    }

    public void putUUIDArray(String key, UUID... value) {
        NbtCompound array = new NbtCompound();
        array.putString("type", "uuidArray");
        array.putInt("length", value.length);

        for (int i = 0; i < value.length; i++) {
            array.putUuid(String.valueOf(i), value[i]);
        }

        this.put(key, array);
    }

    public void putEntityType(String key, EntityType<? extends Entity> entityType) {
        NbtCompound entityTypeCompound = new NbtCompound();
        entityTypeCompound.putString("type", "entity_type");
        entityTypeCompound.putString("entity_type", entityType.toString());

        this.put(key, entityTypeCompound);
    }

    public void putStringArray(String key, String[] value) {
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("length", value.length);
        nbt.putString("type", "stringArray");
        for(int i = 0;i<value.length;i++) {
            nbt.putString(String.valueOf(i), value[i]);
        }

        this.put(key, nbt);
    }

    //endregion

    //region//Utility
    public void removeIfPresent(String key) {
        if (this.contains(key)) {
            this.remove(key);
        }
    }

    public Object getAndRemove(String key) {
        if(this.get(key) == null) return null;

        assert this.get(key) != null;
        Object obj = Objects.requireNonNull(this.get(key)).copy();
        this.removeIfPresent(key);
        return obj;
    }

    public void ifPresent(String key, Consumer<Object> consumer) {
        consumer.accept(this.get(key));
    }

    public void ifPresentOrElse(String key, Consumer<Object> consumer, Runnable fallback) {
        if (this.contains(key)) {
            consumer.accept(this.get(key));
        } else {
            fallback.run();
        }
    }
    //endregion
}
