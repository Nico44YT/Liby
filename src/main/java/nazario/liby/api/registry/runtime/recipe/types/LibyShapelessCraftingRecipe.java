package nazario.liby.api.registry.runtime.recipe.types;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nazario.liby.api.registry.runtime.recipe.LibyIngredient;
import nazario.liby.api.registry.runtime.recipe.LibyRecipe;
import net.minecraft.util.Identifier;

public class LibyShapelessCraftingRecipe extends LibyRecipe {
    private static final Identifier TYPE = Identifier.of("minecraft","crafting_shapeless");

    public LibyShapelessCraftingRecipe(Identifier id, LibyIngredient[] ingredients, Identifier result, int count) {
        JsonObject recipe = new JsonObject();
        recipe.addProperty("type", getType().toString());

        JsonArray ingredientsObject = new JsonArray();
        for(LibyIngredient ingredient : ingredients) {
            JsonObject itemObject = new JsonObject();
            itemObject.addProperty(ingredient.getType(), ingredient.getIngredientId().toString());

            ingredientsObject.add(itemObject);
        }

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
