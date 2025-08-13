package nazario.liby.internal.resource;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApiStatus.Internal
public abstract class LibyResourceRegistry {
    protected final Set<LibyJsonResource> resources = new HashSet<>();

    protected void addResource(LibyJsonResource... jsonObjects) {
        resources.addAll(List.of(jsonObjects));
    }

    protected void removeResource(Identifier id) {
        resources.removeIf(libyJsonObject -> libyJsonObject.getId().equals(id));
    }

    public Set<LibyJsonResource> resourceMap() {
        return resources;
    }
}
