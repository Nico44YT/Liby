package nazario.liby.api.registry.runtime.models;

import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.util.Identifier;

public interface LibyModel {
    Identifier getId();
    UnbakedModel bake();
}
