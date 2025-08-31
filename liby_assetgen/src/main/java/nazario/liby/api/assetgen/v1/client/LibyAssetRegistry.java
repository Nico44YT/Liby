package nazario.liby.api.assetgen.v1.client;

import nazario.liby.api.assetgen.v1.client.renderer.LibyTooltipRenderer;
import nazario.liby.api.assetgen.v1.client.texture.LibyTexture;
import nazario.liby.internal.assetgen.v1.client.LibyInternalAssetRegistry;
import nazario.liby.internal.assetgen.v1.client.blockstate.LibyBlockState;
import nazario.liby.internal.assetgen.v1.client.model.LibyModel;
import nazario.liby.internal.assetgen.v1.client.model.item_model_predicate.LibyItemModelPredicate;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public interface LibyAssetRegistry {
    static LibyAssetRegistry of(String packNamespace) {
        return LibyInternalAssetRegistry.create(packNamespace);
    }

    LibyModel<?> registerModel(LibyModel<?> model);

    <T extends LibyBlockState> T registerBlockSate(T blockstate);

    LibyTexture registerTexture(Identifier textureIdentifier, LibyTexture libyTexture);
    LibyTexture registerTexture(String prefix, Identifier identifier, LibyTexture texture);
    void registerItemPredicateModel(ItemConvertible item, ModelIdentifier modelIdentifier, LibyItemModelPredicate predicate);
    void registerLang(String langCode, Map<String, String> keys);
    void registerTooltipRenderer(Item item, LibyTooltipRenderer.Factory renderer);
}
