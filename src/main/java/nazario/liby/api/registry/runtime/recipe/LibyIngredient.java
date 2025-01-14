package nazario.liby.api.registry.runtime.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class LibyIngredient {
    protected final String type;
    protected final Identifier identifier;
    protected final Character recipeCharacter;

    private LibyIngredient(String type, Identifier identifier, Character recipeCharacter) {
        this.type = type;
        this.identifier = identifier;
        this.recipeCharacter = recipeCharacter;
    }

    public String getType() {
        return this.type;
    }

    public Identifier getIngredientId() {
        return this.identifier;
    }

    public Character getRecipeCharacter() {
        return this.recipeCharacter;
    }

    public static LibyIngredient createItem(Identifier id) {
        return new LibyIngredient("item", id, ' ');
    }

    public static LibyIngredient createItem(Identifier id, Character recipeCharacter) {
        return new LibyIngredient("item", id, recipeCharacter);
    }

    public static LibyIngredient createItem(ItemConvertible itemConvertible) {
        return new LibyIngredient("item", itemConvertible.asItem().liby$getId(), ' ');
    }

    public static LibyIngredient createItem(ItemConvertible itemConvertible, Character recipeCharacter) {
        return new LibyIngredient("item", itemConvertible.asItem().liby$getId(), recipeCharacter);
    }

    public static LibyIngredient createTag(Identifier tag) {
        return new LibyIngredient("tag", tag, ' ');
    }

    public static LibyIngredient createTag(Identifier tag, Character recipeCharacter) {
        return new LibyIngredient("tag", tag, recipeCharacter);
    }

    public static LibyIngredient createTag(TagKey<Item> tagKey) {
        return new LibyIngredient("tag", tagKey.id(), ' ');
    }

    public static LibyIngredient createTag(TagKey<Item> tagKey, Character recipeCharacter) {
        return new LibyIngredient("tag", tagKey.id(), recipeCharacter);
    }
}
