package nazario.liby.internal.assetgen.v1.client.blockstate;

import nazario.liby.internal.assetgen.v1.client.LibyResourceRegistries;
import nazario.liby.internal.assetgen.v1.client.LibyResourceRegistry;
import net.minecraft.resource.ResourcePack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@ApiStatus.Internal
public class LibyBlockStateRegistry implements LibyResourceRegistry {
    private static LibyBlockStateRegistry instance;
    public Map<String, List<LibyBlockState>> blockstateMap;

    private LibyBlockStateRegistry() {
        this.blockstateMap = new ConcurrentHashMap<>();

        LibyResourceRegistries.get().registerRegistry(this);
    }

    public static LibyBlockStateRegistry get() {
        if (instance == null) {
            instance = new LibyBlockStateRegistry();
        }
        return instance;
    }

    public void registerBlockState(String namespace, LibyBlockState... model) {
        blockstateMap.computeIfAbsent(namespace, k -> new ArrayList<>())
                .addAll(List.of(model));
    }

    @Nullable
    public LibyBlockState getBlockState(Identifier id) {
        List<LibyBlockState> list = blockstateMap.get(id.getNamespace());
        if (list != null) {
            for (LibyBlockState blockState : list) {
                if (blockState.getId().equals(id)) {
                    return blockState;
                }
            }
        }
        return null;
    }

    @Override
    public String getPrefix() {
        return "blockstates";
    }

    @Override
    public void acceptResultConsumer(String namespace, String prefix, Map<Identifier, Supplier<InputStream>> map) {
        blockstateMap.getOrDefault(namespace, List.of()).forEach(blockState -> {
            Identifier id = Identifier.of(blockState.getId().getNamespace(), prefix+"/"+blockState.getId().getPath() + ".json");
            blockState.accept(id, map);
        });
    }

    @Override
    public Set<String> collectNamespaces() {
        Set<String> namespaces = new HashSet<>();

        this.blockstateMap.forEach((parentNamespace, libyBlockStates) -> {
            namespaces.add(parentNamespace);

            libyBlockStates.forEach(blockState -> {
                namespaces.add(blockState.getId().getNamespace());
            });
        });

        return namespaces;
    }
}