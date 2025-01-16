package nazario.liby.api.registry.runtime.models;

import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemConvertible;

import java.util.Arrays;

public final class LibyItemModelObject {
    public final ItemConvertible itemConvertible;
    public final ModelIdentifier modelIdentifier;
    public final ModelTransformationMode[] modes;

    public LibyItemModelObject(ItemConvertible itemConvertible, ModelIdentifier modelIdentifier, ModelTransformationMode... modes) {
        this.itemConvertible = itemConvertible;
        this.modelIdentifier = modelIdentifier;
        this.modes = modes;
    }

    public boolean containsMode(ModelTransformationMode mode) {
        return Arrays.stream(modes).toList().contains(mode);
    }
}