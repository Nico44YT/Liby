package nazario.liby.api.recipe.types;

import com.google.gson.JsonObject;
import nazario.liby.api.recipe.LibyIngredient;
import nazario.liby.api.recipe.LibyRecipe;
import net.minecraft.util.Identifier;

public class LibySmithingRecipe extends LibyRecipe {
    private static final Identifier TYPE = Identifier.of("minecraft", "smithing");

    public LibySmithingRecipe(LibyIngredient base, LibyIngredient addition, Identifier result) {
        JsonObject recipe = this.createBasicJson();

        JsonObject baseObject = new JsonObject();
        baseObject.addProperty(base.getType(), base.getIngredientId().toString());
        recipe.add("base", baseObject);

        JsonObject additionObject = new JsonObject();
        additionObject.addProperty(addition.getType(), addition.getIngredientId().toString());
        recipe.add("base", additionObject);

        // Add result
        recipe.addProperty("result", result.toString());

        this.jsonObject = recipe;
    }

    @Override
    protected Identifier getType() {
        return TYPE;
    }
}
