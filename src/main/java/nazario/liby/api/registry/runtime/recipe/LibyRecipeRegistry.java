package nazario.liby.api.registry.runtime.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class LibyRecipeRegistry {
    private static HashMap<Identifier, JsonElement> recipeMap = new HashMap<>();

    public static void addRecipe(Identifier recipeId, JsonElement recipeJson) {
        recipeMap.put(recipeId, recipeJson);
    }

    public static Map<Identifier, JsonElement> getMap() {
        return new HashMap<>(recipeMap);
    }

    /**
     * Creates a crafting recipe JSON object.
     *
     * @param type      The type of the recipe (e.g., "minecraft:crafting_shaped").
     * @param pattern   An array of strings representing the crafting pattern.
     * @param key       A map where each character in the pattern is mapped to an item ID.
     * @param result    The resulting item ID of the recipe.
     * @param count     The count of the resulting item.
     * @return          A JsonObject representing the crafting recipe.
     */
    public static JsonObject createRecipe(String type, String[] pattern, Map<Character, String> key, String result, int count) {
        JsonObject recipe = new JsonObject();
        recipe.addProperty("type", type);

        // Add pattern
        JsonArray patternArray = new JsonArray();
        for (String row : pattern) {
            patternArray.add(row);
        }
        recipe.add("pattern", patternArray);

        // Add key
        JsonObject keyObject = new JsonObject();
        for (Map.Entry<Character, String> entry : key.entrySet()) {
            JsonObject itemObject = new JsonObject();
            itemObject.addProperty("item", entry.getValue());
            keyObject.add(String.valueOf(entry.getKey()), itemObject);
        }
        recipe.add("key", keyObject);

        // Add result
        JsonObject resultObject = new JsonObject();
        resultObject.addProperty("item", result);
        resultObject.addProperty("count", count);
        recipe.add("result", resultObject);

        return recipe;
    }
}