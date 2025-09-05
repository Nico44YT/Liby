package nazario.liby.mixin.world.v1.client.hanging_sign;

import nazario.liby.api.world.v1.block.hanging_sign.LibyHangingSign;
import nazario.liby.api.world.v1.block.hanging_sign.LibyHangingSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.HangingSignBlockEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registries;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(HangingSignBlockEntityRenderer.class)
public abstract class HangingSignBlockEntityRendererMixin {

    @Mutable
    @Shadow @Final private Map<WoodType, HangingSignBlockEntityRenderer.HangingSignModel> MODELS;
    private SignBlockEntity vanity$blockEntity;

    @Redirect(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/block/entity/HangingSignBlockEntityRenderer;MODELS:Ljava/util/Map;", opcode = Opcodes.PUTFIELD))
    public void vanity$init(HangingSignBlockEntityRenderer instance, Map<WoodType, HangingSignBlockEntityRenderer.HangingSignModel> value) {
        MODELS = new HashMap<>(value);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void vanity$init(BlockEntityRendererFactory.Context context, CallbackInfo ci) {
        Registries.BLOCK.stream().filter(block -> block instanceof LibyHangingSignBlock).forEach(sign -> {
            WoodType type = ((LibyHangingSignBlock)sign).getWoodType();
            HangingSignBlockEntityRenderer.HangingSignModel model =  new HangingSignBlockEntityRenderer.HangingSignModel(context.getLayerModelPart(EntityModelLayers.createHangingSign(WoodType.OAK)));
            MODELS.put(type, model);
        });
    }

    @Inject(method = "render(Lnet/minecraft/block/entity/SignBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V", at = @At("HEAD"))
    public void vanity$render(SignBlockEntity signBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j, CallbackInfo ci) {
        this.vanity$blockEntity = signBlockEntity;
    }

    @Inject(method = "getTextureId", at = @At("HEAD"), cancellable = true)
    public void vanity$getTextureId(WoodType signType, CallbackInfoReturnable<SpriteIdentifier> cir) {
        if(this.vanity$blockEntity.getCachedState().getBlock() instanceof LibyHangingSign sign) {
            cir.setReturnValue(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, sign.getTexture()));
        }
    }
}
