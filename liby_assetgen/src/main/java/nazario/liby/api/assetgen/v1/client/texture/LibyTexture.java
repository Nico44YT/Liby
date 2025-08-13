package nazario.liby.api.assetgen.v1.client.texture;

import net.minecraft.resource.InputSupplier;
import net.minecraft.util.Identifier;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class LibyTexture {
    protected Color[][] pixels;

    public LibyTexture(Color[][] pixels) {
        this.pixels = pixels;
    }

    public Color[][] getPixels() {
        return pixels;
    }

    public InputSupplier<InputStream> getInputStream() {
        BufferedImage image = new BufferedImage(this.pixels.length, this.pixels[0].length, BufferedImage.TYPE_4BYTE_ABGR);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color color = this.pixels[x][y];
                if(color == null) color = new Color(0, 0, 0, 0);
                int colorInt = ((color.getAlpha() & 0xFF) << 24) |
                        ((color.getRed()   & 0xFF) << 16) |
                        ((color.getGreen() & 0xFF) << 8)  |
                        (color.getBlue()   & 0xFF);
                image.setRGB(x, y, colorInt);
            }
        }

        return () -> {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            baos.flush();
            return new ByteArrayInputStream(baos.toByteArray());
        };
    }
}
