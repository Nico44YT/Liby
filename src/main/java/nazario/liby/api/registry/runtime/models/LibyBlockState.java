package nazario.liby.api.registry.runtime.models;

import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.util.Identifier;

import java.util.List;

public abstract class LibyBlockState {
    public Identifier id;
    public String resourcePackName;

    public Identifier getId() {
        return this.id;
    }

    public List<ModelLoader.SourceTrackedData> createTrackedData() {
        return List.of();
    }
}
