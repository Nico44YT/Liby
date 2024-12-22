package nazario.liby.api.registry.runtime.models;

import net.minecraft.block.BlockState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LibyModelRegistry {
    protected static List<LibyModel> libyModels = new ArrayList<>();
    protected static HashMap<BlockState, LibyBlockState> libyBlockStateList = new HashMap<>();

    public static void register(LibyModel... models) {
        libyModels.addAll(Arrays.stream(models).toList());
    }

    public static void addBlockState(LibyBlockState model) {
        libyBlockStateList.put(model.state, model);
    }

    public static List<LibyModel> getList() {
        return new ArrayList<>(libyModels);
    }

    public static HashMap<BlockState, LibyBlockState> getMap() {
        return new HashMap<>(libyBlockStateList);
    }

    public static LibyBlockState getModel(BlockState state) {
        return libyBlockStateList.get(state);
    }
}
