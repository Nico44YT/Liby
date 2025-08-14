package nazario.liby.internal.animation.v2.networking;

import nazario.liby.LibyAnimationsV2;
import nazario.liby.api.animation.v2.LibyAnimatable;
import nazario.liby.api.util.nbt.LibyNbtCompound;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public record SyncAnimationS2C(NbtCompound animationData, NbtCompound animatableResolver) implements ClientPlayNetworking.PlayChannelHandler {

    public static final Identifier ID = LibyAnimationsV2.id("sync_animation_s2c");

    public static SyncAnimationS2C fromPacketByteBuf(PacketByteBuf packetByteBuf) {
        return new SyncAnimationS2C(packetByteBuf.readNbt(), packetByteBuf.readNbt());
    }

    public static SyncAnimationS2C create(LibyAnimatable libyAnimatable) {
        LibyNbtCompound animationData = new LibyNbtCompound();
        LibyNbtCompound animatableResolver = new LibyNbtCompound();

        libyAnimatable.getLibyAnimation().get().writeToNbt(animationData);

        if(libyAnimatable instanceof Entity entity) {
            animatableResolver.putEnum("type", LibyAnimatable.AnimatableType.ENTITY);
            animatableResolver.putInt("entity_id", entity.getId());
        }

        if(libyAnimatable instanceof BlockEntity blockEntity) {
            animatableResolver.putEnum("type", LibyAnimatable.AnimatableType.BLOCK_ENTITY);
            animatableResolver.putString("world", blockEntity.getWorld().getRegistryKey().toString());
            animatableResolver.putString("block_entity_type", blockEntity.getType().toString());
            animatableResolver.putBlockPos("block_pos", blockEntity.getPos());
        }

        return new SyncAnimationS2C(animationData, animatableResolver);
    }


    public PacketByteBuf write(PacketByteBuf packetByteBuf) {
        packetByteBuf.writeNbt(animationData);
        packetByteBuf.writeNbt(animatableResolver);
        return packetByteBuf;
    }

    public static LibyAnimatable getAnimatable(World world, NbtCompound animatableResolver) {
        LibyNbtCompound libyNbt = new LibyNbtCompound(animatableResolver);

        LibyAnimatable.AnimatableType type = libyNbt.getEnum("type", LibyAnimatable.AnimatableType.class);

        return switch(type) {
            case ENTITY -> {
                int entityId = libyNbt.getInt("entity_id");

                yield world.getEntityById(entityId);
            }
            case BLOCK_ENTITY -> {
                BlockPos blockPos = libyNbt.getBlockPos("block_pos");
                String worldKey = libyNbt.getString("world");
                String blockEntityType = libyNbt.getString("block_entity_type");

                if(world.getRegistryKey().toString().equals(worldKey)) {
                    BlockEntity blockEntity = world.getBlockEntity(blockPos);

                    if(!blockEntity.getType().toString().equals(blockEntityType)) yield null;

                    yield blockEntity;
                }

                yield null;
            }
        };
    }

    @Override
    public void receive(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        SyncAnimationS2C packet = SyncAnimationS2C.fromPacketByteBuf(packetByteBuf);
        LibyAnimatable animatable = getAnimatable(MinecraftClient.getInstance().world, packet.animatableResolver);

        if(animatable instanceof Entity entity) {
            NbtCompound nbtCompound = new NbtCompound();
            entity.writeNbt(nbtCompound);
            nbtCompound.put("liby_animation", packet.animationData);
            entity.readNbt(nbtCompound);
        }
    }
}
