package nazario.liby.api.recipe;

import com.google.gson.JsonObject;
import nazario.liby.internal.resource.LibyJsonResource;
import net.minecraft.util.Identifier;

public abstract class LibyRecipe extends LibyJsonResource {
    private String group = null;
    private String category = "misc";

    protected Identifier getType() {
        return Identifier.of("minecraft","none");
    }

    public JsonObject createBasicJson() {
        JsonObject object = new JsonObject();

        object.addProperty("type", this.getType().toString());

        return object;
    }

    protected interface Builder extends LibyRecipeBuilder {
        @Override
        LibyRecipe build();
    }
}
