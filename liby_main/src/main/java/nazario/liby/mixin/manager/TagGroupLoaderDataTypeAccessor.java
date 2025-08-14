package nazario.liby.mixin.manager;

import net.minecraft.tag.TagGroupLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TagGroupLoader.class)
public interface TagGroupLoaderDataTypeAccessor {
    @Accessor("dataType")
    String getDataType();
}
