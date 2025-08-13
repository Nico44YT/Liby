package nazario.liby.internal;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class LibyRuntimeErrors {
    public static IllegalArgumentException invalidRegistryNamespace(String namespace) {
        return new IllegalArgumentException("Registry namespace has to match [a-z0-9_.-] and cannot be null, namespace is currently: \"" + namespace + "\"");
    }

    public static IllegalArgumentException invalidBlockStateRotation(Identifier id, String model, int xRotation, int yRotation) {
        return new IllegalArgumentException(String.format("BlockState %s of model %s must confine to 90 degree increments in rotation for xRotation and yRotation: X-Rotation = %d, Y-Rotation = %d", id.toString(), model, xRotation, yRotation));
    }
}
