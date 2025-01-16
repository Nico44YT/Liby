package nazario.liby.api.registry.runtime.models;

import com.google.gson.JsonObject;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

public class LibyJsonModel implements LibyModel {
    public final ModelIdentifier identifier;
    public final JsonObject jsonModel;
    public final String modelType;

    public LibyJsonModel(ModelIdentifier id, JsonObject jsonModel, String modelType) {
        this.identifier = id;
        this.jsonModel = jsonModel;
        this.modelType = modelType;
    }

    public LibyJsonModel(Identifier id, String variant, JsonObject jsonModel, String modelType) {
        this(new ModelIdentifier(id, variant), jsonModel, modelType);
    }

    @Override
    public Identifier getBasicId() {
        return this.identifier.id().withPrefixedPath(modelType + "/");
    }

    @Override
    public ModelIdentifier getModelId() {
        return this.identifier;
    }

    public JsonObject jsonModel() {
        return this.jsonModel;
    }

    @Override
    public JsonUnbakedModel bake() {
        JsonUnbakedModel unbakedModel = JsonUnbakedModel.deserialize(jsonModel.toString());
        unbakedModel.id = identifier.id().toString();
        return unbakedModel;
    }

    @Override
    public String toString() {
        return "LibyJsonModel{\n" +
                identifier.toString() + "\n" +
                jsonModel.toString() + "\n" +
                "}";
    }
}
