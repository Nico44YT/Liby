package nazario.liby.mixin.manager;

import nazario.liby.internal.registry.LibyInternalTagRegistry;
import net.minecraft.registry.tag.TagGroupLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mixin(TagGroupLoader.class)
public abstract class TagGroupLoaderMixin {

    @Inject(method = "loadTags", at = @At("RETURN"))
    private void liby$loadTags(ResourceManager manager, CallbackInfoReturnable<Map<Identifier, List<TagGroupLoader.TrackedEntry>>> cir) {
        // Ensure the tag group loader and its data type map exist
        TagGroupLoader<?> tagGroupLoader = (TagGroupLoader<?>)(Object)this;
        Map<Identifier, List<TagGroupLoader.TrackedEntry>> additionalTags = LibyInternalTagRegistry.getTagMap().get(((TagGroupLoaderDataTypeAccessor)tagGroupLoader).getDataType());

        if (additionalTags != null) {
            for (Map.Entry<Identifier, List<TagGroupLoader.TrackedEntry>> entry : additionalTags.entrySet()) {
                // Merge the lists instead of overwriting
                cir.getReturnValue().merge(entry.getKey(), entry.getValue(), (existingList, newList) -> {
                    // Combine existing and new lists
                    List<TagGroupLoader.TrackedEntry> mergedList = new ArrayList<>(existingList);
                    mergedList.addAll(newList);
                    return mergedList;
                });
            }
        }
    }
}