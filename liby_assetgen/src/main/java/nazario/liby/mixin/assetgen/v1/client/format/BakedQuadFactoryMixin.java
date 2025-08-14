package nazario.liby.mixin.assetgen.v1.client.format;

import nazario.liby.LibyAssetGenMain;
import  nazario.liby.internal.assetgen.v1.client.format.LibyFreeFormRotation;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.BakedQuadFactory;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.json.ModelElementFace;
import net.minecraft.client.render.model.json.ModelRotation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BakedQuadFactory.class)
public abstract class BakedQuadFactoryMixin {

    @Shadow protected abstract void transformVertex(Vector3f vertex, Vector3f origin, Matrix4f transformationMatrix, Vector3f scale);

    @Unique private Identifier liby$id;

    @Inject(method = "bake", at = @At("HEAD"))
    private void liby$getModelId(Vector3f from, Vector3f _to, ModelElementFace face, Sprite texture, Direction side, ModelBakeSettings settings, ModelRotation rotation, boolean shade, Identifier modelId, CallbackInfoReturnable<BakedQuad> cir) {
        this.liby$id = modelId;
    }

    @ModifyVariable(
            method = "bake",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/Sprite;getAnimationFrameDelta()F"), // variable assignment point
            index = 7
    )
    private ModelRotation changeRotation(ModelRotation original) {
        if(original == null && LibyAssetGenMain.libyModelRotationUsers.contains(liby$id)) return new ModelRotation(new Vector3f(), Direction.Axis.X, 0, false);
        return original;
    }

    @Inject(method = "rotateVertex", at = @At("HEAD"), cancellable = true)
    public void liby$rotateVertex(Vector3f vertex, ModelRotation rotation, CallbackInfo ci) {
        if (rotation != null && rotation.libyAssets$isLibyFreeFormSet()) {

            LibyFreeFormRotation freeFormRotation = rotation.libyAssets$getFreeFormRotation();

            Vector3f rotationVector = freeFormRotation.getRotationVector();
            Vector3f origin = freeFormRotation.getOrigin();

            float xRad = (float)Math.toRadians(rotationVector.x());
            float yRad = (float)Math.toRadians(rotationVector.y());
            float zRad = (float)Math.toRadians(rotationVector.z());

            Quaternionf quaternion = new Quaternionf().rotateYXZ(yRad, xRad, zRad);

            Matrix4f matrix = new Matrix4f().rotation(quaternion);
            Vector3f scale = new Vector3f(1.0f, 1.0f, 1.0f); // No scaling
            this.transformVertex(vertex, origin, matrix, scale);

            ci.cancel();
        }
    }
}

