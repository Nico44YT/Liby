package nazario.liby.mixin.world.v1;

import nazario.liby.api.world.v1.block.hanging_sign.LibyHangingSign;
import nazario.liby.api.world.v1.block.sign.LibySign;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityType.class)
public abstract class BlockEntityTypeMixin {
    @Inject(method = "supports", at = @At("HEAD"), cancellable = true)
    public void liby$supports(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if(BlockEntityType.SIGN.equals(this)) {
            if(state.getBlock() instanceof LibySign) cir.setReturnValue(true);
        }

        if(BlockEntityType.HANGING_SIGN.equals(this)) {
            if(state.getBlock() instanceof LibyHangingSign) cir.setReturnValue(true);
        }
    }
}
