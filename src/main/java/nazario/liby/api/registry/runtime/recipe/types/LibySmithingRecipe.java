package nazario.liby.api.registry.runtime.recipe.types;

import com.google.gson.JsonObject;
import nazario.liby.api.registry.runtime.recipe.LibyIngredient;
import nazario.liby.api.registry.runtime.recipe.LibyRecipe;
import net.minecraft.util.Identifier;

public class LibySmithingRecipe extends LibyRecipe {
    private static final Identifier TYPE = new Identifier("minecraft", "smithing");

    public LibySmithingRecipe(Identifier id, LibyIngredient base, LibyIngredient addition, Identifier result) {
        JsonObject recipe = new JsonObject();
        recipe.addProperty("type", getType().toString());

        JsonObject baseObject = new JsonObject();
        baseObject.addProperty(base.getType(), base.getIngredientId().toString());
        recipe.add("base", baseObject);

        JsonObject additionObject = new JsonObject();
        additionObject.addProperty(addition.getType(), addition.getIngredientId().toString());
        recipe.add("base", additionObject);

        // Add result
        recipe.addProperty("result", result.toString());

        this.jsonObject = recipe;
        this.id = id;
    }

    @Override
    protected Identifier getType() {
        return TYPE;
    }
}
