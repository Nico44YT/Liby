package nazario.liby.api.assetgen.v1.client.model.obj;

import nazario.liby.internal.assetgen.v1.client.LibyInternalAssetRegistry;
import nazario.liby.internal.assetgen.v1.client.model.LibyModel;
import nazario.liby.internal.assetgen.v1.client.model.obj.Face;
import nazario.liby.internal.assetgen.v1.client.model.obj.LibyObjDeserializer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;

import java.util.function.Supplier;

public class LibyObjModel implements LibyModel<Face[]> {

    private final Identifier identifier;
    private final Face[] faces;

    public LibyObjModel(Identifier identifier, Resource resourceFile) {
        this(identifier, LibyObjDeserializer.objToFaceList(resourceFile).toArray(Face[]::new));
    }

    public LibyObjModel(Identifier identifier, Face[] faces) {
        this.identifier = identifier;
        this.faces = faces;
    }

    @Override
    public Identifier getId() {
        return this.identifier;
    }

    @Override
    public Face[] bake() {
        return this.faces;
    }

    public void render(Identifier textureIdentifier, VertexConsumerProvider vertexConsumers, MatrixStack matrixStack, int light, int overlay) {
        this.render(vertexConsumers.getBuffer(RenderLayer.getEntityCutout(textureIdentifier)), matrixStack, light, overlay);
    }

    public void render(VertexConsumer vertexConsumer, MatrixStack matrices, int light, int overlay) {
        Matrix4f modelMatrix = matrices.peek().getPositionMatrix();
        Matrix3f normalMatrix = matrices.peek().getNormalMatrix();

        this.render(vertexConsumer, modelMatrix, normalMatrix, light, overlay, new int[]{255, 255, 255, 255}, new boolean[]{false, false, false});
    }

    public void render(VertexConsumer vertexConsumer, Matrix4f modelMatrix, Matrix3f normalMatrix, int light, int overlay, int[] rgba, boolean[] mirror) {
        for (Face face : this.faces) {
            face.render(vertexConsumer, modelMatrix, normalMatrix, light, overlay, rgba, mirror);
        }
    }

    public static Supplier<LibyObjModel> get(Identifier id) {
        return LibyInternalAssetRegistry.getObjModel(id);
    }
}