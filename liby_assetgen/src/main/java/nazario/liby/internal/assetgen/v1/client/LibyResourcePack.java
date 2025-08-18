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
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

@ApiStatus.Internal
public class LibyResourcePack implements ResourcePack, ResourcePackProvider, ResourcePackProfile.PackFactory {
    public final String name = "liby_runtime_assets";
    public final ResourcePackProfile profile;
    public final PackResourceMetadata metadata;
    public final PackFeatureSetMetadata featureMetadata;
    protected static final Text profileName = Text.translatable("resourcepack.liby.name");
    protected static final Text profileDescription = Text.translatable("resourcepack.liby.description");
    protected final ResourcePackInfo info;
    protected final ResourcePackPosition position;

    private static LibyResourcePack INSTANCE;
    public static LibyResourcePack get() {
        if(INSTANCE == null) INSTANCE = new LibyResourcePack();
        return INSTANCE;
    }

    public LibyResourcePack() {
        this.metadata = new PackResourceMetadata(profileDescription, SharedConstants.getGameVersion().getResourceVersion(ResourceType.CLIENT_RESOURCES), Optional.empty());
        this.featureMetadata = new PackFeatureSetMetadata(FeatureSet.empty());
        this.info = new ResourcePackInfo(name, profileName, ResourcePackSource.NONE, Optional.empty());
        this.position = new ResourcePackPosition(true, ResourcePackProfile.InsertionPosition.TOP, false);
        this.profile = ResourcePackProfile.create(
                this.info,
                this,
                ResourceType.CLIENT_RESOURCES,
                this.position
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
    public ResourcePackInfo getInfo() {
        return this.info;
    }

    @Override
    public void close() {

    }

    @Override
    public void register(Consumer<ResourcePackProfile> profileAdder) {
        profileAdder.accept(this.profile);
    }

    @Override
    public ResourcePack open(ResourcePackInfo info) {
        return this;
    }

    @Override
    public ResourcePack openWithOverlays(ResourcePackInfo info, ResourcePackProfile.Metadata metadata) {
        return this;
    }
}
