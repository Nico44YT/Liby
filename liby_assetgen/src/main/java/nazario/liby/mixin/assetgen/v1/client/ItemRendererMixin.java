package nazario.liby.mixin.assetgen.v1.client;

import nazario.liby.internal.assetgen.v1.client.LibyInternalAssetRegistry;
import nazario.liby.internal.assetgen.v1.client.model.item_model_predicate.LibyItemModelRule;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Optional;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @SuppressWarnings("all")
    @ModifyVariable(method = "renderItem", at = @At("HEAD"), argsOnly = true)
    public BakedModel liby$renderItem(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        Optional<LibyItemModelRule> optional = LibyInternalAssetRegistry.itemModelRules.stream().filter(rule -> rule.item().equals(stack.getItem())).findFirst();

        if(optional.isPresent()) {
            LibyItemModelRule rule = optional.get();
            if(rule.predicate().apply(renderMode, stack, leftHanded)) return ((ItemRendererAccessor)this).getItemModels().getModelManager().getModel(rule.modelIdentifier());
        }

        return value;
    }
}
