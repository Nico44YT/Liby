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

public class LibyShapelessCraftingRecipe extends LibyRecipe {
    private static final Identifier TYPE = new Identifier("minecraft","crafting_shapeless");

    public LibyShapelessCraftingRecipe(LibyIngredient[] ingredients, Identifier result, int count) {
        JsonObject recipe = this.createBasicJson();

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
    }

    @Override
    protected Identifier getType() {
        return TYPE;
    }

    public static class Builder implements LibyRecipe.Builder {

        protected List<LibyIngredient> ingredientList;
        protected ItemConvertible result;
        protected int count;

        public Builder() {
            this.ingredientList = new ArrayList<>();
        }

        public Builder addIngredient(ItemConvertible ingredient) {
            this.ingredientList.add(LibyIngredient.ofItem(ingredient, null));
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
            return new LibyShapelessCraftingRecipe(this.ingredientList.toArray(LibyIngredient[]::new), this.result.asItem().liby$getId(), this.count);
        }
    }
}
