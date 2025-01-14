package nazario.liby.api.registry.runtime.models;

import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

import java.util.List;

public abstract class LibyBlockState {
    public Identifier id;
    public String resourcePackName;

    public List<Resource> createResource() {
        return List.of();
    }

    public Identifier getId() {
        return this.id;
    }
}
