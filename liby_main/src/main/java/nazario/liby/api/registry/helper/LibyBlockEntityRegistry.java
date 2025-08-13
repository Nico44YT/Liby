package nazario.liby.api.registry.helper;

import nazario.liby.internal.registry.LibyImplementableRegistry;
import nazario.liby.internal.registry.LibyImplementedRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.function.BiFunction;

public interface LibyBlockEntityRegistry extends LibyImplementableRegistry {
    static LibyBlockEntityRegistry of(String name) {
        return LibyImplementedRegistry.ofBlockEntityTypes(name);
    }

    <T extends BlockEntityType<?>> T registerBlockEntityType(String name, T blockEntityType);
    <T extends BlockEntityType<?>> T registerBlockEntityType(Identifier id, T blockEntityType);
    <T extends BlockEntity> BlockEntityType<T> registerBlockEntityType(String name, FabricBlockEntityTypeBuilder.Factory<T> factory, Class<? extends BlockEntityProvider> blockClass);
    <T extends BlockEntity> BlockEntityType<T> registerBlockEntityType(Identifier id, FabricBlockEntityTypeBuilder.Factory<T> factory, Class<? extends BlockEntityProvider> blockClass);
    <T extends BlockEntity> BlockEntityType<T> registerBlockEntityType(String name, FabricBlockEntityTypeBuilder.Factory<T> factory, BlockEntityProvider... blocks);
    <T extends BlockEntity> BlockEntityType<T> registerBlockEntityType(Identifier id, FabricBlockEntityTypeBuilder.Factory<T> factory, BlockEntityProvider... blocks);
}
