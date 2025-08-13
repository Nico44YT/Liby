package nazario.liby.internal.assetgen.v1.client.model;

import nazario.liby.api.assetgen.v1.client.model.LibyJsonModel;
import nazario.liby.internal.assetgen.v1.client.LibyResourceRegistries;
import nazario.liby.internal.assetgen.v1.client.LibyResourceRegistry;
import net.minecraft.resource.ResourcePack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ApiStatus.Internal
public class LibyModelRegistry implements LibyResourceRegistry {
    private static LibyModelRegistry instance;
    public Map<String, List<LibyModel<?>>> modelMap;

    private LibyModelRegistry() {
        this.modelMap = new ConcurrentHashMap<>();

        LibyResourceRegistries.get().registerRegistry(this);
    }

    public static LibyModelRegistry get() {
        if (instance == null) {
            instance = new LibyModelRegistry();
        }
        return instance;
    }

    public void registerModel(String namespace, LibyModel<?>... model) {
        modelMap.computeIfAbsent(namespace, k -> new ArrayList<>())
                .addAll(List.of(model));
    }

    @Nullable
    public LibyModel<?> getModel(Identifier id) {
        List<LibyModel<?>> list = modelMap.get(id.getNamespace());
        if (list != null) {
            for (LibyModel<?> model : list) {
                if (model.getId().equals(id)) {
                    return model;
                }
            }
        }
        return null;
    }

    @Override
    public String getPrefix() {
        return "models";
    }

    @Override
    public void acceptResultConsumer(String namespace, String prefix, ResourcePack.ResultConsumer consumer) {
        modelMap.getOrDefault(namespace, List.of()).forEach(model -> {
            Identifier id = Identifier.of(model.getId().getNamespace(), prefix + "/" + model.getId().getPath() + ".json");
            if(model instanceof LibyJsonModel jsonModel) {
                consumer.accept(id, jsonModel.bake());
            }
        });
    }

    @Override
    public Set<String> collectNamespaces() {
        Set<String> namespaces = new HashSet<>();

        this.modelMap.forEach((parentNamespace, libyModels) -> {
            namespaces.add(parentNamespace);

            libyModels.forEach(model -> {
                namespaces.add(model.getId().getNamespace());
            });
        });

        return namespaces;
    }
}