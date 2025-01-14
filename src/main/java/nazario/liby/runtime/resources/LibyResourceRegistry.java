package nazario.liby.runtime.resources;

import com.google.gson.JsonElement;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.Map;

@ApiStatus.Internal
public abstract class LibyResourceRegistry {

    protected final Map<Identifier, JsonElement> resourceMap = new HashMap<>();

    protected void addResource(LibyJsonObject resourceObject) {
        resourceMap.put(resourceObject.getId(), resourceObject.create());
    }

    public Map<Identifier, JsonElement> getResourceMap() {
        return resourceMap;
    }

}
