package nazario.liby.internal.assetgen.v1.client;

import com.mojang.bridge.game.PackType;
import net.minecraft.SharedConstants;
import net.minecraft.resource.*;
import net.minecraft.resource.metadata.PackResourceMetadata;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

@ApiStatus.Internal
public class LibyResourcePack implements ResourcePack {
    public final String name = "liby_runtime_assets";
    public final ResourcePackProfile profile;
    public final PackResourceMetadata metadata;
    public final Map<Identifier, Supplier<InputStream>> assetList;
    protected static final Text profileName = Text.translatable("resourcepack.liby.name");
    protected static final Text profileDescription = Text.translatable("resourcepack.liby.description");

    private static LibyResourcePack INSTANCE;
    public static LibyResourcePack get() {
        if(INSTANCE == null) INSTANCE = new LibyResourcePack();
        return INSTANCE;
    }

    public LibyResourcePack() {
        this.metadata = new PackResourceMetadata(profileDescription, SharedConstants.getGameVersion().getPackVersion(PackType.RESOURCE));
        this.profile = new ResourcePackProfile(
                name,
                profileName,
                true,
                () -> this,
                this.metadata,
                ResourceType.CLIENT_RESOURCES,
                ResourcePackProfile.InsertionPosition.TOP,
                ResourcePackSource.PACK_SOURCE_NONE
        );

        this.assetList = new HashMap<>();
    }

    @Override
    public @Nullable InputStream openRoot(String fileName) throws IOException {
        return null;
    }

    @Override
    public InputStream open(ResourceType type, Identifier id) throws IOException {
        if(type == ResourceType.SERVER_DATA) return null;
        return assetList.get(id).get();
    }

    @Override
    public Collection<Identifier> findResources(ResourceType type, String namespace, String prefix, Predicate<Identifier> allowedPathPredicate) {
        LibyResourceRegistries.get().callRegistry(namespace, prefix, this.assetList);
        return this.assetList.keySet();
    }

    @Override
    public boolean contains(ResourceType type, Identifier id) {
        if(type == ResourceType.SERVER_DATA) return false;
        return assetList.containsKey(id);
    }

    @Override
    public Set<String> getNamespaces(ResourceType type) {
        return LibyResourceRegistries.get().collectNamespaces();
    }

    @Override
    public @Nullable <T> T parseMetadata(ResourceMetadataReader<T> metaReader) throws IOException {
        return (T)switch(metaReader.getKey()) {
            case "pack" -> this.metadata;
            default -> null;
        };
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void close() {

    }
}
