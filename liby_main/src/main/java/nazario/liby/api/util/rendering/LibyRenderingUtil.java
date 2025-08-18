package nazario.liby.api.util.rendering;

import nazario.liby.api.client.renderer.LibyFace;
import nazario.liby.api.client.renderer.LibyTriangleData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.List;

public class LibyRenderingUtil {

    /***
     * Renders a cube. If the cube only renders black because of the uv cords try calling renderCube with flipV
     * @see #renderCube(MatrixStack, VertexConsumer, int[], int, int, float, float, float, float, boolean)
     * @param matrixStack
     * @param buffer
     * @param rgba
     * @param overlay
     * @param light
     * @param minU
     * @param minV
     * @param maxU
     * @param maxV
     */
    public static void renderCube(MatrixStack matrixStack, VertexConsumer buffer, int[] rgba, int overlay, int light, float minU, float minV, float maxU, float maxV) {
        renderCube(matrixStack, buffer, rgba, overlay, light, minU, minV, maxU, maxV, false);
    }

    public static void renderCube(MatrixStack matrixStack, VertexConsumer buffer, int[] rgba, int overlay, int light, float minU, float minV, float maxU, float maxV, boolean flipV) {
        Matrix4f positionMatrix = matrixStack.peek().getPositionMatrix();
        MatrixStack.Entry normalMatrix = matrixStack.peek();

        minV *= flipV ? -1 : 1;
        maxV *= flipV ? -1 : 1;

        List<Vector3f> vertexes = List.of(
                // FRONT (z = 1)
                new Vector3f(0, 0, 1),
                new Vector3f(1, 0, 1),
                new Vector3f(1, 1, 1),
                new Vector3f(0, 1, 1),
                // BACK (z = 0)
                new Vector3f(1, 0, 0),
                new Vector3f(0, 0, 0),
                new Vector3f(0, 1, 0),
                new Vector3f(1, 1, 0),
                // LEFT (x = 0)
                new Vector3f(0, 0, 0),
                new Vector3f(0, 0, 1),
                new Vector3f(0, 1, 1),
                new Vector3f(0, 1, 0),
                // RIGHT (x = 1)
                new Vector3f(1, 0, 1),
                new Vector3f(1, 0, 0),
                new Vector3f(1, 1, 0),
                new Vector3f(1, 1, 1),
                // TOP (y = 1)
                new Vector3f(0, 1, 1),
                new Vector3f(1, 1, 1),
                new Vector3f(1, 1, 0),
                new Vector3f(0, 1, 0),
                // BOTTOM (y = 0)
                new Vector3f(0, 0, 0),
                new Vector3f(1, 0, 0),
                new Vector3f(1, 0, 1),
                new Vector3f(0, 0, 1)
        );

        for (int i = 0; i < vertexes.size(); i+=4) {
            renderQuad(positionMatrix, normalMatrix, buffer,
                    new Vector3f[]{
                            vertexes.get(i),
                            vertexes.get(i + 1),
                            vertexes.get(i + 2),
                            vertexes.get(i + 3)

                    },
                    minU, minV,
                    maxU, maxV,
                    light, overlay,
                    rgba
            );
        }
    }

    public static void renderQuad(Matrix4f positionMatrix, MatrixStack.Entry normalMatrix, VertexConsumer buffer, Vector3f[] vertexes, float uMin, float vMin, float uMax, float vMax, int light, int overlay, int[] rgba) {
        // Front triangle
        assembleFace(
                new Vector3f[]{vertexes[0], vertexes[1], vertexes[2]},
                new Vector2f[]{
                        new Vector2f(uMin, vMin),
                        new Vector2f(uMax, vMin),
                        new Vector2f(uMax, vMax)
                }
        ).render(buffer, positionMatrix, normalMatrix, light, overlay, rgba, new boolean[]{false, false, false});

        // Back triangle
        assembleFace(
                new Vector3f[]{vertexes[0].mul(1, 1, -1), vertexes[3].mul(1, 1, -1), vertexes[2].mul(1, 1, -1)},
                new Vector2f[]{
                        new Vector2f(uMin, vMin),
                        new Vector2f(uMin, vMax),
                        new Vector2f(uMax, vMax)
                }
        ).render(buffer, positionMatrix, normalMatrix, light, overlay, rgba, new boolean[]{false, false, true});
    }

    public static LibyFace assembleFace(Vector3f[] vertexes, Vector2f[] texCoords) {
        LibyTriangleData[] triangles = new LibyTriangleData[vertexes.length + 1];

        // Compute face normal using cross product
        Vector3f edge1 = vertexes[1].sub(vertexes[0]);
        Vector3f edge2 = vertexes[2].sub(vertexes[0]);
        Vector3f normal = edge1.cross(edge2).normalize();

        for (int i = 0; i < vertexes.length; i++) {
            triangles[i] = new LibyTriangleData(vertexes[i], normal, texCoords[i]);
        }

        triangles[vertexes.length] = triangles[0]; // close loop

        return new LibyFace(triangles);
    }
}
