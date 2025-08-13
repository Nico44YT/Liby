package nazario.liby.internal.assetgen.v1.client.model;

import net.minecraft.util.Identifier;

public interface LibyModel<T> {
    Identifier getId();
    T bake();
}
