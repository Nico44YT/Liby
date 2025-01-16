package nazario.liby.api.registry.runtime.models;

import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

public interface LibyModel {
    ModelIdentifier getModelId();
    Identifier getBasicId();
    UnbakedModel bake();
}
