package nazario.liby.api.client.renderer;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public record LibyTriangleData(Vec3d vertex, Vec3d normal, Vec2f texturePos) {
    public void render(VertexConsumer buffer, Matrix4f positionMatrix, Matrix3f normalMatrix, int light, int overlay, int[] rgba) {
        this.render(buffer, positionMatrix, normalMatrix, light, overlay, rgba, new boolean[]{false, false, false});
    }

    public void render(VertexConsumer buffer, Matrix4f positionMatrix, Matrix3f normalMatrix, int light, int overlay, int[] rgba, boolean[] mirror) {
        Vec3d pos = this.vertex();
        Vec2f uv = this.texturePos();
        Vec3d normal = this.normal();

        buffer
                .vertex(positionMatrix, (float) pos.x * (mirror[0]?-1:1), (float) pos.y * (mirror[1]?-1:1), (float) pos.z * (mirror[2]?-1:1))
                .color(rgba[0], rgba[1], rgba[2], rgba[3])
                .texture((float) uv.x, 1.0f - (float) uv.y)
                .overlay(overlay)
                .light(light)
                .normal(normalMatrix, (float) normal.x, (float) normal.y, (float) normal.z)
                .next();

    }
}