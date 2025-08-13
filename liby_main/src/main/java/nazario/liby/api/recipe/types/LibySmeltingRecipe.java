package nazario.liby.api.recipe.types;

import com.google.gson.JsonObject;
import nazario.liby.api.recipe.LibyIngredient;
import nazario.liby.api.recipe.LibyRecipe;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;

public class LibySmeltingRecipe extends LibyRecipe {
    protected static final Identifier SMELTING_TYPE = Identifier.of("minecraft", "smelting");
    protected static final Identifier BLASTING_TYPE = Identifier.of("minecraft","blasting");
    protected static final Identifier SMOKING_TYPE = Identifier.of("minecraft","smoking");
    protected static final Identifier CAMPFIRE_COOKING_TYPE = Identifier.of("minecraft","campfire_cooking");

    private final Identifier TYPE;

    public LibySmeltingRecipe(Identifier typeIdentifier, LibyIngredient ingredient, Identifier result, double experience, int cookingTime) {
        this.TYPE = typeIdentifier;
        JsonObject recipe = this.createBasicJson();

        JsonObject ingredientObject = new JsonObject();
        ingredientObject.addProperty(ingredient.getType(), ingredient.getIngredientId().toString());

        recipe.add("ingredient", ingredientObject);

        // Add result
        recipe.addProperty("result", result.toString());

        recipe.addProperty("experience", experience);
        recipe.addProperty("cookingtime", cookingTime);

        this.jsonObject = recipe;
    }

    @Override
    protected Identifier getType() {
        return TYPE;
    }

    public static class SmeltingBuilder extends Builder {
        public SmeltingBuilder() {
            super(SMELTING_TYPE);
        }

        public static SmeltingBuilder create() {
            return new SmeltingBuilder();
        }
    }

    public static class BlastingBuilder extends Builder {
        public BlastingBuilder() {
            super(BLASTING_TYPE);
        }
    }

    public static class SmokingBuilder extends Builder {
        public SmokingBuilder() {
            super(SMOKING_TYPE);
        }

        public static SmokingBuilder create() {
            return new SmokingBuilder();
        }
    }

    public static class CampfireCookingBuilder extends Builder {
        public CampfireCookingBuilder() {
            super(CAMPFIRE_COOKING_TYPE);
        }

        public static CampfireCookingBuilder create() {
            return new CampfireCookingBuilder();
        }
    }

    public static class Builder implements LibyRecipe.Builder {
        protected Identifier recipeType;
        protected ItemConvertible result;
        protected LibyIngredient ingredient;
        protected double experience;
        protected int cookingTime;

        public Builder(Identifier typeIdentifier) {
            this.recipeType = typeIdentifier;
            this.experience = 1;
            this.cookingTime = 200;
        }

        protected static Builder create(Identifier typeIdentifier) {
            return new Builder(typeIdentifier);
        }

        public Builder setResult(ItemConvertible result) {
            this.result = result;
            return this;
        }

        public Builder setIngredient(ItemConvertible ingredient) {
            this.ingredient = LibyIngredient.ofItem(ingredient, null);
            return this;
        }

        public Builder setIngredient(LibyIngredient ingredient) {
            this.ingredient = ingredient;
            return this;
        }

        public Builder setCookingTime(int cookingTime) {
            this.cookingTime = cookingTime;
            return this;
        }

        public Builder setExperience(double experience) {
            this.experience = experience;
            return this;
        }

        @Override
        public LibyRecipe build() {
            return new LibySmeltingRecipe(recipeType, ingredient, result.asItem().liby$getId(), experience, cookingTime);
        }
    }
}
