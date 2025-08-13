package nazario.liby.internal.resource;

import com.google.gson.JsonObject;
import nazario.liby.api.util.LibyIdentifier;
import nazario.liby.internal.injections.LibyIdentifierResolvable;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class LibyJsonResource implements LibyIdentifierResolvable {
    protected Identifier id;
    protected JsonObject jsonObject;

    public JsonObject createJson() {
        return this.jsonObject;
    }

    public Identifier getId() {
        return this.id;
    }

    public LibyJsonResource setId(Identifier id) {
        this.id = id;
        return this;
    }

    @Override
    public LibyIdentifier liby$getId() {
        return new LibyIdentifier(this.id);
    }
}
