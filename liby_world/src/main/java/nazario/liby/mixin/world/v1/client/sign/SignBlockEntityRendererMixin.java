package nazario.liby.mixin.world.v1.client.sign;

import nazario.liby.api.world.v1.block.sign.LibySign;
import nazario.liby.api.world.v1.block.sign.LibySignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registries;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(SignBlockEntityRenderer.class)
public abstract class SignBlockEntityRendererMixin {
    @Mutable
    @Shadow
    @Final
    private Map<WoodType, SignBlockEntityRenderer.SignModel> typeToModel;


    @Unique
    private SignBlockEntity liby$blockEntity;

    @Redirect(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/block/entity/SignBlockEntityRenderer;typeToModel:Ljava/util/Map;", opcode = Opcodes.PUTFIELD))
    public void liby$init(SignBlockEntityRenderer instance, Map<WoodType, SignBlockEntityRenderer.SignModel> value) {
        typeToModel = new HashMap<>(value);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void liby$init(BlockEntityRendererFactory.Context ctx, CallbackInfo ci) {
        Registries.BLOCK.stream().filter(block -> block instanceof LibySignBlock).forEach(sign -> {
            SignBlockEntityRenderer.SignModel model = SignBlockEntityRenderer.createSignModel(ctx.getLayerRenderDispatcher(), WoodType.OAK);
            typeToModel.put(((LibySignBlock)sign).getWoodType(), model);
        });
    }

    @Inject(method = "render(Lnet/minecraft/block/entity/SignBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V", at = @At("HEAD"))
    public void liby$render(SignBlockEntity signBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j, CallbackInfo ci) {
        this.liby$blockEntity = signBlockEntity;
    }

    @Inject(method = "getTextureId", at = @At("HEAD"), cancellable = true)
    public void liby$getTextureId(WoodType signType, CallbackInfoReturnable<SpriteIdentifier> cir) {
        if(this.liby$blockEntity.getCachedState().getBlock() instanceof LibySign sign) {
            cir.setReturnValue(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, sign.getTexture()));
        }
    }
}
