package nazario.liby.api.registry.runtime.models;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.block.BlockModels;
import net.minecraft.client.util.ModelIdentifier;

public class LibyBlockState {
    public BlockState state;
    public LibyModel model;

    public LibyBlockState(BlockState state, LibyModel model) {
        this.state = state;
        this.model = model;
    }

    public ModelIdentifier getModelIdentifier() {
        return BlockModels.getModelId(state);
    }
}
