package nazario.liby.api.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import nazario.liby.api.registry.runtime.models.LibyJsonModel;
import nazario.liby.api.registry.runtime.models.LibyModel;
import net.minecraft.block.Block;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

public class LibyModelHelper {
    public static LibyModel createItemGenerated(ItemConvertible convertible, Identifier... spriteIdentifier) {
        return createItemCustom(convertible, spriteIdentifier, "minecraft:item/generated");
    }

    public static LibyModel createBlock(Block block, Identifier parentModel, Identifier... textures) {
        Identifier id = block.liby$getId();

        JsonObject model = new JsonObject();

        model.addProperty("parent", parentModel.toString());

        JsonObject textureObject = new JsonObject();

        textureObject.addProperty("particle", textures[0].toString());

        for(int i = 0;i<textures.length;i++) {
            textureObject.addProperty(String.valueOf(i), textures[i].toString());
        }

        model.add("textures", textureObject);

        return new LibyJsonModel(Identifier.of(id.getNamespace(), "block/" + id.getPath()), "", model, "block");
    }

    public static LibyModel createBlockItem(Block block) {
        Identifier id = block.liby$getId();

        JsonObject model = new JsonObject();

        model.addProperty("parent", Identifier.of(id.getNamespace(), "block/" + id.getPath()).toString());

        return new LibyJsonModel(Identifier.of(id.getNamespace(), "item/" + id.getPath()), "", model, "item");
    }

    public static LibyModel createParentModel(ModelIdentifier modelId, Identifier parentId, String modelType) {
        JsonObject model = new JsonObject();

        model.addProperty("parent", parentId.toString());

        return new LibyJsonModel(modelId, model, modelType);
    }

    public static LibyModel createItemCustom(ItemConvertible convertible, Identifier[] spriteIdentifier, String parentModel) {
        return createItemCustom(ModelIdentifier.ofInventoryVariant(convertible.asItem().liby$getId()), spriteIdentifier, parentModel);
    }

    public static LibyModel createItemCustom(ModelIdentifier id, Identifier[] spriteIdentifier, String parentModel) {

        JsonObject model = new JsonObject();

        model.addProperty("parent", parentModel);

        JsonObject texturesObject = new JsonObject();

        for(int i = 0;i<spriteIdentifier.length;i++) {
            texturesObject.addProperty("layer" + i, spriteIdentifier[i].toString());
        }

        return new LibyJsonModel(id.id().withPrefixedPath("item/"), id.getVariant(), model, "item");
    }

    public static LibyModel createCustom(ModelIdentifier id, Pair<String, Identifier>[] spriteKeyPair, String parentModel, String modelType) {

        JsonObject model = new JsonObject();

        model.addProperty("parent", parentModel);

        JsonObject texturesObject = new JsonObject();

        for(Pair<String, Identifier> pair : spriteKeyPair) {
            texturesObject.addProperty(pair.getLeft(), pair.getRight().toString());
        }

        model.add("textures", texturesObject);

        return new LibyJsonModel(id, model, modelType);
    }

    public static LibyModel createFromJson(Identifier id, String json, String modelType) {
        return new LibyJsonModel(id, "", JsonParser.parseString(json).getAsJsonObject(), modelType);
    }

    public static LibyModel createFromJson(Identifier id, JsonObject object, String modelType) {
        return new LibyJsonModel(id, "", object, modelType);
    }
}
