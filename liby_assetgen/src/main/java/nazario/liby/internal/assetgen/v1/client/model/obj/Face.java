package nazario.liby.internal.assetgen.v1.client.model.obj;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public record Face(TriangleData[] data) {
    public void render(VertexConsumer buffer, Matrix4f modelMatrix, Matrix3f normalMatrix, int light, int overlay, int[] rgba, boolean[] mirror) {
        if(mirror[0] || mirror[1] || mirror[2]) {
            for (int i = data.length - 1; i >= 0; i--) {
                data[i].render(buffer, modelMatrix, normalMatrix, light, overlay, rgba, mirror);
            }
        } else {
            for (int i = 0; i < data.length; i++) {
                data[i].render(buffer, modelMatrix, normalMatrix, light, overlay, rgba, mirror);
            }
        }
    }
}