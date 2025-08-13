package nazario.liby.api.recipe.types;

import com.google.gson.JsonObject;
import nazario.liby.api.recipe.LibyIngredient;
import nazario.liby.api.recipe.LibyRecipe;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;

public class LibyStoneCuttingRecipe extends LibyRecipe {
    private static final Identifier TYPE = new Identifier("minecraft", "stonecutting");

    public LibyStoneCuttingRecipe(LibyIngredient ingredient, Identifier result, int count) {
        JsonObject recipe = new JsonObject();
        recipe.addProperty("type", getType().toString());

        JsonObject ingredientObject = new JsonObject();
        ingredientObject.addProperty(ingredient.getType(), ingredient.getIngredientId().toString());

        recipe.add("ingredient", ingredientObject);

        // Add result
        recipe.addProperty("result", result.toString());
        recipe.addProperty("count", count);

        this.jsonObject = recipe;
    }

    @Override
    protected Identifier getType() {
        return TYPE;
    }

    public static class Builder implements LibyRecipe.Builder {
        private LibyIngredient ingredient;
        private ItemConvertible result;
        private int count;

        public Builder() {

        }

        public Builder setIngredient(ItemConvertible ingredient) {
            return this.setIngredient(LibyIngredient.ofItem(ingredient, null));
        }

        public Builder setIngredient(LibyIngredient ingredient) {
            this.ingredient = ingredient;
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
            return new LibyStoneCuttingRecipe(this.ingredient, this.result.asItem().liby$getId(), this.count);
        }
    }
}
