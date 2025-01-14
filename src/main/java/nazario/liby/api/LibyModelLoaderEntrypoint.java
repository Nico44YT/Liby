package nazario.liby.api;

import nazario.liby.api.registry.rendering.LibyItemSpecialModelRegistry;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemConvertible;

public interface LibyModelLoaderEntrypoint {
    void onLibyModelLoaderInitialize();

    default void liby$addModel(ModelIdentifier modelIdentifier) {
        LibyItemSpecialModelRegistry._addModel(modelIdentifier);
    }

    default void liby$registerSpecialItemModel(ItemConvertible itemConvertible, ModelIdentifier modelIdentifier, ModelTransformation.Mode... modes) {
        LibyItemSpecialModelRegistry._addModel(modelIdentifier);
        LibyItemSpecialModelRegistry.register(itemConvertible, modelIdentifier, modes);
    }
}
