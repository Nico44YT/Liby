package nazario.liby.api.registry.runtime.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class LibyMultipartBlockState extends LibyBlockState {

    protected List<JsonObject> variants;

    public LibyMultipartBlockState(Identifier id, String resourcePackName) {
        this.id = id;
        this.resourcePackName = resourcePackName;
        this.variants = new ArrayList<>();
    }

    public LibyMultipartBlockState addState(String model, Pair<String, Object>... when) {
        return this.addState(model, 0, 0, false, 1, when);
    }

    public LibyMultipartBlockState addState(String model, int x_rotation, int y_rotation, boolean uv_lock, int weight, Pair<String, Object>... when) {
        JsonObject mainObject = new JsonObject();

        JsonObject applyObject = new JsonObject();
        applyObject.addProperty("model", model);
        applyObject.addProperty("x", x_rotation);
        applyObject.addProperty("y", y_rotation);
        applyObject.addProperty("uvlock", uv_lock);
        applyObject.addProperty("weight", weight);

        mainObject.add("apply", applyObject);

        if(when.length > 0) {
            JsonObject whenObject = new JsonObject();

            for(Pair<String, Object> pair : when) {
                if(pair.getRight() instanceof Boolean bool) whenObject.addProperty(pair.getLeft(), bool ? "true" : "false");
                if(pair.getRight() instanceof String string) whenObject.addProperty(pair.getLeft(), string);
                if(pair.getRight() instanceof Integer integer) whenObject.addProperty(pair.getLeft(), integer);
            }

            mainObject.add("when", whenObject);
        }

        variants.add(mainObject);

        return this;
    }

    @Override
    public List<Resource> createResource() {
        JsonObject mainJson = new JsonObject();

        JsonArray multipartArray = new JsonArray();

        for(JsonObject variant : variants) {
            multipartArray.add(variant);
        }

        mainJson.add("multipart", multipartArray);

        return List.of(new Resource(
                resourcePackName,
                () -> new ByteArrayInputStream(mainJson.toString().getBytes())
        ));
    }


}
