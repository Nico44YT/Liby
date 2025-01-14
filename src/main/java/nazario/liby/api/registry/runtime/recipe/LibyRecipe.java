package nazario.liby.api.registry.runtime.recipe;

import nazario.liby.runtime.resources.LibyJsonObject;
import net.minecraft.util.Identifier;

public abstract class LibyRecipe extends LibyJsonObject {
    protected Identifier getType() {
        return Identifier.of("minecraft","none");
    }
}
