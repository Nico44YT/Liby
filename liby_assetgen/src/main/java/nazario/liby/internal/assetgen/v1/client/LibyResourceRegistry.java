package nazario.liby.internal.assetgen.v1.client;

import net.minecraft.resource.ResourcePack;
import org.jetbrains.annotations.ApiStatus;

import java.util.Set;

@ApiStatus.Internal
public interface LibyResourceRegistry {
    String getPrefix();
    Set<String> collectNamespaces();
    void acceptResultConsumer(String namespace, String prefix, ResourcePack.ResultConsumer consumer);
}
