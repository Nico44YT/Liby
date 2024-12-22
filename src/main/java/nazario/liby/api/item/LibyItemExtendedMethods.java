package nazario.liby.api.item;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.Optional;

public interface LibyItemExtendedMethods {
    default Optional<Item.Settings> liby$getItemSettings() {
        return Optional.empty();
    }

    default Identifier liby$getId() {
        return null;
    }
}
