package nazario.liby.internal.registry;

import nazario.liby.api.tag.TagTypesList;
import net.minecraft.registry.tag.TagEntry;
import net.minecraft.registry.tag.TagGroupLoader;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@ApiStatus.Internal
public class LibyInternalTagRegistry {

    @ApiStatus.Internal
    private static HashMap<String, HashMap<Identifier, List<TagGroupLoader.TrackedEntry>>> tagMap = new HashMap<>();

    public static HashMap<String, HashMap<Identifier, List<TagGroupLoader.TrackedEntry>>> getTagMap() {
        return tagMap;
    }

    /**
     *
     * @param tagType The {@linkplain TagTypesList type} of the tag from {@link TagTypesList}
     * @param tagId The {@link Identifier} of the tag, e.g: "minecraft:candles"
     * @param source The source of tag e.g: "Minecraft" or "Mod Name"
     * @param tagEntries An array of the {@link TagEntry}
     */
    public static void add(String tagType, Identifier tagId, String source, TagEntry... tagEntries) {
        List<TagGroupLoader.TrackedEntry> list = new ArrayList<>(Arrays.stream(tagEntries).map(tagEntry -> new TagGroupLoader.TrackedEntry(tagEntry, source)).toList());

        HashMap<Identifier, List<TagGroupLoader.TrackedEntry>> tagMap = getTagMap().getOrDefault(tagType, new HashMap<>());
        List<TagGroupLoader.TrackedEntry> existingEntries = tagMap.getOrDefault(tagId, new ArrayList<>());

        list.addAll(existingEntries);

        tagMap.put(tagId, list);

        getTagMap().put(tagType, tagMap);
    }
}
