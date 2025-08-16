package nazario.liby.internal.assetgen.v1.client.lang;

import nazario.liby.internal.assetgen.v1.client.LibyResourceRegistries;
import nazario.liby.internal.assetgen.v1.client.LibyResourceRegistry;
import net.minecraft.resource.ResourcePack;

import java.util.Set;

public class LibyLangRegistry implements LibyResourceRegistry {

    public LibyLangRegistry() {
        LibyResourceRegistries.get().registerRegistry(this);
    }

    @Override
    public String getPrefix() {
        return "lang";
    }

    @Override
    public Set<String> collectNamespaces() {
        return Set.of();
    }

    @Override
    public void acceptResultConsumer(String namespace, String prefix, ResourcePack.ResultConsumer consumer) {

    }
}
