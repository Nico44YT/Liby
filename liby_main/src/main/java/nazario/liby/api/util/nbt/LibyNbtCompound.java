package nazario.liby.api.util.nbt;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector4f;

import java.io.StringReader;
import java.time.Instant;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class LibyNbtCompound extends NbtCompound {

    public LibyNbtCompound() {
        this(new NbtCompound());
    }

    public LibyNbtCompound(NbtCompound nbt) {
        this.copyFrom(nbt);
    }

    //region // Get Methods
    @Deprecated @ApiStatus.Experimental @ApiStatus.ScheduledForRemoval
    public Entity getEntity(String key, @NotNull World world) {
        LibyNbtCompound nbt = this.getLibyCompound(key);

        if(nbt.getString("type").equals("entity")) {
            EntityType<?> entityType = nbt.getEntityType("entity_type");

            Entity entity = entityType.create(world);
            entity.setUuid(nbt.getUuid("uuid"));
            entity.readNbt(nbt.getLibyCompound("entity"));
            return entity;
        }

        return null;
    }

    public Date getDate(String key) {
        LibyNbtCompound nbt = this.getLibyCompound(key);

        if(nbt.getString("type").equals("date")) {
            return Date.from(Instant.parse(nbt.getString("date")));
        }

        return null;
    }

    public <T extends Enum<T>> T getEnum(String key, Class<T> enumClass) {
        LibyNbtCompound nbt = this.getLibyCompound(key);

        if(nbt.getString("type").equals("enum")) {
            return Enum.valueOf(enumClass, nbt.getString("enum"));
        }

        return null;
    }

    public LibyNbtCompound getLibyCompound(String key) {
        return new LibyNbtCompound(this.getCompound(key));
    }

    public <T extends NbtConvertible> T getNbtConvertible(String key, Function<NbtCompound, T> function) {
        NbtCompound nbt = this.getCompound(key);

        if(!nbt.getString("type").equals("nbtconvertible")) return null;

        return function.apply(nbt.getCompound("nbtconvertible"));
    }

    public Optional<ItemStack> getItemStack(RegistryWrapper.WrapperLookup registries, String key) {
        NbtCompound nbt = this.getCompound(key);

        if(!nbt.getString("type").equals("itemstack")) return null;

        return ItemStack.fromNbt(registries, nbt.getCompound("value"));
    }

    public Optional<ItemStack>[] getItemStackArray(RegistryWrapper.WrapperLookup registries, String key) {
        LibyNbtCompound nbt = this.getLibyCompound(key);

        if(!nbt.getString("type").equals("itemstackArray")) return null;

        int length = nbt.getInt("length");

        Optional<ItemStack>[] array = new Optional[length];

        for (int i = 0; i < length; i++) {
            array[i] = nbt.getItemStack(registries, String.valueOf(i));
        }

        return array;
    }

    public Identifier getIdentifier(String key) {
        NbtCompound idNbt = this.getCompound(key);
        String path = idNbt.getString("path");
        String namespace = idNbt.getString("namespace");

        if(idNbt.getString("type").equals("identifier")){
            return Identifier.of(namespace, path);
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

    @SuppressWarnings("unchecked")
    public <T extends NbtConvertible> T[] getNbtConvertibleArray(String key, Function<NbtCompound, T> factory) {
        LibyNbtCompound element = this.getLibyCompound(key);

        try {
            if (element.getString("type").equals("convertibleArray")) {
                T[] array = (T[]) new NbtConvertible[element.getInt("length")];
                for (int i = 0; i < array.length; i++) {
                    array[i] = element.getNbtConvertible(String.valueOf(i), factory);
                }

                return array;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public NbtCompound[] getCompoundArray(String key) {
        LibyNbtCompound element = this.getLibyCompound(key);

        try {
            if(element.getString("type").equals("compoundArray")) {
                NbtCompound[] array = new NbtCompound[element.getInt("length")];
                for (int i = 0; i < array.length; i++) {
                    array[i] = element.getCompound(String.valueOf(i));
                }
                return array;
            }
        }catch (Exception e) {
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
    //endregion

    //region//Put Methods
    public void putEntity(String key, Entity entity) {
        LibyNbtCompound entityNbt = new LibyNbtCompound();
        entity.saveNbt(entityNbt);

        LibyNbtCompound holder = new LibyNbtCompound();

        holder.putString("type", "entity");

        holder.putUuid("uuid", entity.getUuid());
        holder.putEntityType("entity_type", entity.getType());
        holder.put("entity", entityNbt);

        this.put(key, holder);
    }

    public void putDate(String key, Date value) {
        LibyNbtCompound nbt = new LibyNbtCompound();

        nbt.putString("type", "date");
        nbt.putString("date", value.toInstant().toString());

        this.put(key, nbt);
    }

    public void putEnum(String key, Enum<?> value) {
        LibyNbtCompound nbt = new LibyNbtCompound();

        nbt.putString("type", "enum");
        nbt.putString("enum", value.name());
        nbt.putString("class", value.getDeclaringClass().getCanonicalName());

        this.put(key, nbt);
    }

    public void putNbtConvertible(String key, NbtConvertible value) {
        NbtCompound nbt = new NbtCompound();

        nbt.putString("type", "nbtconvertible");
        LibyNbtCompound data = new LibyNbtCompound();
        value.writeToNbt(data);
        nbt.put("nbtconvertible", data);

        this.put(key, nbt);
    }


    /* TODO
    public void putItemStack(String key, ItemStack value) {
        NbtCompound nbt = new NbtCompound();

        nbt.putString("type", "itemstack");
        nbt.put("value", value.applyComponentsFrom(new NbtCompound()));

        this.put(key, nbt);
    }


    public void putItemStackArray(String key, ItemStack[] value) {
        LibyNbtCompound array = new LibyNbtCompound();

        array.putString("type", "itemstackArray");
        array.putInt("length", value.length);

        for (int i = 0; i < value.length; i++) {
            array.putItemStack(String.valueOf(i), value[i]);
        }

        this.put(key, array);
    }
     */

    public void putIdentifier(String key, Identifier value) {
        NbtCompound idNbt = new NbtCompound();
        idNbt.putString("path", value.getPath());
        idNbt.putString("namespace", value.getNamespace());

        idNbt.putString("type", "identifier");

        this.put(key, idNbt);
    }

    public void putNbtConvertibleArray(String key, NbtConvertible[] value) {
        LibyNbtCompound array = new LibyNbtCompound();
        array.putString("type", "convertibleArray");
        array.putInt("length", value.length);

        for (int i = 0; i < value.length; i++) {
            array.putNbtConvertible(String.valueOf(i), value[i]);
        }

        this.put(key, array);
    }

    public void putFloatArray(String key, Float[] value) {
        NbtCompound array = new NbtCompound();
        array.putString("type", "floatArray");
        array.putInt("length", value.length);

        for (int i = 0; i < value.length; i++) {
            array.putFloat(String.valueOf(i), value[i]);
        }

        this.put(key, array);
    }

    public void putFloatArray(String key, List<Float> value) {
        this.putFloatArray(key, value.toArray(Float[]::new));
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
    public void forEach(BiConsumer<String, NbtElement> consumer) {
        this.getKeys().forEach(key -> {
            consumer.accept(key, this.get(key));
        });
    }

    public void putIfAbsent(String key, NbtElement value) {
        if(!isPresent(key)) this.put(key, value);
    }

    public boolean isPresent(String key) { //This method is only for consistency
        return this.contains(key);
    }

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

    public void ifPresent(String key, Consumer<NbtElement> consumer) {
        consumer.accept(this.get(key));
    }

    public void ifPresentOrElse(String key, Consumer<NbtElement> consumer, Runnable fallback) {
        if (this.contains(key)) {
            consumer.accept(this.get(key));
        } else {
            fallback.run();
        }
    }

    public void merge(NbtCompound otherCompound) {
        otherCompound.getKeys().forEach(key -> {
            this.put(key, otherCompound.get(key));
        });
    }

    public JsonObject asJsonObject() {
        return JsonParser.parseReader(new StringReader(this.toString())).getAsJsonObject();
    }
    //endregion
}