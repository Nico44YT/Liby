package nazario.liby.api.assetgen.v1.client.model;

import com.google.gson.JsonObject;
import nazario.liby.api.util.LibyIdentifier;
import nazario.liby.internal.assetgen.v1.client.model.LibyModel;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class LibyModelHelper {

    public static LibyModel<?> createItem(Item item, Identifier... spriteIdentifier) {
        List<Pair<String, Identifier>> texturePairs = new ArrayList<>();

        for (int i = 0; i < spriteIdentifier.length; i++) {
            texturePairs.add(new Pair<>("layer" + i, spriteIdentifier[i]));
        }

        return createFromParent(item.asItem().liby$getId().prependPath("item/"), LibyIdentifier.ofVanilla("item/generated"), texturePairs);
    }

    public static LibyModel<?> createBlockItem(Block block) {
        return createFromParent(block.liby$getId().prependPath("item/"), block.liby$getId().prependPath("block/"), new ArrayList<>());
    }

    public static LibyModel<?> createBlock(Block block, Identifier parentId, List<Pair<String, Identifier>> texturePairs) {
        return createFromParent(block.liby$getId().prependPath("block/"), parentId, texturePairs);
    }

    public static LibyModel<?> createFromParent(Identifier modelId, Identifier parentId, List<Pair<String, Identifier>> texturePairs) {
        JsonObject modelJson = new JsonObject();

        modelJson.addProperty("parent", parentId.toString());

        JsonObject textureObject = new JsonObject();

        texturePairs.forEach(pair -> {
            String name = pair.getLeft();
            Identifier id = pair.getRight();

            textureObject.addProperty(name, id.toString());
        });

        modelJson.add("textures", textureObject);

        return new LibyJsonModel(modelId, modelJson);
    }
}
