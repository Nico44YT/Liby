package nazario.test;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class TestBlock extends Block {
    public TestBlock(Settings settings) {
        super(settings);
    }


    @Override
    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return super.getCameraCollisionShape(state, world, pos, context);
    }
}
