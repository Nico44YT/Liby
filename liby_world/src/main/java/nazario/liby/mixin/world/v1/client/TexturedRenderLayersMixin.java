package nazario.liby.mixin.world.v1.client;

import nazario.liby.api.world.v1.block.hanging_sign.LibyHangingSign;
import nazario.liby.api.world.v1.block.sign.LibySign;
import net.minecraft.block.WoodType;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(TexturedRenderLayers.class)
public abstract class TexturedRenderLayersMixin {
    @Shadow @Final public static Identifier SIGNS_ATLAS_TEXTURE;

    @Inject(method = "addDefaultTextures", at = @At("TAIL"))
    private static void vanity$addSignTextures(Consumer<SpriteIdentifier> adder, CallbackInfo ci) {
        Registries.BLOCK.forEach(block -> {
            if(block instanceof LibySign sign) {
                adder.accept(new SpriteIdentifier(SIGNS_ATLAS_TEXTURE, sign.getTexture()));
            }

            if(block instanceof LibyHangingSign sign) {
                adder.accept(new SpriteIdentifier(SIGNS_ATLAS_TEXTURE, sign.getTexture()));
            }
        });
    }
}
