package nazario.liby.api.assetgen.v1.client;

import nazario.liby.api.assetgen.v1.client.texture.LibyTexture;
import nazario.liby.internal.assetgen.v1.client.blockstate.LibyBlockState;
import nazario.liby.internal.assetgen.v1.client.blockstate.LibyBlockStateRegistry;
import nazario.liby.internal.assetgen.v1.client.model.LibyModel;
import nazario.liby.internal.assetgen.v1.client.model.LibyModelRegistry;
import nazario.liby.internal.assetgen.v1.client.texture.LibyTextureRegistry;
import net.minecraft.util.Identifier;

public class LibyAssetRegistry {
    protected final String packNamespace;
    private LibyAssetRegistry(String packNamespace) {
        this.packNamespace = packNamespace;
    }

    public static LibyAssetRegistry of(String packNamespace) {
        return new LibyAssetRegistry(packNamespace);
    }

    public LibyModel<?> registerModel(LibyModel<?> model) {
        LibyModelRegistry.get().registerModel(this.packNamespace, model);
        return model;
    }

    public <T extends LibyBlockState> T registerBlockSate(T blockstate) {
        LibyBlockStateRegistry.get().registerBlockState(this.packNamespace, blockstate);
        return blockstate;
    }

    public LibyTexture registerTexture(Identifier textureIdentifier, LibyTexture libyTexture) {
        String prefix = textureIdentifier.toString().split("/")[0];
        return this.registerTexture(prefix, textureIdentifier, libyTexture);
    }

    public LibyTexture registerTexture(String prefix, Identifier identifier, LibyTexture texture) {
        LibyTextureRegistry.get(prefix).registerTexture(this.packNamespace, identifier, texture);
        return texture;
    }
}
