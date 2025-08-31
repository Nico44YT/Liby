package nazario.liby.mixin.assetgen.v1.client;

import nazario.liby.api.assetgen.v1.client.LibyDrawContext;
import nazario.liby.api.assetgen.v1.client.renderer.LibyTooltipRenderer;
import nazario.liby.internal.assetgen.v1.client.LibyAssetGenFlags;
import nazario.liby.internal.assetgen.v1.client.LibyInternalAssetRegistry;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipBackgroundRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TooltipBackgroundRenderer.class)
public abstract class TooltipBackgroundRendererMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private static void liby$render(DrawContext context, int x, int y, int width, int height, int z, CallbackInfo ci) {
        ItemStack stack = LibyAssetGenFlags.tooltipItemStack;

        if(stack != null && !stack.isEmpty()) {
            LibyTooltipRenderer renderer = LibyInternalAssetRegistry.tooltipRenderers.getOrDefault(stack.getItem(), null);
            if(renderer != null) {
                renderer.render(stack, new LibyDrawContext(context), x, y, width, height, z);
                ci.cancel();
            }
        }
    }
}
