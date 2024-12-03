package nazario.liby.registry.internal;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

import java.util.HashMap;

public class LibyClientEntityTypeList {
    public static final HashMap<EntityType<? extends Entity>, EntityRendererFactory<? extends Entity>> entityRenderList = new HashMap<>();
}
