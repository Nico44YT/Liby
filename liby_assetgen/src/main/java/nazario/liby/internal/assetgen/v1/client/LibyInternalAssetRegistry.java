package nazario.liby.internal.assetgen.v1.client;

import nazario.liby.api.assetgen.v1.client.LibyAssetRegistry;
import nazario.liby.api.assetgen.v1.client.model.obj.LibyObjModel;
import nazario.liby.api.assetgen.v1.client.texture.LibyTexture;
import nazario.liby.internal.assetgen.v1.client.blockstate.LibyBlockState;
import nazario.liby.internal.assetgen.v1.client.blockstate.LibyBlockStateRegistry;
import nazario.liby.internal.assetgen.v1.client.model.LibyModel;
import nazario.liby.internal.assetgen.v1.client.model.LibyModelRegistry;
import nazario.liby.internal.assetgen.v1.client.model.item_model_predicate.LibyItemModelPredicate;
import nazario.liby.internal.assetgen.v1.client.model.item_model_predicate.LibyItemModelRule;
import nazario.liby.internal.assetgen.v1.client.resource_loader.LibyObjResourceLoader;
import nazario.liby.internal.assetgen.v1.client.texture.LibyTextureRegistry;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class LibyInternalAssetRegistry implements LibyAssetRegistry {
    public static final Map<String, LibyAssetRegistry> registries = new ConcurrentHashMap<>();

    public static LibyAssetRegistry create(String packNamespace) {
        return registries.computeIfAbsent(packNamespace, LibyInternalAssetRegistry::new);
    }

    protected final String packNamespace;
    public static final Set<LibyItemModelRule> itemModelRules = new HashSet<>();

    public LibyInternalAssetRegistry(String packNamespace) {
        this.packNamespace = packNamespace;
    }

    @Override
    public LibyModel<?> registerModel(LibyModel<?> model) {
        LibyModelRegistry.get().registerModel(this.packNamespace, model);
        return model;
    }

    @Override
    public <T extends LibyBlockState> T registerBlockSate(T blockstate) {
        LibyBlockStateRegistry.get().registerBlockState(this.packNamespace, blockstate);
        return blockstate;
    }

    @Override
    public LibyTexture registerTexture(Identifier textureIdentifier, LibyTexture libyTexture) {
        String prefix = textureIdentifier.toString().split("/")[0];
        return this.registerTexture(prefix, textureIdentifier, libyTexture);
    }

    @Override
    public LibyTexture registerTexture(String prefix, Identifier identifier, LibyTexture texture) {
        LibyTextureRegistry.get(prefix).registerTexture(this.packNamespace, identifier, texture);
        return texture;
    }

    @Override
    public void registerItemPredicateModel(ItemConvertible item, ModelIdentifier modelIdentifier, LibyItemModelPredicate predicate) {
        itemModelRules.add(new LibyItemModelRule(item, modelIdentifier, predicate));
    }

    public static Supplier<LibyObjModel> getObjModel(Identifier id) {
        return () -> LibyObjResourceLoader.get().getMap().get(id);
    }

    public String getPackNamespace() {
        return packNamespace;
    }

    public Set<LibyItemModelRule> getItemModelRules() {
        return itemModelRules;
    }
}