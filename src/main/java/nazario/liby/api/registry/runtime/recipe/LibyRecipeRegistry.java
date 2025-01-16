package nazario.liby.api.registry.runtime.recipe;

import nazario.liby.runtime.resources.LibyResourceRegistry;

public class LibyRecipeRegistry extends LibyResourceRegistry {

    private static LibyRecipeRegistry INSTANCE;
    public static LibyRecipeRegistry get() {
        if(INSTANCE == null) INSTANCE = new LibyRecipeRegistry();
        return INSTANCE;
    }

    public static void addRecipe(LibyRecipe libyRecipe) {
        get().addResource(libyRecipe);
    }
}