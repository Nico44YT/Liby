package nazario.liby.api.registry.helper;

import nazario.liby.api.recipe.LibyRecipe;
import nazario.liby.internal.registry.LibyImplementableRegistry;
import nazario.liby.internal.registry.LibyImplementedRegistry;
import net.minecraft.util.Identifier;

public interface LibyRecipeRegistry extends LibyImplementableRegistry {
    static LibyRecipeRegistry of(String name) {
        return LibyImplementedRegistry.ofRecipes(name);
    }

    <T extends LibyRecipe> T registerRecipe(Identifier recipeIdentifier, T recipe);
}
