package nazario.liby.mixin;

import nazario.liby.api.block.LibySetBlockListener;
import net.minecraft.command.argument.BlockStateArgument;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockStateArgument.class)
public abstract class BlockStateArgumentMixin {
    @Inject(method = "setBlockState", at = @At("HEAD"), cancellable = true)
    public void liby$setBlockStateHead(ServerWorld world, BlockPos pos, int flags, CallbackInfoReturnable<Boolean> cir) {
        BlockStateArgument blockStateArgument = (BlockStateArgument) (Object) this;

        if (blockStateArgument.getBlockState().getBlock() instanceof LibySetBlockListener listener) {
            listener.setBlockStateEventHead(blockStateArgument, world, pos, flags, cir);
        }
    }

    @Inject(method = "setBlockState", at = @At("TAIL"), cancellable = true)
    public void liby$setBlockStateTail(ServerWorld world, BlockPos pos, int flags, CallbackInfoReturnable<Boolean> cir) {
        BlockStateArgument blockStateArgument = (BlockStateArgument) (Object) this;

        if (blockStateArgument.getBlockState().getBlock() instanceof LibySetBlockListener listener) {
            listener.setBlockStateEventHead(blockStateArgument, world, pos, flags, cir);
        }
    }
}