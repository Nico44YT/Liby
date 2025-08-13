package nazario.liby_networking.mixin;

import nazario.liby.api.util.nbt.LibyNbtCompound;
import nazario.liby_networking.api.LibySyncedValue;
import nazario.liby_networking.internal.BlockEntityInjects;
import nazario.liby_networking.internal.packet.LibyBlockEntitySyncPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.logging.log4j.util.TriConsumer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Mixin(BlockEntity.class)
public abstract class BlockEntityMixin implements BlockEntityInjects {

    @Unique
    private static Map<Class<?>, TriConsumer<Field, LibyNbtCompound, BlockEntity>> typeHandlers;

    @Override
    public void liby$sync() {
        if(typeHandlers == null) {
            typeHandlers = new HashMap<>();

            typeHandlers.put(Integer.class, (field, dataCompound, blockEntity) -> dataCompound.putInt(field.getName(), (Integer) getValue(field, blockEntity)));
            typeHandlers.put(Float.class, (field, dataCompound, blockEntity) -> dataCompound.putFloat(field.getName(), (Float) getValue(field, blockEntity)));
        }

        LibyNbtCompound dataCompound = new LibyNbtCompound();

        BlockEntity blockEntity = (BlockEntity)(Object)this;

        for(Field declaredField : blockEntity.getClass().getDeclaredFields()) {
           if(declaredField.isAnnotationPresent(LibySyncedValue.class)) {
               try{
                   declaredField.setAccessible(true);

                   typeHandlers.get(declaredField.getClass()).accept(declaredField, dataCompound, blockEntity);
               }catch (Exception ignore) {

               }
           }
        }

        assert blockEntity.getWorld() != null;
        if(blockEntity.getWorld().isClient) {
            ClientPlayNetworking.send(new LibyBlockEntitySyncPacket(blockEntity.getPos(), dataCompound));
        } else {
            blockEntity.getWorld().getPlayers().forEach(player -> {
                ServerPlayNetworking.send((ServerPlayerEntity)player, new LibyBlockEntitySyncPacket(blockEntity.getPos(), dataCompound));
            });
        }
    }

    private static Object getValue(Field field, Object instance) {
        try{
            return field.get(instance);
        }catch (Exception e) {
            return null;
        }
    }
}
