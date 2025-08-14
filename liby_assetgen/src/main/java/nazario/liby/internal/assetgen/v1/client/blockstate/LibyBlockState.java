package nazario.liby.internal.assetgen.v1.client.blockstate;

import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.util.Map;
import java.util.function.Supplier;

public abstract class LibyBlockState {
    public Identifier id;

    public Identifier getId() {
        return this.id;
    }

    public abstract void accept(Identifier resourceId, Map<Identifier, Supplier<InputStream>> map);
}
