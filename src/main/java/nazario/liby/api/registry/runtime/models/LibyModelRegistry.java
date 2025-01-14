package nazario.liby.api.registry.runtime.models;

import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LibyModelRegistry {
    protected static List<LibyModel> libyModels = new ArrayList<>();
    protected static HashMap<Identifier, LibyBlockState> libyBlockStateMap = new HashMap<>();

    public static void addBlockState(LibyBlockState... libyBlockState) {
        for(LibyBlockState state : libyBlockState) {
            libyBlockStateMap.put(state.id, state);
        }
    }

    public static void register(LibyModel... models) {
        libyModels.addAll(Arrays.stream(models).toList());
    }

    public static List<LibyModel> getModelList() {
        return new ArrayList<>(libyModels);
    }

    public static HashMap<Identifier, LibyBlockState> getBlockStateMap() {
        return new HashMap<>(libyBlockStateMap);
    }
}
