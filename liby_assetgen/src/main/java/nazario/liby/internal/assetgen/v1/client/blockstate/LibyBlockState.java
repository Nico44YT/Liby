package nazario.liby.internal.assetgen.v1.client.blockstate;

import net.minecraft.resource.ResourcePack;
import net.minecraft.util.Identifier;

public abstract class LibyBlockState {
    public Identifier id;

    public Identifier getId() {
        return this.id;
    }

    public abstract void accept(Identifier resourceId, ResourcePack.ResultConsumer consumer);
}
