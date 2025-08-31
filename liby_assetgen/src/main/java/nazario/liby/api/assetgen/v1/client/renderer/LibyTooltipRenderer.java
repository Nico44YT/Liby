package nazario.liby.api.assetgen.v1.client.renderer;

import nazario.liby.api.assetgen.v1.client.LibyDrawContext;
import net.minecraft.client.gui.tooltip.TooltipBackgroundRenderer;
import net.minecraft.item.ItemStack;

public interface LibyTooltipRenderer {
    void render(ItemStack stack, LibyDrawContext drawContext, int x, int y, int width, int height, int z);

    default void renderHorizontalLine(ItemStack stack, LibyDrawContext context, int x, int y, int width, int height, int z, int startColor, int endColor) {
        TooltipBackgroundRenderer.renderBorder(context, x, y, width, height, z, startColor, endColor);
    }

    default void renderVerticalLine(ItemStack stack, LibyDrawContext context, int x, int y, int height, int z, int color) {
        TooltipBackgroundRenderer.renderVerticalLine(context, x, y, height, z, color);
    }

    default void renderVerticalLine(ItemStack stack, LibyDrawContext context, int x, int y, int height, int z, int startColor, int endColor) {
        TooltipBackgroundRenderer.renderVerticalLine(context, x, y, height, z, startColor, endColor);
    }

    default void renderHorizontalLine(ItemStack stack, LibyDrawContext context, int x, int y, int width, int z, int color) {
        TooltipBackgroundRenderer.renderHorizontalLine(context, x, y, width, z, color);
    }

    default void renderHorizontalLine(ItemStack stack, LibyDrawContext context, int x, int y, int width, int z, int startColor, int endColor) {
        context.fillGradient(x, y, x + width, y + 1, z, startColor, endColor);
    }

    default void renderRectangle(ItemStack stack, LibyDrawContext context, int x, int y, int width, int height, int z, int color) {
        TooltipBackgroundRenderer.renderRectangle(context, x, y, width, height, z, color);
    }

    default void renderBorder(ItemStack stack, LibyDrawContext context, int x, int y, int width, int height, int z, int startColor, int endColor) {
        TooltipBackgroundRenderer.renderBorder(context, x, y, width, height, z, startColor, endColor);
    }

    @FunctionalInterface
    public interface Factory {
        LibyTooltipRenderer apply();
    }
}
