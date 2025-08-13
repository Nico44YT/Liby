package nazario.liby.mixin.assetgen.v1.manager.client;

import nazario.liby.api.assetgen.v1.client.texture.TextureList;
import net.minecraft.client.texture.SpriteContents;
import net.minecraft.client.texture.SpriteLoader;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Mixin(SpriteLoader.class)
public abstract class SpriteLoaderMixin {
    /*@Inject(method = "load(Lnet/minecraft/util/Identifier;Lnet/minecraft/resource/Resource;)Lnet/minecraft/client/texture/SpriteContents;", at = @At("HEAD"))
    private static void liby$load(Identifier id, Resource resource, CallbackInfoReturnable<SpriteContents> cir) throws IOException {
        BufferedImage image = ImageIO.read(resource.getInputStream());

        Color[][] colors = new Color[image.getWidth()][image.getHeight()];

        for(int x = 0;x<image.getWidth();x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y);
                colors[x][y] = new Color(rgb);
            }
        }

        TextureList.textures.put(id, colors);
    }*/
}
