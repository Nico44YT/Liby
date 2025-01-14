package nazario.liby.api.registry.runtime.models;

import com.google.gson.JsonObject;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.util.Identifier;

public class LibyJsonModel implements LibyModel {
    public final Identifier identifier;
    public final JsonObject jsonModel;

    public LibyJsonModel(Identifier id, JsonObject jsonModel) {
        this.identifier = id;
        this.jsonModel = jsonModel;
    }

    @Override
    public Identifier getId() {
        return this.identifier;
    }

    public JsonObject jsonModel() {
        return this.jsonModel;
    }

    @Override
    public UnbakedModel bake() {
        return JsonUnbakedModel.deserialize(jsonModel.toString());
    }

    @Override
    public String toString() {
        return "LibyJsonModel{\n" +
                identifier.toString() + "\n" +
                jsonModel.toString() + "\n" +
                "}";
    }
}
