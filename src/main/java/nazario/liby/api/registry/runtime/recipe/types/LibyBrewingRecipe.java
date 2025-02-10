package nazario.liby.api.registry.runtime.recipe.types;

import nazario.liby.api.registry.runtime.recipe.LibyRecipe;
import nazario.liby.mixin.BrewingRecipeRegistryMixin;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class LibyBrewingRecipe extends LibyRecipe {
    private static final Identifier TYPE = new Identifier("liby", "brewing");

    public LibyBrewingRecipe(Identifier id, Potion result, Item ingredient, Potion input) {
        Potion potion = Registry.register(Registry.POTION, id, result);

        BrewingRecipeRegistryMixin.registerPotionRecipe(input, ingredient, potion);

        this.id = id;
    }


    protected Identifier getType() {
        return TYPE;
    }
}
