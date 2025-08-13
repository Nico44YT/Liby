package nazario.liby.api.util.rendering;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;

public record LibyRenderingContext(float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light) {

}
