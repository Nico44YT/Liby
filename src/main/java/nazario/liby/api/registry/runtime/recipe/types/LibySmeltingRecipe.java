package nazario.liby.api.registry.runtime.recipe.types;

import com.google.gson.JsonObject;
import nazario.liby.api.registry.runtime.recipe.LibyIngredient;
import nazario.liby.api.registry.runtime.recipe.LibyRecipe;
import net.minecraft.util.Identifier;

public class LibySmeltingRecipe extends LibyRecipe {
    private static final Identifier TYPE = new Identifier("minecraft", "smelting");

    public LibySmeltingRecipe(Identifier id, LibyIngredient ingredient, Identifier result, double experience, int cookingTime) {
        JsonObject recipe = new JsonObject();
        recipe.addProperty("type", getType().toString());

        JsonObject ingredientObject = new JsonObject();
        ingredientObject.addProperty(ingredient.getType(), ingredient.getIngredientId().toString());

        recipe.add("ingredient", ingredientObject);

        // Add result
        recipe.addProperty("result", result.toString());

        recipe.addProperty("experience", experience);
        recipe.addProperty("cookingtime", cookingTime);

        this.jsonObject = recipe;
        this.id = id;
    }

    @Override
    protected Identifier getType() {
        return TYPE;
    }
}
