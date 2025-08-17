package nazario.liby.api.client.renderer;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public record LibyFace(LibyTriangleData... data) {
    public void render(VertexConsumer buffer, MatrixStack matrixStack, int light, int overlay, int[] rgba) {
        this.render(buffer, matrixStack.peek().getPositionMatrix(), matrixStack.peek().getNormalMatrix(), light, overlay, rgba);
    }

    public void render(VertexConsumer buffer, Matrix4f positionMatrix, Matrix3f normalMatrix, int light, int overlay, int[] rgba) {
        this.render(buffer, positionMatrix, normalMatrix, light, overlay, rgba, new boolean[]{false, false, false});
    }

    public void render(VertexConsumer buffer, Matrix4f positionMatrix, Matrix3f normalMatrix, int light, int overlay, int[] rgba, boolean[] mirror) {
        if(mirror[0] || mirror[1] || mirror[2]) {
            for (int i = data.length - 1; i >= 0; i--) {
                data[i].render(buffer, positionMatrix, normalMatrix, light, overlay, rgba, mirror);
            }
        } else {
            for (int i = 0; i < data.length; i++) {
                data[i].render(buffer, positionMatrix, normalMatrix, light, overlay, rgba, mirror);
            }
        }
    }
}