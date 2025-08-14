package nazario.liby.mixin.assetgen.v1.client.format;

import  nazario.liby.internal.assetgen.v1.client.format.LibyFreeFormRotation;
import net.minecraft.client.render.model.BakedQuadFactory;
import net.minecraft.client.render.model.json.ModelRotation;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BakedQuadFactory.class)
public abstract class BakedQuadFactoryMixin {

    @Shadow protected abstract void transformVertex(Vec3f vertex, Vec3f origin, Matrix4f transformationMatrix, Vec3f scale);

    @ModifyVariable(
            method = "bake",
            at = @At("HEAD"),
            index = 7
    )
    private ModelRotation changeRotation(ModelRotation original) {
        if(original == null) return new ModelRotation(new Vec3f(), Direction.Axis.X, 0, false);
        return original;
    }

    @Inject(method = "rotateVertex", at = @At("HEAD"), cancellable = true)
    public void liby$rotateVertex(Vec3f vertex, ModelRotation rotation, CallbackInfo ci) {
        if (rotation != null && rotation.libyAssets$isLibyFreeFormSet()) {

            LibyFreeFormRotation freeFormRotation = rotation.libyAssets$getFreeFormRotation();

            Vec3f rotationVector = freeFormRotation.getRotationVector();
            Vec3f origin = freeFormRotation.getOrigin();

            float xRad = (float)Math.toRadians(rotationVector.getX());
            float yRad = (float)Math.toRadians(rotationVector.getY());
            float zRad = (float)Math.toRadians(rotationVector.getZ());

            Quaternion quaternion = new Quaternion(xRad, yRad, zRad,0);

            Matrix4f matrix = new Matrix4f();
            matrix.multiply(quaternion);
            Vec3f scale = new Vec3f(1.0f, 1.0f, 1.0f); // No scaling
            this.transformVertex(vertex, origin, matrix, scale);

            ci.cancel();
        }
    }
}

