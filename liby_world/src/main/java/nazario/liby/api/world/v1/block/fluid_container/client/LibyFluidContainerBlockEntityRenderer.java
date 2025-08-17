package nazario.liby.api.world.v1.block.fluid_container.client;

import nazario.liby.api.util.rendering.LibyRenderingUtil;
import nazario.liby.api.world.v1.block.fluid_container.LibyFluidContainerBlockEntity;
import nazario.liby.api.world.v1.fluid_container.LibyFluidContainer;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LibyFluidContainerBlockEntityRenderer<T extends LibyFluidContainerBlockEntity> implements BlockEntityRenderer<T> {

    public LibyFluidContainerBlockEntityRenderer(BlockEntityRendererFactory.Context context) {

    }

    @Override
    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        LibyFluidContainer fluidContainer = entity.getFluidContainer();

        if(fluidContainer.isResourceBlank()) return; // * We abort the render method if there is no fluid inside the container.

        matrices.push();
        this.renderFluid(entity, fluidContainer, tickDelta, matrices, vertexConsumers.getBuffer(RenderLayers.getFluidLayer(fluidContainer.getFluidState())), light, overlay);
        matrices.pop();
    }

    public void renderFluid(T entity, LibyFluidContainer fluidContainer, float tickDelta, MatrixStack matrices, VertexConsumer buffer, int light, int overlay) {
        int[] color = getRGBAColorFromVariant(fluidContainer.getVariant(), entity.getWorld(), entity.getPos());

        Sprite sprite = FluidVariantRendering.getSprites(fluidContainer.getVariant())[0];
        float minU = sprite.getFrameU(getMinU(fluidContainer) * 16f);
        float minV = sprite.getFrameV(getMinV(fluidContainer) * 16f);
        float maxU = sprite.getFrameU(getMaxU(fluidContainer) * 16f);
        float maxV = sprite.getFrameV(getMaxV(fluidContainer) * 16f);

        LibyRenderingUtil.renderCube(matrices, buffer, color, overlay, light, minU, minV, maxU, maxV, true);
    }

    /**
     *
     * @param fluidVariant
     * @param world
     * @param pos
     * @return an array of 4 integers, formated as Red, Green, Blue, Alpha.
     */
    public int[] getRGBAColorFromVariant(FluidVariant fluidVariant, World world, BlockPos pos) {
        int color = FluidVariantRendering.getColor(fluidVariant, world, pos);
        int a = ((color >> 24) & 0xFF);
        int r = ((color >> 16) & 0xFF);
        int g = ((color >> 8) & 0xFF);
        int b = (color & 0xFF);

        return new int[]{r, g, b, a};
    }

    /**
     * Each pixel of a 16x16 texture is 1/16, so if you were to render an 8x8 area of a texture, you would have 0 as the u and v minimum and 8/16 for the u and v maximum
     * Returns the minimum U coordinate of the texture.
     * @return
     */
    public float getMinU(LibyFluidContainer fluidContainer) {
        return 0;
    }

    /**
     * Each pixel of a 16x16 texture is 1/16, so if you were to render an 8x8 area of a texture, you would have 0 as the u and v minimum and 8/16 for the u and v maximum
     * Returns the minimum V coordinate of the texture.
     * @return
     */
    public float getMinV(LibyFluidContainer fluidContainer) {
        return 0;
    }

    /**
     * Each pixel of a 16x16 texture is 1/16, so if you were to render an 8x8 area of a texture, you would have 0 as the u and v minimum and 8/16 for the u and v maximum
     * Returns the maximum U coordinate of the texture.
     * @return
     */
    public float getMaxU(LibyFluidContainer fluidContainer) {
        return 1;
    }

    /**
     * Each pixel of a 16x16 texture is 1/16, so if you were to render an 8x8 area of a texture, you would have 0 as the u and v minimum and 8/16 for the u and v maximum
     * Returns the maximum V coordinate of the texture.
     * @return
     */
    public float getMaxV(LibyFluidContainer fluidContainer) {
        return 1 * fluidContainer.getFillPrecentage();
    }
}
