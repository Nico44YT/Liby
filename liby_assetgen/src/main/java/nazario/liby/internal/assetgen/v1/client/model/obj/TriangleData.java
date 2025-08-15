package nazario.liby.internal.assetgen.v1.client.model.obj;

import net.minecraft.client.render.VertexConsumer;
import org.jetbrains.annotations.ApiStatus;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2d;
import org.joml.Vector3d;

@ApiStatus.Internal
public record TriangleData(Vector3d vertex, Vector3d normal, Vector2d texturePos) {
    public void render(VertexConsumer buffer, Matrix4f modelMatrix, Matrix3f normalMatrix, int light, int overlay, int[] rgba, boolean[] mirror) {

        Vector3d pos = this.vertex();
        Vector2d uv = this.texturePos();
        Vector3d normal = this.normal();

        buffer
                .vertex(modelMatrix, (float) pos.x * (mirror[0]?-1:1), (float) pos.y * (mirror[1]?-1:1), (float) pos.z * (mirror[2]?-1:1))
                .color(rgba[0], rgba[1], rgba[2], rgba[3])
                .texture((float) uv.x, 1.0f - (float) uv.y)
                .overlay(overlay)
                .light(light)
                .normal(normalMatrix, (float) normal.x, (float) normal.y, (float) normal.z)
                .next();

    }
}