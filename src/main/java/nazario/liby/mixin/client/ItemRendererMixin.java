package nazario.liby.mixin.client;

import nazario.liby.api.registry.rendering.LibyItemSpecialModelRegistry;
import nazario.liby.api.registry.runtime.models.LibyItemModelObject;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Environment(EnvType.CLIENT)
@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @ModifyVariable(method = "renderItem", at = @At("HEAD"), argsOnly = true)
    public BakedModel liby$renderItem(BakedModel value, ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        if (LibyItemSpecialModelRegistry.getList().containsKey(stack.getItem())) {
            LibyItemModelObject modelObject = LibyItemSpecialModelRegistry.getList().get(stack.getItem());
            if (!modelObject.containsMode(renderMode)) {
                return ((ItemRendererAccessor) this).liby$getModels().getModelManager().getModel(modelObject.modelIdentifier);
            }
        }

        return value;
    }
}
