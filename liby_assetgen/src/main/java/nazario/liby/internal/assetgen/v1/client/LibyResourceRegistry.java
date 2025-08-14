package nazario.liby.internal.assetgen.v1.client;

import net.minecraft.resource.ResourcePack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

@ApiStatus.Internal
public interface LibyResourceRegistry {
    String getPrefix();
    Set<String> collectNamespaces();
    void acceptResultConsumer(String namespace, String prefix, Map<Identifier, Supplier<InputStream>> map);
}
