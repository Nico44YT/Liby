package nazario.liby.internal.assetgen.v1.client;

import net.minecraft.client.texture.NativeImage;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.io.InputStream;

public class EarlyTextureLoader {
    public static boolean check(Identifier id) {
        String fullPath = "/assets/" + id.getNamespace() + "/textures/" + id.getPath() + ".png";

        try (InputStream stream = EarlyTextureLoader.class.getResourceAsStream(fullPath)) {
            return stream != null;
        } catch (Exception ignored) {
            return false;
        }
    }

    public static NativeImage loadEarly(Identifier id) throws IOException {
        // path should be like: "assets/modid/textures/item/my_item.png"
        String fullPath = "/assets/" + id.getNamespace() + "/textures/" + id.getPath() + ".png";

        try (InputStream stream = EarlyTextureLoader.class.getResourceAsStream(fullPath)) {
            if (stream == null) {
                //throw new IOException("Texture not found: " + fullPath);
            }
            return NativeImage.read(stream);
        }
    }
}