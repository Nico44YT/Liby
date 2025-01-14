package nazario.liby.api.registry.runtime.recipe.types;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nazario.liby.api.registry.runtime.recipe.LibyIngredient;
import nazario.liby.api.registry.runtime.recipe.LibyRecipe;
import net.minecraft.util.Identifier;

public class LibyShapedCraftingRecipe extends LibyRecipe {
    private static final Identifier TYPE = new Identifier("minecraft","crafting_shaped");

    public LibyShapedCraftingRecipe(Identifier id, String[] pattern, LibyIngredient[] ingredients, Identifier result, int count) {
        JsonObject recipe = new JsonObject();
        recipe.addProperty("type", getType().toString());

        // Add pattern
        JsonArray patternArray = new JsonArray();
        for (String row : pattern) {
            patternArray.add(row);
        }
        recipe.add("pattern", patternArray);

        // Add key
        JsonObject keyObject = new JsonObject();
        for (LibyIngredient ingredient : ingredients) {
            JsonObject itemObject = new JsonObject();
            itemObject.addProperty(ingredient.getType(), ingredient.getIngredientId().toString());
            keyObject.add(String.valueOf(ingredient.getRecipeCharacter()), itemObject);
        }
        recipe.add("key", keyObject);

        // Add result
        JsonObject resultObject = new JsonObject();
        resultObject.addProperty("item", result.toString());
        resultObject.addProperty("count", count);
        recipe.add("result", resultObject);

        this.jsonObject = recipe;
        this.id = id;
    }

    @Override
    protected Identifier getType() {
        return TYPE;
    }
}
