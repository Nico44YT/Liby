package nazario.liby.internal.assetgen.v1.client.format;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.util.math.AffineTransformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class LibyFreeFormRotation {
    private final AffineTransformation affineTransformation;
    private final Vector3f origin;
    private final Vector3f rotationVector;

    public LibyFreeFormRotation(float x, float y, float z, Vector3f origin) {
        Quaternionf quaternionf = (new Quaternionf()).rotateYXZ((float)Math.toRadians(-y), (float)Math.toRadians(-x), (float)Math.toRadians(-z));

        this.rotationVector = new Vector3f(x, y, z);
        this.affineTransformation = new AffineTransformation(
                new Vector3f(), // Translation
                quaternionf, // Left-Rotation
                new Vector3f(1, 1, 1), // Scale
                new Quaternionf() // Right-Rotation
        );
        this.origin = origin;
    }

    public static LibyFreeFormRotation deserializeRotation(JsonObject object) {
        if(object.has("rotation")) {
            JsonObject rotationObject = object.get("rotation").getAsJsonObject();

            float x = rotationObject.has("x") ? rotationObject.get("x").getAsFloat() : 0;
            float y = rotationObject.has("y") ? rotationObject.get("y").getAsFloat() : 0;
            float z = rotationObject.has("z") ? rotationObject.get("z").getAsFloat() : 0;

            Vector3f origin = rotationObject.has("origin") ? deserializeRotationOrigin(rotationObject.get("origin").getAsJsonArray()) : new Vector3f();

            return new LibyFreeFormRotation(x, y, z, origin);
        }

        return new LibyFreeFormRotation(0, 0, 0, new Vector3f());
    }

    private static Vector3f deserializeRotationOrigin(JsonArray originArray) {
        return new Vector3f(originArray.get(0).getAsFloat(), originArray.get(1).getAsFloat(), originArray.get(2).getAsFloat());
    }

    public AffineTransformation getAffineTransformation() {
        return this.affineTransformation;
    }

    public Vector3f getOrigin() {
        return this.origin;
    }

    public Vector3f getRotationVector() {
        return this.rotationVector;
    }

    @Override
    public String toString() {
        return String.format("[%s, %s]", this.affineTransformation, this.origin);
    }
}
