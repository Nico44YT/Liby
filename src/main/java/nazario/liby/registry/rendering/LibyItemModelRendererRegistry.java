package nazario.liby.registry.rendering;

import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemConvertible;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LibyItemModelRendererRegistry {
    @ApiStatus.Internal
    protected static final HashMap<ItemConvertible, LibyItemModelObject> renderList = new HashMap<>();

    @ApiStatus.Internal
    protected static final List<ModelIdentifier> modelList = new ArrayList<>();

    public static void register(ItemConvertible itemConvertible, ModelIdentifier modelIdentifier, ModelTransformationMode... modes) {
        renderList.put(itemConvertible, new LibyItemModelObject(itemConvertible, modelIdentifier, modes));
    }

    public static HashMap<ItemConvertible, LibyItemModelObject> getList() {
        return renderList;
    }

    @ApiStatus.Internal
    public static List<ModelIdentifier> _getModelList() {
        return modelList;
    }

    @ApiStatus.Internal
    public static void _addModel(ModelIdentifier modelIdentifier) {
        modelList.add(modelIdentifier);
    }
}