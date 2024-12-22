package nazario.liby.mixin;

import nazario.liby.api.registry.helper.LibyAxeStrippingRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.item.AxeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin {
    @Inject(method = "getStrippedState", at = @At("HEAD"), cancellable = true)
    public void liby$getStrippedState(BlockState state, CallbackInfoReturnable<Optional<BlockState>> cir) {
        Optional<BlockState> optional = Optional.ofNullable(LibyAxeStrippingRegistry.getMap().get(state.getBlock())).map(block -> (BlockState) block.getStateWithProperties(state));
        if (optional.isPresent()) cir.setReturnValue(optional);
    }
}
