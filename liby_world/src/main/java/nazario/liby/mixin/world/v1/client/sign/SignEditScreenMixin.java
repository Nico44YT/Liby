package nazario.liby.mixin.world.v1.client.sign;

import nazario.liby.api.world.v1.block.sign.LibySign;
import net.minecraft.block.WoodType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import oshi.util.tuples.Pair;

import java.util.HashMap;
import java.util.Map;

@Mixin(SignEditScreen.class)
public abstract class SignEditScreenMixin {

    @Unique
    private static final Map<WoodType, Pair<SignBlockEntityRenderer.SignModel, LibySign>> liby$typeToModel = new HashMap<>();

    @Inject(method = "<init>", at = @At("TAIL"))
    public void liby$init(SignBlockEntity sign, boolean filtered, boolean bl, CallbackInfo ci) {
        Registries.BLOCK.forEach(block -> {
            if(block instanceof LibySign libySign) {
                SignBlockEntityRenderer.SignModel model = SignBlockEntityRenderer.createSignModel(MinecraftClient.getInstance().getEntityModelLoader(), WoodType.OAK);
                liby$typeToModel.put(libySign.getWoodType(), new Pair<>(model, libySign));
            }
        });
    }

    @Redirect(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/entity/SignBlockEntityRenderer;createSignModel(Lnet/minecraft/client/render/entity/model/EntityModelLoader;Lnet/minecraft/block/WoodType;)Lnet/minecraft/client/render/block/entity/SignBlockEntityRenderer$SignModel;"))
    public SignBlockEntityRenderer.SignModel liby$createSignModel(EntityModelLoader entityModelLoader, WoodType type) {
        if(liby$typeToModel.containsKey(type)) {
            return SignBlockEntityRenderer.createSignModel(entityModelLoader, WoodType.OAK);
        }

        return SignBlockEntityRenderer.createSignModel(entityModelLoader, type);
    }

    @Redirect(method = "renderSignBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TexturedRenderLayers;getSignTextureId(Lnet/minecraft/block/WoodType;)Lnet/minecraft/client/util/SpriteIdentifier;"))
    public SpriteIdentifier getSignTexture(WoodType signType) {
        LibySign sign = liby$typeToModel.getOrDefault(signType, null).getB();
        if(sign != null) {
            return new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, sign.getTexture());
        }
        return TexturedRenderLayers.getSignTextureId(signType);
    }
}
