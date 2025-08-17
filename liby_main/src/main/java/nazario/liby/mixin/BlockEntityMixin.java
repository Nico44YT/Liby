package nazario.liby.mixin;


import nazario.liby.internal.injections.LibyBlockEntityInjects;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntity.class)
public abstract class BlockEntityMixin implements LibyBlockEntityInjects {
    @Shadow @Nullable public abstract World getWorld();

    @Shadow public abstract BlockPos getPos();

    @Unique private Block liby$Block;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void liby$init(BlockEntityType<?> type, BlockPos pos, BlockState state, CallbackInfo ci) {
        this.liby$Block = state.getBlock();
    }

    @Override
    public BlockState liby$getBlockState() {
        if(this.getWorld() == null) return null;
        BlockState state = this.getWorld().getBlockState(this.getPos());
        if(!state.getBlock().equals(liby$Block)) return null;
        return state;
    }
}
