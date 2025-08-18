package nazario.liby.api.client.renderer;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public record LibyTriangleData(Vector3f vertex, Vector3f normal, Vector2f texturePos) {
    public void render(VertexConsumer buffer, Matrix4f positionMatrix, MatrixStack.Entry normalMatrix, int light, int overlay, int[] rgba) {
        this.render(buffer, positionMatrix, normalMatrix, light, overlay, rgba, new boolean[]{false, false, false});
    }

    public void render(VertexConsumer buffer, Matrix4f positionMatrix, MatrixStack.Entry normalMatrix, int light, int overlay, int[] rgba, boolean[] mirror) {
        Vector3f pos = this.vertex();
        Vector2f uv = this.texturePos();
        Vector3f normal = this.normal();

        buffer
                .vertex(positionMatrix,pos.x * (mirror[0]?-1:1),pos.y * (mirror[1]?-1:1),pos.z * (mirror[2]?-1:1))
                .color(rgba[0], rgba[1], rgba[2], rgba[3])
                .texture((float) uv.x, 1.0f - (float) uv.y)
                .overlay(overlay)
                .light(light)
                .normal(normalMatrix, (float) normal.x, (float) normal.y, (float) normal.z);

    }
}