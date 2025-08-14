package nazario.liby.internal.assetgen.v1.client.model.item_model_predicate;

import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemConvertible;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public record LibyItemModelRule(ItemConvertible item, ModelIdentifier modelIdentifier, LibyItemModelPredicate predicate) {
    @Override
    public ItemConvertible item() {
        return item;
    }

    @Override
    public ModelIdentifier modelIdentifier() {
        return modelIdentifier;
    }

    public LibyItemModelPredicate predicate() {
        return this.predicate;
    }
}