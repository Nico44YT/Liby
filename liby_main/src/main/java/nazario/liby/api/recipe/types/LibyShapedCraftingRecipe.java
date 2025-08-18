package nazario.liby.api.recipe.types;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import nazario.liby.api.recipe.LibyIngredient;
import nazario.liby.api.recipe.LibyRecipe;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LibyShapedCraftingRecipe extends LibyRecipe {
    private static final Identifier TYPE = Identifier.of("minecraft","crafting_shaped");

    public LibyShapedCraftingRecipe(String[] pattern, LibyIngredient[] ingredients, Identifier result, int count) {
        JsonObject recipe = this.createBasicJson();

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
    }

    @Override
    protected Identifier getType() {
        return TYPE;
    }

    public static class Builder implements LibyRecipe.Builder {

        private String[] pattern;
        private List<LibyIngredient> ingredientList;
        private ItemConvertible result;
        private int count;

        public Builder() {
            this.ingredientList = new ArrayList<>();
        }

        public Builder setPattern(String[] pattern) {
            this.pattern = pattern;
            return this;
        }

        public Builder addIngredient(ItemConvertible ingredient, char key) {
            this.ingredientList.add(LibyIngredient.ofItem(ingredient, key));
            return this;
        }

        public Builder addIngredient(LibyIngredient... ingredient) {
            this.ingredientList.addAll(Arrays.stream(ingredient).toList());
            return this;
        }

        public Builder setResult(ItemConvertible result) {
            return this.setResult(result, 1);
        }

        public Builder setResult(ItemConvertible result, int count) {
            this.result = result;
            this.count = count;
            return this;
        }


        @Override
        public LibyRecipe build() {
            return new LibyShapedCraftingRecipe(this.pattern, this.ingredientList.toArray(LibyIngredient[]::new), this.result.asItem().liby$getId(), this.count);
        }
    }
}
