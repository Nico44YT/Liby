package nazario.liby.internal.assetgen.v1.client;

import net.minecraft.resource.ResourcePack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.io.InputStream;
import java.util.*;
import java.util.function.Supplier;

@ApiStatus.Internal
public class LibyResourceRegistries {
    private static LibyResourceRegistries INSTANCE;
    public static LibyResourceRegistries get() {
        if(INSTANCE == null) INSTANCE = new LibyResourceRegistries();
        return INSTANCE;
    }

    public List<LibyResourceRegistry> registries;

    public LibyResourceRegistries() {
        this.registries = new ArrayList<>();
    }

    public void registerRegistry(LibyResourceRegistry registry) {
        this.registries.add(registry);
    }

    public void callRegistry(String namespace, String prefix, Map<Identifier, Supplier<InputStream>> map) {
        this.registries.stream().filter(registry -> prefix.equals(registry.getPrefix())).forEach(registry -> registry.acceptResultConsumer(namespace, prefix, map));
    }

    public Set<String> collectNamespaces() {
        Set<String> namespaces = new HashSet<>();

        this.registries.forEach(registry -> {
            namespaces.addAll(registry.collectNamespaces());
        });

        return namespaces;
    }
}
