package nazario.liby.api.registry.itemgroup;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LibyItemGroupRegistry {
    private static final List<LibyItemGroup> LIBY_ITEM_GROUPS = new ArrayList<>();

    public static void registerItemGroup(LibyItemGroup itemGroup) {
        LIBY_ITEM_GROUPS.add(itemGroup);
    }

    @ApiStatus.Internal
    public static void registerAll() {
        LIBY_ITEM_GROUPS.forEach(LibyItemGroup::register);
    }

    @ApiStatus.Internal
    public static List<LibyItemGroup> getItemGroupList() {
        return new ArrayList<>(LIBY_ITEM_GROUPS);
    }

    public Optional<LibyItemGroup> getItemGroup(Identifier id) {
        return LIBY_ITEM_GROUPS.stream().filter(itemGroup -> itemGroup.id.equals(id)).findFirst();
    }
}
