package nazario.liby.internal.assetgen.v1.client.model.item_model_predicate;

import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface LibyItemModelPredicate {
    boolean apply(ModelTransformationMode mode, ItemStack stack, boolean leftHanded);
}
