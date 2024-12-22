package nazario.liby.api;

import nazario.liby.api.registry.rendering.LibyItemSpecialModelRegistry;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemConvertible;

public interface LibyModelLoaderEntrypoint {
    void onLibyModelLoaderInitialize();

    default void liby$addModel(ModelIdentifier modelIdentifier) {
        LibyItemSpecialModelRegistry._addModel(modelIdentifier);
    }

    ;

    default void liby$registerItemConvertible(ItemConvertible itemConvertible, ModelIdentifier modelIdentifier, ModelTransformationMode... modes) {
        LibyItemSpecialModelRegistry._addModel(modelIdentifier);
        LibyItemSpecialModelRegistry.register(itemConvertible, modelIdentifier, modes);
    }
}
