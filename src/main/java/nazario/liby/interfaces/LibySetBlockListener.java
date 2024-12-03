package nazario.liby.interfaces;

import net.minecraft.command.argument.BlockStateArgument;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public interface LibySetBlockListener {
    default void setBlockStateEventHead(BlockStateArgument blockStateArgument, ServerWorld world, BlockPos pos, int flags, CallbackInfoReturnable<Boolean> cir) {};
    default void setBlockStateEventTail(BlockStateArgument blockStateArgument, ServerWorld world, BlockPos pos, int flags, CallbackInfoReturnable<Boolean> cir) {};
}
