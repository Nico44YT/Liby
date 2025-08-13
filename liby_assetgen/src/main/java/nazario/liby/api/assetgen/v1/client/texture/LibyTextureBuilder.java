package nazario.liby.api.assetgen.v1.client.texture;

import nazario.liby.api.util.LibyArrayUtil;
import net.minecraft.util.Identifier;

import java.awt.*;

public class LibyTextureBuilder {
    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);

    int width;
    int height;

    Color[][] pixelArray;
    Color[][] clipboard;

    public LibyTextureBuilder(int width, int height) {
        this.pixelArray = new Color[width][height];
        this.width = width;
        this.height = height;
        this.clipboard = new Color[0][0];
    }

    public LibyTextureBuilder load(Identifier textureIdentifier) {
        this.pixelArray = TextureList.requestTexture(textureIdentifier);
        return this;
    }

    public LibyTextureBuilder load(Identifier textureIdentifier, LibyTextureExceptPredicate predicate) {
        Color[][] colors = TextureList.requestTexture(textureIdentifier);
        for (int x = 0; x < colors.length; x++) {
            for (int y = 0; y < colors[x].length; y++) {
                if (predicate.check(x, y, this.pixelArray[x][y], colors[x][y])) {
                    setPixel(x, y, colors[x][y]);
                }
            }
        }
        return this;
    }

    public LibyTextureBuilder paste(int x0, int y0) {
        return this.paste(x0, y0, (x, y, ec, rc) -> true, false);
    }

    public LibyTextureBuilder paste(int x0, int y0, LibyTextureExceptPredicate predicate) {
        return this.paste(x0, y0, predicate, false);
    }

    public LibyTextureBuilder paste(int x0, int y0, LibyTextureExceptPredicate predicate, boolean blend) {
        int width = this.clipboard.length;
        int height = this.clipboard[0].length;

        for (int cx = 0; cx < width; cx++) {
            for (int cy = 0; cy < height; cy++) {
                int dx = x0 + cx;
                int dy = y0 + cy;

                if (dx >= 0 && dx < this.pixelArray.length &&
                        dy >= 0 && dy < this.pixelArray[0].length) {

                    Color clipboardPixel = this.clipboard[cx][cy];
                    Color existingPixel = getPixel(dx, dy);
                    if (predicate.check(dx, dy, clipboardPixel, existingPixel)) {
                        if (blend) {
                            setPixel(dx, dy, blendColors(getPixel(dx, dy), clipboardPixel));
                        } else {
                            setPixel(dx, dy, clipboardPixel);
                        }
                    }
                }
            }
        }
        return this;
    }

    public LibyTextureBuilder copy(int x0, int y0, int x1, int y1) {
        int width = x1 - x0;
        int height = y1 - y0;
        Color[][] clipboard = new Color[width][height];

        for (int cx = 0; cx < width; cx++) {
            for (int cy = 0; cy < height; cy++) {
                clipboard[cx][cy] = getPixel(x0 + cx, y0 + cy);
            }
        }
        this.clipboard = clipboard;
        return this;
    }

    public LibyTextureBuilder cut(int x0, int y0, int x1, int y1) {
        int width = x1 - x0;
        int height = y1 - y0;
        Color[][] clipboard = new Color[width][height];

        for (int cx = 0; cx < width; cx++) {
            for (int cy = 0; cy < height; cy++) {
                clipboard[cx][cy] = this.getPixel(x0 + cx, y0 + cy);
                this.setPixel(x0 + cx, y0 + cy, TRANSPARENT);
            }
        }
        this.clipboard = clipboard;
        return this;
    }

    public LibyTextureBuilder fill(Color[][] colors) {
        return this.fill(colors, (x, y, ec, rc) -> true);
    }

    public LibyTextureBuilder fill(Color[][] colors, LibyTextureExceptPredicate predicate) {
        for (int x = 0; x < colors.length; x++) {
            for (int y = 0; y < colors[x].length; y++) {
                this.setPixelPredicate(x, y, colors[x][y], predicate);
            }
        }
        return this;
    }

    public LibyTextureBuilder fill(int x0, int y0, int x1, int y1, Color color) {
        return this.fill(x0, y0, x1, y1, color, (x, y, ec, rc) -> true);
    }

    public LibyTextureBuilder fill(int x0, int y0, int x1, int y1, Color color, LibyTextureExceptPredicate predicate) {
        for (int x = x0; x < x1; x++) {
            for (int y = y0; y < y1; y++) {
                this.setPixelPredicate(x, y, color, predicate);
            }
        }
        return this;
    }

    public LibyTextureBuilder setPixelPredicate(int x, int y, Color color, LibyTextureExceptPredicate predicate) {
        if (predicate.check(x, y, this.pixelArray[x][y], color)) {
            this.setPixel(x, y, color);
        }
        return this;
    }

    public LibyTextureBuilder setPixel(int x, int y, Color color) {
        this.pixelArray[x][y] = color;
        return this;
    }

    public Color getPixel(int x, int y) {
        return new Color(this.pixelArray[x][y].getRGB(), true);
    }

    public LibyTextureBuilder brighter() {
        for(int x = 0;x<this.pixelArray.length;x++) {
            for(int y = 0;y<this.pixelArray[x].length;y++) {
                this.pixelArray[x][y] = this.pixelArray[x][y].brighter();
            }
        }
        return this;
    }

    public LibyTextureBuilder darker() {
        for(int x = 0;x<this.pixelArray.length;x++) {
            for(int y = 0;y<this.pixelArray[x].length;y++) {
                this.pixelArray[x][y] = this.pixelArray[x][y].darker();
            }
        }
        return this;
    }

    public LibyTexture build() {
        Color[][] colors = new Color[pixelArray.length][pixelArray[0].length];
        for(int x = 0;x<colors.length;x++) {
            for(int y = 0;y<colors[x].length;y++) {
                colors[x][y] = new Color(this.pixelArray[x][y].getRGB(), true);
            }
        }
        return new LibyTexture(colors);
    }

    // Alpha blending helper
    public static Color blendColors(Color base, Color overlay) {
        if (overlay.getAlpha() == 0) return base;
        float alpha = overlay.getAlpha() / 255f;
        int r = (int) (overlay.getRed() * alpha + base.getRed() * (1 - alpha));
        int g = (int) (overlay.getGreen() * alpha + base.getGreen() * (1 - alpha));
        int b = (int) (overlay.getBlue() * alpha + base.getBlue() * (1 - alpha));
        int a = Math.min(255, overlay.getAlpha() + base.getAlpha());
        return new Color(r, g, b, a);
    }
}
