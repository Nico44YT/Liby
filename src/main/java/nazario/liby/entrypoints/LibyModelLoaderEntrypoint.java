package nazario.liby.entrypoints;

import nazario.liby.registry.rendering.LibyItemModelRendererRegistry;
import net.minecraft.client.util.ModelIdentifier;

public interface LibyModelLoaderEntrypoint {
    void onLibyModelLoaderInitialize();
    default void addModel(ModelIdentifier modelIdentifier) {
        LibyItemModelRendererRegistry._addModel(modelIdentifier);
    };
}
