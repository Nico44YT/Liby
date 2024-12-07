package nazario.liby.registry.rendering;

import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemConvertible;

import java.util.Arrays;

public final class LibyItemModelObject {
    public final ItemConvertible itemConvertible;
    public final ModelIdentifier modelIdentifier;
    public final ModelTransformation.Mode[] modes;

    public LibyItemModelObject(ItemConvertible itemConvertible, ModelIdentifier modelIdentifier, ModelTransformation.Mode... modes) {
        this.itemConvertible = itemConvertible;
        this.modelIdentifier = modelIdentifier;
        this.modes = modes;
    }

    public boolean containsMode(ModelTransformation.Mode mode) {
        return Arrays.stream(modes).toList().contains(mode);
    }
}