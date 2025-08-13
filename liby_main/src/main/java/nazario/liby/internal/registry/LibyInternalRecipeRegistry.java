package nazario.liby.internal.registry;

import nazario.liby.api.recipe.LibyRecipe;
import nazario.liby.api.recipe.LibyRecipeBuilder;
import nazario.liby.internal.resource.LibyResourceRegistry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class LibyInternalRecipeRegistry extends LibyResourceRegistry {
    private static LibyInternalRecipeRegistry INSTANCE;
    public static LibyInternalRecipeRegistry get() {
        if(INSTANCE == null) INSTANCE = new LibyInternalRecipeRegistry();
        return INSTANCE;
    }

    public static void register(Identifier identifier, LibyRecipe libyRecipe) {
        libyRecipe.setId(identifier);
        get().addResource(libyRecipe);
    }

    public static void removeRecipe(Identifier id) {
        get().removeResource(id);
    }
}
