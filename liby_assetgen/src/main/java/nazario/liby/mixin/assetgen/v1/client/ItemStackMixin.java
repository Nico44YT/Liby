package nazario.liby.mixin.assetgen.v1.client;

import nazario.liby.internal.assetgen.v1.client.LibyAssetGenFlags;
import net.minecraft.client.item.TooltipData;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Inject(method = "getTooltipData", at = @At("HEAD"))
    public void liby$getTooltipData(CallbackInfoReturnable<Optional<TooltipData>> cir) {
        LibyAssetGenFlags.tooltipItemStack = (ItemStack)(Object)this;
    }

}
