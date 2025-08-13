package nazario.liby.internal.assetgen.v1.client.texture;

import nazario.liby.api.assetgen.v1.client.texture.LibyTexture;
import nazario.liby.api.assetgen.v1.client.texture.TextureList;
import nazario.liby.internal.assetgen.v1.client.LibyResourceRegistries;
import nazario.liby.internal.assetgen.v1.client.LibyResourceRegistry;
import net.minecraft.resource.ResourcePack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ApiStatus.Internal
public class LibyTextureRegistry implements LibyResourceRegistry {
    public static Map<String, LibyTextureRegistry> registries = new HashMap<>();
    public static LibyTextureRegistry get(String prefix) {
        if(!registries.containsKey(prefix)) {
            registries.put(prefix, new LibyTextureRegistry(prefix));
        }

        return registries.get(prefix);
    }

    public final Map<String, Map<Identifier, LibyTexture>> textureMap;
    private final String prefix;

    private LibyTextureRegistry(String prefix) {
        this.textureMap = new ConcurrentHashMap<>();
        this.prefix = prefix;

        LibyResourceRegistries.get().registerRegistry(this);
    }

    public void registerTexture(String namespace, Identifier identifier, LibyTexture texture) {
        textureMap.computeIfAbsent(namespace, k -> new HashMap<>()).put(identifier, texture);
    }

    @Override
    public String getPrefix() {
        return "textures/" + prefix;
    }

    @Override
    public void acceptResultConsumer(String namespace, String prefix, ResourcePack.ResultConsumer consumer) {
        textureMap.getOrDefault(namespace, new HashMap<>()).forEach((identifier, texture) -> {
            Identifier id = Identifier.of(identifier.getNamespace(), "textures/" + identifier.getPath() + ".png");
            TextureList.textures.put(identifier, texture.getPixels());
            consumer.accept(id, texture.getInputStream());
        });
    }

    @Override
    public Set<String> collectNamespaces() {
        Set<String> namespaces = new HashSet<>();

        this.textureMap.forEach((parentNamespace, libyModels) -> {
            namespaces.add(parentNamespace);

            libyModels.forEach((identifier, texture) -> {
                namespaces.add(identifier.getNamespace());
            });
        });

        return namespaces;
    }
}