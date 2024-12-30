package nazario.liby.api.registry.runtime.models;

import net.minecraft.block.Block;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;

public class LibyModelHelper {
    public static LibyModel createItemGenerated(ItemConvertible convertible, Identifier spriteIdentifier) {
        return createItemCustom(convertible, spriteIdentifier, "minecraft:item/generated");
    }

    public static LibyModel createBlock(Block block, Identifier parentModel, Identifier... textures) {
        Identifier id = block.getRegistryEntry().getKey().get().getValue();

        String jsonModel = "{\n";

        jsonModel += "\t\"parent\": \"" + parentModel.toString() + "\",\n";
        jsonModel += "\t\"textures\": {\n";
        jsonModel += "\t\t\"particle\": \"" + textures[0].toString() + "\",\n";

        for (int i = 0; i < textures.length; i++) {
            jsonModel += "\t\t\"" + i + "\": \"" + textures[i].toString() + "\"";
            if (i + 1 < textures.length) {
                jsonModel += ",";
            }
            jsonModel += "\n";
        }

        jsonModel += "\n\t}\n}";

        return new LibyJsonModel(Identifier.of(id.getNamespace(), "block/" + id.getPath()), jsonModel);
    }

    public static LibyModel createBlockItem(Block block) {
        Identifier id = block.getRegistryEntry().getKey().get().getValue();

        String jsonModel =
                "{\n" +
                        "\t\"parent\": \"" + Identifier.of(id.getNamespace(), "block/" + id.getPath()).toString() + "\"\n" +
                        "}";

        return new LibyJsonModel(Identifier.of(id.getNamespace(), "item/" + id.getPath()), jsonModel);
    }

    public static LibyModel createItemCustom(ItemConvertible convertible, Identifier spriteIdentifier, String parentModel) {
        String jsonModel = "{\n" +
                "\t\"parent\": \"" + parentModel + "\",\n" +
                "\t\"textures\": {\n" +
                "\t\t\"layer0\": \"" + spriteIdentifier.toString() + "\"\n" +
                "\t}\n" +
                "}";
        return new LibyJsonModel(convertible.asItem().liby$getId(), jsonModel);
    }
}
