package nazario.liby.api.assetgen.v1.client.texture;

import nazario.liby.internal.assetgen.v1.client.EarlyTextureLoader;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TextureList {
    public static Map<Identifier, Color[][]> textures = new ConcurrentHashMap<>();

    public static void putBlockTexture(Identifier texture, Color[][] color) {
        textures.put(Identifier.of(texture.getNamespace(), "block/" + texture.getPath()), color);
    }

    public static void putItemTexture(Identifier texture, Color[][] color) {
        textures.put(Identifier.of(texture.getNamespace(), "item/" + texture.getPath()), color);
    }

    public static Color[][] getBlockTexture(Identifier id) {
        return requestTexture(Identifier.of(id.getNamespace(), "block/" + id.getPath()));
    }

    public static Color[][] getItemTexture(Identifier id) {
        return requestTexture(Identifier.of(id.getNamespace(), "item/" + id.getPath()));
    }

    public static boolean checkTexture(Identifier id) {
        if(textures.containsKey(id)) return true;

        return EarlyTextureLoader.check(id);
    }

    public static Color[][] requestTexture(Identifier id) {
        try{
            if(!textures.containsKey(id)) {

                NativeImage nativeImage = EarlyTextureLoader.loadEarly(id);

                Color[][] colors = new Color[nativeImage.getWidth()][nativeImage.getHeight()];

                for (int x = 0; x < nativeImage.getWidth(); x++) {
                    for (int y = 0; y < nativeImage.getHeight(); y++) {
                        int opacity = Byte.toUnsignedInt(nativeImage.getOpacity(x, y));
                        int red = Byte.toUnsignedInt(nativeImage.getRed(x, y));
                        int green = Byte.toUnsignedInt(nativeImage.getGreen(x, y));
                        int blue = Byte.toUnsignedInt(nativeImage.getBlue(x, y));

                        colors[x][y] = new Color(red, green, blue, opacity);
                    }
                }

                textures.put(id, colors);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        Color[][] existingColors = textures.get(id);
        Color[][] colors = new Color[existingColors.length][existingColors[0].length];

        for(int x = 0;x<colors.length;x++) {
            for(int y = 0;y<colors[x].length;y++) {
                colors[x][y] = new Color(existingColors[x][y].getRGB(), true);
            }
        }

        return colors;
    }
}
