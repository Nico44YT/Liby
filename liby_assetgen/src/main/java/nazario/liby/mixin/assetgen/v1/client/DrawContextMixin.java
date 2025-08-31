package nazario.liby.mixin.assetgen.v1.client;

import nazario.liby.api.assetgen.v1.client.renderer.LibyTooltipRenderer;
import nazario.liby.internal.assetgen.v1.client.LibyAssetGenFlags;
import nazario.liby.internal.assetgen.v1.client.LibyInternalAssetRegistry;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.item.ItemStack;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DrawContext.class)
public abstract class DrawContextMixin {
    @Redirect(method = "drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;IILnet/minecraft/client/gui/tooltip/TooltipPositioner;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/tooltip/TooltipComponent;drawText(Lnet/minecraft/client/font/TextRenderer;IILorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;)V"))
    public void liby$drawText(TooltipComponent tooltip, TextRenderer textRenderer, int x, int y, Matrix4f matrix, VertexConsumerProvider.Immediate vertexConsumers) {
        ItemStack stack = LibyAssetGenFlags.tooltipItemStack;

        if(stack != null && !stack.isEmpty()) {
            LibyTooltipRenderer renderer = LibyInternalAssetRegistry.tooltipRenderers.getOrDefault(stack.getItem(), null);
            if(renderer != null) {
                renderer.renderText(tooltip, textRenderer, x, y, matrix, vertexConsumers);
                return;
            }
        }

        LibyAssetGenFlags.tooltipItemStack = null;
        tooltip.drawText(textRenderer, x, y, matrix, vertexConsumers);
    }
}
