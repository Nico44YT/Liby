package nazario.liby.mixin;

import nazario.liby.api.registry.runtime.tags.LibyTagRegistry;
import net.minecraft.resource.ResourceManager;
import net.minecraft.tag.TagGroupLoader;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mixin(TagGroupLoader.class)
public abstract class TagGroupLoaderMixin {
    @Inject(method = "loadTags", at = @At("RETURN"), cancellable = true)
    private void liby$loadTags(ResourceManager manager, CallbackInfoReturnable<Map<Identifier, List<TagGroupLoader.TrackedEntry>>> cir) {
        // Ensure the original return value is not null
        Map<Identifier, List<TagGroupLoader.TrackedEntry>> originalMap = cir.getReturnValue();
        if (originalMap == null) {
            originalMap = new HashMap<>(); // Create an empty map if null
        }

        // Create a copy of the original map
        HashMap<Identifier, List<TagGroupLoader.TrackedEntry>> map = new HashMap<>(originalMap);

        // Ensure the tag group loader and its data type map exist
        TagGroupLoader<?> tagGroupLoader = (TagGroupLoader<?>) (Object) this;
        Map<Identifier, List<TagGroupLoader.TrackedEntry>> additionalTags =
                LibyTagRegistry.getMap().get(tagGroupLoader.dataType);

        if (additionalTags != null) {
            for (Map.Entry<Identifier, List<TagGroupLoader.TrackedEntry>> entry : additionalTags.entrySet()) {
                // Merge the lists instead of overwriting
                map.merge(entry.getKey(), entry.getValue(), (existingList, newList) -> {
                    // Combine existing and new lists
                    List<TagGroupLoader.TrackedEntry> mergedList = new ArrayList<>(existingList);
                    mergedList.addAll(newList);
                    return mergedList;
                });
            }
        }

        // Set the modified map as the return value
        cir.setReturnValue(map);
    }
}

