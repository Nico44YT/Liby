package nazario.liby.api.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class LibyIngredient {
    protected final String type;
    protected final Identifier identifier;
    protected final Character recipeCharacter;

    protected LibyIngredient(String type, Identifier identifier, Character recipeCharacter) {
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

    public static LibyIngredient ofItem(Identifier id, @Nullable  Character recipeCharacter) {
        return new LibyIngredient("item", id, recipeCharacter);
    }

    public static LibyIngredient ofItem(ItemConvertible itemConvertible, @Nullable Character recipeCharacter) {
        return new LibyIngredient("item", itemConvertible.asItem().liby$getId(), recipeCharacter);
    }

    public static LibyIngredient ofTag(Identifier tag, @Nullable Character recipeCharacter) {
        return new LibyIngredient("tag", tag, recipeCharacter);
    }

    public static LibyIngredient ofTag(TagKey<Item> tagKey, @Nullable Character recipeCharacter) {
        return new LibyIngredient("tag", tagKey.id(), recipeCharacter);
    }

    public static LibyIngredient ofCustom(String type, Identifier id, @Nullable Character recipeCharacter) {
        return new LibyIngredient(type, id, recipeCharacter);
    }
}
