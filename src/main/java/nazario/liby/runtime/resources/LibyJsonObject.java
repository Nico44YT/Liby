package nazario.liby.runtime.resources;

import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public abstract class LibyJsonObject {
    protected Identifier id;
    protected JsonObject jsonObject;

    public JsonObject create() {
        return this.jsonObject;
    }

    public Identifier getId() {
        return this.id;
    }
}
