package nazario.liby.api.registry.runtime.recipe.types;

import nazario.liby.api.registry.runtime.recipe.LibyIngredient;
import net.minecraft.util.Identifier;

public class LibyCampfireRecipe extends LibySmeltingRecipe {
    private static final Identifier TYPE = new Identifier("minecraft", "campfire_cooking");

    public LibyCampfireRecipe(Identifier id, LibyIngredient ingredient, Identifier result, double experience, int cookingTime) {
        super(id, ingredient, result, experience, cookingTime);
    }

    @Override
    protected Identifier getType() {
        return TYPE;
    }
}
