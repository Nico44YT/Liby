package nazario.liby.api.registry.runtime.recipe.types;

import com.google.gson.JsonObject;
import nazario.liby.api.registry.runtime.recipe.LibyIngredient;
import nazario.liby.api.registry.runtime.recipe.LibyRecipe;
import net.minecraft.util.Identifier;

public class LibyStoneCuttingRecipe extends LibyRecipe {
    private static final Identifier TYPE = Identifier.of("minecraft", "stonecutting");

    public LibyStoneCuttingRecipe(Identifier id, LibyIngredient ingredient, Identifier result, int count) {
        JsonObject recipe = new JsonObject();
        recipe.addProperty("type", getType().toString());

        JsonObject ingredientObject = new JsonObject();
        ingredientObject.addProperty(ingredient.getType(), ingredient.getIngredientId().toString());

        recipe.add("ingredient", ingredientObject);

        JsonObject resultObject = new JsonObject();
        resultObject.addProperty("id", result.toString());
        resultObject.addProperty("count", count);

        // Add result
        recipe.add("result", resultObject);

        this.jsonObject = recipe;
        this.id = id;
    }

    @Override
    protected Identifier getType() {
        return TYPE;
    }
}
