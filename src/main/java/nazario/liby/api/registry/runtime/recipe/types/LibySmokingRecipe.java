package nazario.liby.api.registry.runtime.recipe.types;

import nazario.liby.api.registry.runtime.recipe.LibyIngredient;
import net.minecraft.util.Identifier;

public class LibySmokingRecipe extends LibySmeltingRecipe {
    private static final Identifier TYPE = new Identifier("minecraft", "smoking");

    public LibySmokingRecipe(Identifier id, LibyIngredient ingredient, Identifier result, double experience, int cookingTime) {
        super(id, ingredient, result, experience, cookingTime);
    }

    @Override
    protected Identifier getType() {
        return TYPE;
    }
}
