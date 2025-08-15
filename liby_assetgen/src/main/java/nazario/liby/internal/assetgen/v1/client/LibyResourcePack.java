package nazario.liby.internal.assetgen.v1.client;

import net.minecraft.SharedConstants;
import net.minecraft.resource.*;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.resource.metadata.PackFeatureSetMetadata;
import net.minecraft.resource.metadata.PackResourceMetadata;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.function.Consumer;

@ApiStatus.Internal
public class LibyResourcePack implements ResourcePack, ResourcePackProvider {
    public final String name = "liby_runtime_assets";
    public final ResourcePackProfile profile;
    public final PackResourceMetadata metadata;
    public final PackFeatureSetMetadata featureMetadata;
    protected static final Text profileName = Text.translatable("resourcepack.liby.name");
    protected static final Text profileDescription = Text.translatable("resourcepack.liby.description");

    private static LibyResourcePack INSTANCE;
    public static LibyResourcePack get() {
        if(INSTANCE == null) INSTANCE = new LibyResourcePack();
        return INSTANCE;
    }

    public LibyResourcePack() {
        this.metadata = new PackResourceMetadata(profileDescription, SharedConstants.getGameVersion().getResourceVersion(ResourceType.CLIENT_RESOURCES));
        this.featureMetadata = new PackFeatureSetMetadata(FeatureSet.empty());
        this.profile = ResourcePackProfile.create(
                name,
                profileName,
                true,
                (_name) -> this,
                ResourceType.CLIENT_RESOURCES,
                ResourcePackProfile.InsertionPosition.TOP,
                ResourcePackSource.NONE
        );
    }

    @Override
    public @Nullable InputSupplier<InputStream> openRoot(String... segments) {
        return null;
    }

    @Override
    public @Nullable InputSupplier<InputStream> open(ResourceType type, Identifier id) {
        return null;
    }

    @Override
    public void findResources(ResourceType type, String namespace, String prefix, ResultConsumer consumer) {
        LibyResourceRegistries.get().callRegistry(namespace, prefix, consumer);
    }

    @Override
    public Set<String> getNamespaces(ResourceType type) {
        return LibyResourceRegistries.get().collectNamespaces();
    }

    @Override
    public @Nullable <T> T parseMetadata(ResourceMetadataReader<T> metaReader) throws IOException {
        return (T)switch(metaReader.getKey()) {
            case "pack" -> this.metadata;
            case "features" -> this.featureMetadata;
            default -> null;
        };
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isAlwaysStable() {
        return true;
    }

    @Override
    public void close() {

    }

    @Override
    public void register(Consumer<ResourcePackProfile> profileAdder) {
        profileAdder.accept(this.profile);
    }
}
