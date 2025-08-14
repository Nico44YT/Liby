package nazario.liby.internal.assetgen.v1.client.format;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.util.math.AffineTransformation;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;

public class LibyFreeFormRotation {
    private final AffineTransformation affineTransformation;
    private final Vec3f origin;
    private final Vec3f rotationVector;

    public LibyFreeFormRotation(float x, float y, float z, Vec3f origin) {
        Quaternion quaternion = (new Quaternion((float)x, (float)y, (float)z, true));

        this.rotationVector = new Vec3f(x, y, z);
        this.affineTransformation = new AffineTransformation(
                new Vec3f(), // Translation
                quaternion, // Left-Rotation
                new Vec3f(1, 1, 1), // Scale
                null // Right-Rotation
        );
        this.origin = origin;
    }

    public static LibyFreeFormRotation deserializeRotation(JsonObject object) {
        if(object.has("rotation")) {
            JsonObject rotationObject = object.get("rotation").getAsJsonObject();

            float x = rotationObject.has("x") ? rotationObject.get("x").getAsFloat() : 0;
            float y = rotationObject.has("y") ? rotationObject.get("y").getAsFloat() : 0;
            float z = rotationObject.has("z") ? rotationObject.get("z").getAsFloat() : 0;

            Vec3f origin = rotationObject.has("origin") ? deserializeRotationOrigin(rotationObject.get("origin").getAsJsonArray()) : new Vec3f();

            return new LibyFreeFormRotation(x, y, z, origin);
        }

        return new LibyFreeFormRotation(0, 0, 0, new Vec3f());
    }

    private static Vec3f deserializeRotationOrigin(JsonArray originArray) {
        return new Vec3f(originArray.get(0).getAsFloat(), originArray.get(1).getAsFloat(), originArray.get(2).getAsFloat());
    }

    public AffineTransformation getAffineTransformation() {
        return this.affineTransformation;
    }

    public Vec3f getOrigin() {
        return this.origin;
    }

    public Vec3f getRotationVector() {
        return this.rotationVector;
    }

    @Override
    public String toString() {
        return String.format("[%s, %s]", this.affineTransformation.getRotation1(), this.origin);
    }
}
