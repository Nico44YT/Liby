package nazario.liby.networking.payloads;

import nazario.liby.nbt.NbtCompoundReader;
import nazario.liby.networking.LibySyncType;
import nazario.liby.networking.LibySyncedValue;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import java.lang.reflect.Field;
import java.util.UUID;

public record LibySyncPacket(NbtCompound nbtData) implements CustomPayload {
    public static final Id<LibySyncPacket> ID = new Id<>(Identifier.of("liby", "sync"));
    public static final PacketCodec<RegistryByteBuf, LibySyncPacket> CODEC = PacketCodec.tuple(PacketCodecs.NBT_COMPOUND, LibySyncPacket::nbtData, LibySyncPacket::new);

    public static void receive(LibySyncPacket packet, ClientPlayNetworking.Context context) {
        NbtCompoundReader packetReader = NbtCompoundReader.create(packet.nbtData());

        LibySyncType type = (LibySyncType)packetReader.getEnum("type");

        if (type == LibySyncType.BLOCK_SYNC) {
            Block block = context.client().world.getBlockState(packetReader.getBlockPos("pos")).getBlock();
            sync(packetReader, packet, block);
        } else if(type == LibySyncType.BLOCK_ENTITY_SYNC) {
            BlockEntity blockEntity = context.client().world.getBlockEntity(packetReader.getBlockPos("pos"));
            sync(packetReader, packet, blockEntity);
        }else {
            throw new RuntimeException("Liby Sync Packet doesn't appear to have a type or an invalid one!");
        }
    }

    public static void sync(NbtCompoundReader packetReader, LibySyncPacket packet, Object object) {
        NbtCompoundReader data = packetReader.getCompoundAsReader("data");

        try {
            Class<?> clazz = Class.forName(packet.nbtData().getString("class"));

            for (Field field : clazz.getFields()) {
                if (field.isAnnotationPresent(LibySyncedValue.class)) {
                    field.setAccessible(true); // Access private fields

                    // Extract the value based on the field type
                    if (field.getType() == int.class || field.getType() == Integer.class) {
                        int intValue = data.asCompound().getInt(field.getName());
                        field.set(object, intValue);
                    } else if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                        boolean boolValue = data.asCompound().getBoolean(field.getName());
                        field.set(object, boolValue);
                    } else if (field.getType() == double.class || field.getType() == Double.class) {
                        double doubleValue = data.asCompound().getDouble(field.getName());
                        field.set(object, doubleValue);
                    } else if (field.getType() == String.class) {
                        String stringValue = data.asCompound().getString(field.getName());
                        field.set(object, stringValue);
                    } else if (field.getType() == float.class || field.getType() == Float.class) {
                        float floatValue = data.asCompound().getFloat(field.getName());
                        field.set(object, floatValue);
                    } else if (field.getType() == long.class || field.getType() == Long.class) {
                        long longValue = data.asCompound().getLong(field.getName());
                        field.set(object, longValue);
                    } else if (field.getType() == UUID.class) {
                        UUID uuidValue = data.asCompound().getUuid(field.getName());
                        field.set(object, uuidValue);
                    } else if (field.getType() == BlockPos.class) {
                        BlockPos blockPosValue = data.getBlockPos(field.getName());
                        field.set(object, blockPosValue);
                    } else if (field.getType() == Integer[].class) {
                        int[] integersArrayValue = data.asCompound().getIntArray(field.getName());
                        field.set(object, integersArrayValue);
                    } else if (field.getType() == Float[].class) {
                        float[] floatsArrayValue = data.getFloatArray(field.getName());
                        field.set(object, floatsArrayValue);
                    } else if (field.getType() == Vec2f.class) {
                        Vec2f v2fValue = data.getVec2f(field.getName());
                        field.set(object, v2fValue);
                    } else if (field.getType() == Vec3d.class) {
                        Vec3d vec3dValue = data.getVec3d(field.getName());
                        field.set(object, vec3dValue);
                    } else if (field.getType() == Vec3i.class) {
                        Vec3i vec3iValue = data.getVec3i(field.getName());
                        field.set(object, vec3iValue);
                    } else if (field.getType() == Byte[].class) {
                        byte[] byteArray = data.asCompound().getByteArray(field.getName());
                        field.set(object, byteArray);
                    } else if (field.getType() == Long[].class) {
                        long[] longArray = data.asCompound().getLongArray(field.getName());
                        field.set(object, longArray);
                    } else if (field.getType() == Short.class) {
                        short shortValue = data.asCompound().getShort(field.getName());
                        field.set(object, shortValue);
                    } else if (field.getType() == Byte.class) {
                        byte byteValue = data.asCompound().getByte(field.getName());
                        field.set(object, byteValue);
                    } else if (field.getType() == NbtCompound.class) {
                        NbtCompound nbtCompound = data.getCompound(field.getName());
                        field.set(object, nbtCompound);
                    } else if(field.getType() == NbtElement.class) {
                        NbtElement nbtElement = (NbtElement) data.getCompound(field.getName());
                        field.set(object, nbtElement);
                    } else {
                        throw new IllegalStateException("Unexpected value type: " + field.getType());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}