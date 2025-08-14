package nazario.liby.mixin.client;

import nazario.liby.api.item.LibyCustomItemRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Shadow @Final private TextureManager textureManager;

    @Shadow @Final private ItemColors colors;

    @Shadow @Final private BuiltinModelItemRenderer builtinModelItemRenderer;

    @Unique
    private BakedModelManager bakery;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void liby$init(TextureManager manager, BakedModelManager bakery, ItemColors colors, BuiltinModelItemRenderer builtinModelItemRenderer, CallbackInfo ci) {
        this.bakery = bakery;
    }

    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;push()V", ordinal = 0), cancellable = true)
    public void liby$renderItem(ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci) {
        if(stack != null && !stack.isEmpty()) {
            if(stack.getItem() instanceof LibyCustomItemRenderer itemRenderer) {
                itemRenderer.getRenderer(MinecraftClient.getInstance(), textureManager, bakery, colors, builtinModelItemRenderer)
                        .renderItem(stack, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, model);
                ci.cancel();
            }
        }
    }
}
