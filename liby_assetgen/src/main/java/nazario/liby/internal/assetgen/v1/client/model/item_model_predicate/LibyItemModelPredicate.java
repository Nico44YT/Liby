package nazario.liby.internal.assetgen.v1.client.model.item_model_predicate;

import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface LibyItemModelPredicate {
    boolean apply(ModelTransformation.Mode mode, ItemStack stack, boolean leftHanded);
}