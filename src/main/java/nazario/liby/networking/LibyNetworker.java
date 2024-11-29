package nazario.liby.networking;

import nazario.liby.nbt.NbtCompoundBuilder;
import nazario.liby.networking.payloads.LibySyncPacket;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.Field;
import java.util.*;

@ApiStatus.Experimental
public class LibyNetworker {
    @ApiStatus.Experimental
    public static void syncBlock(World world, BlockPos pos, Block block) {
        NbtCompoundBuilder packetBuilder = NbtCompoundBuilder.create();
        packetBuilder.putEnum("type", LibySyncType.BLOCK_SYNC, LibySyncType.class);
        packetBuilder.putBlockPos("pos", pos);

        for(ServerPlayerEntity serverPlayer : PlayerLookup.tracking((ServerWorld)world, pos)) {
            LibyNetworker.sync(serverPlayer, packetBuilder, block);
        }
    }

    public static void syncBlockEntity(World world, BlockPos pos, BlockEntity blockEntity) {
        NbtCompoundBuilder packetBuilder = NbtCompoundBuilder.create();
        packetBuilder.putEnum("type", LibySyncType.BLOCK_ENTITY_SYNC, LibySyncType.class);
        packetBuilder.putBlockPos("pos", pos);

        for(ServerPlayerEntity serverPlayer : PlayerLookup.tracking((ServerWorld)world, pos)) {
            LibyNetworker.sync(serverPlayer, packetBuilder, blockEntity);
        }
    }

    @ApiStatus.Experimental
    public static void sync(ServerPlayerEntity playerEntity, NbtCompoundBuilder packetBuilder, Object object) {
        packetBuilder.putString("class", object.getClass().getCanonicalName());

        packetBuilder.put("data", addObject(object).build());

        ServerPlayNetworking.send(playerEntity, new LibySyncPacket(packetBuilder.build()));
    }

    @ApiStatus.Internal
    public static NbtCompoundBuilder addObject(Object object) {
        NbtCompoundBuilder dataBuilder = NbtCompoundBuilder.create();

        try {
            for (Field field : object.getClass().getFields()) {
                if (field.isAnnotationPresent(LibySyncedValue.class)) {
                    field.setAccessible(true); // Make private fields accessible
                    Object value = field.get(object);

                    //region
                    Map<Class<?>, Runnable> typeHandlers = new HashMap<>();

                    typeHandlers.put(Integer.class, () -> {
                        dataBuilder.putInt(field.getName(), (Integer) value);
                    });

                    typeHandlers.put(Boolean.class, () -> {
                        dataBuilder.putBoolean(field.getName(), (Boolean) value);
                    });

                    typeHandlers.put(Double.class, () -> {
                        dataBuilder.putDouble(field.getName(), (Double) value);
                    });

                    typeHandlers.put(String.class, () -> {
                        dataBuilder.putString(field.getName(), (String) value);
                    });

                    typeHandlers.put(Float.class, () -> {
                        dataBuilder.putFloat(field.getName(), (Float) value);
                    });

                    typeHandlers.put(Long.class, () -> {
                        dataBuilder.putLong(field.getName(), (Long) value);
                    });

                    typeHandlers.put(UUID.class, () -> {
                        dataBuilder.putUUID(field.getName(), (UUID) value);
                    });

                    typeHandlers.put(BlockPos.class, () -> {
                        dataBuilder.putBlockPos(field.getName(), (BlockPos) value);
                    });

                    typeHandlers.put(Integer[].class, () -> {
                        dataBuilder.putIntArray(field.getName(), List.of((Integer[])value));
                    });

                    typeHandlers.put(Float[].class, () -> {
                        dataBuilder.putFloatArray(field.getName(), List.of((Float[])value));
                    });

                    typeHandlers.put(Vec2f.class, () -> {
                        dataBuilder.putVec2f(field.getName(), (Vec2f) value);
                    });

                    typeHandlers.put(Vec3d.class, () -> {
                        dataBuilder.putVec3d(field.getName(), (Vec3d) value);
                    });

                    typeHandlers.put(Vec3i.class, () -> {
                        dataBuilder.putVec3i(field.getName(), (Vec3i) value);
                    });

                    typeHandlers.put(Byte[].class, () -> {
                        dataBuilder.putByteArray(field.getName(), List.of((Byte[])value));
                    });

                    typeHandlers.put(Long[].class, () -> {
                        dataBuilder.putLongArray(field.getName(), List.of((Long[])value));
                    });

                    typeHandlers.put(Short.class, () -> {
                        dataBuilder.putShort(field.getName(), (Short) value);
                    });

                    typeHandlers.put(Byte.class, () -> {
                        dataBuilder.putByte(field.getName(), (Byte) value);
                    });

                    typeHandlers.put(NbtComponent.class, () -> {
                        dataBuilder.put(field.getName(), (NbtCompound) value);
                    });

                    typeHandlers.put(NbtElement.class, () -> {
                        dataBuilder.put(field.getName(), (NbtElement) value);
                    });
                    //endregion

                    Class<?> annotationClass = field.getAnnotation(LibySyncedValue.class).value();

                    Runnable handler = typeHandlers.get(annotationClass);
                    if (handler != null) {
                        handler.run(); // Call the appropriate method based on the type
                    } else {
                        throw new IllegalStateException("Unexpected value type: " + annotationClass);
                    }
                }
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace(); // Handle the exception appropriately for debugging
        }

        return dataBuilder;
    }
}
