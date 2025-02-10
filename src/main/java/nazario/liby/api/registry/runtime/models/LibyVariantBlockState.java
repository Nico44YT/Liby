package nazario.liby.api.registry.runtime.models;

import com.google.gson.JsonObject;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;

public class LibyVariantBlockState extends LibyBlockState {

    public HashMap<ModelIdentifier, JsonObject> modelIdentifierMap;

    public LibyVariantBlockState(Identifier id, String resourcePackName) {
        this(id, resourcePackName, new HashMap<>());
    }

    public LibyVariantBlockState(Identifier id, String resourcePackName, HashMap<ModelIdentifier, JsonObject> jsonIdentifiers) {
        this.id = id;
        this.resourcePackName = resourcePackName;
        this.modelIdentifierMap = jsonIdentifiers;
    }

    public LibyVariantBlockState addState(String variant, String model) {
        return this.addState(variant, model, 0, 0, false, 1);
    }

    public LibyVariantBlockState addState(String variant, String model, int x_rotation, int y_rotation, boolean uv_lock, int weight) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("model", model);
        jsonObject.addProperty("x", x_rotation);
        jsonObject.addProperty("y", y_rotation);
        jsonObject.addProperty("uvlock", uv_lock);
        jsonObject.addProperty("weight", weight);

        this.modelIdentifierMap.put(new ModelIdentifier(id, variant), jsonObject);

        return this;
    }

    @Override
    public List<Resource> createResource() {
        JsonObject mainJson = new JsonObject();
        JsonObject variants = new JsonObject();

        for(ModelIdentifier modelId : modelIdentifierMap.keySet()) {
            JsonObject jsonObject = modelIdentifierMap.get(modelId);

            variants.add(modelId.getVariant(), jsonObject);
        }

        mainJson.add("variants", variants);

        return List.of(new Resource(
                resourcePackName,
                () -> new ByteArrayInputStream(mainJson.toString().getBytes())
        ));
    }

    public JsonObject getModel(ModelIdentifier modelIdentifier) {
        return modelIdentifierMap.get(modelIdentifier);
    }
}
