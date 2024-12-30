package nazario.liby.api.registry.runtime.models;

import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.util.Identifier;

public class LibyJsonModel implements LibyModel {
    public final Identifier identifier;
    public final String jsonModel;

    public LibyJsonModel(Identifier id, String jsonModel) {
        this.identifier = id;
        this.jsonModel = jsonModel;
    }

    @Override
    public Identifier getId() {
        return this.identifier;
    }

    public String jsonModel() {
        return this.jsonModel;
    }

    @Override
    public JsonUnbakedModel bake() {
        return JsonUnbakedModel.deserialize(jsonModel);
    }
}
