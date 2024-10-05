package nazario.liby.block;

import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class LibyMultiBlock extends BlockWithEntity {
    public static final BooleanProperty DESTROYED = BooleanProperty.of("destroyed");
    public static final BooleanProperty PARENT = BooleanProperty.of("parent");
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING; // Add facing property for horizontal placement

    public final VoxelShape SHAPE;

    public final BlockPos[] childBlocks;

    protected LibyMultiBlock(Settings settings, BlockPos[] childBlocks) {
        super(settings.pistonBehavior(PistonBehavior.BLOCK));

        this.childBlocks = childBlocks;
        this.SHAPE = createVoxelShape();
    }

    @Override
    public void onPlaced(World world, BlockPos masterPos, BlockState masterState, @Nullable LivingEntity placer, ItemStack itemStack) {
        Direction facing = placer.getHorizontalFacing(); // Get player's facing direction

        world.setBlockState(masterPos, Blocks.AIR.getDefaultState());

        for (int i = 0; i < childBlocks.length; i++) {
            BlockPos rotatedChildPos = rotateBlockPos(childBlocks[i], facing); // Rotate based on facing
            BlockPos childWorldPos = masterPos.add(rotatedChildPos);

            world.setBlockState(childWorldPos, masterState.with(PARENT, i == 0));

            ((LibyMultiBlockEntity) world.getBlockEntity(childWorldPos)).setParentPos(childBlocks[0]);
        }

        if (world.getBlockEntity(childBlocks[0]) instanceof LibyMultiBlockEntity multiblockEntity) {
            multiblockEntity.setParentPos(childBlocks[0]);
        }
    }


    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(DESTROYED, false).with(FACING, ctx.getHorizontalPlayerFacing());
    }

    // Helper method to rotate BlockPos based on the facing direction
    private BlockPos rotateBlockPos(BlockPos pos, Direction facing) {
        return switch (facing) {
            case NORTH -> new BlockPos(-pos.getX(), pos.getY(), -pos.getZ());
            case SOUTH -> new BlockPos(pos.getX(), pos.getY(), pos.getZ());
            case WEST -> new BlockPos(-pos.getZ(), pos.getY(), pos.getX());
            case EAST -> new BlockPos(pos.getZ(), pos.getY(), -pos.getX());
            default -> pos;
        };
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        this.spawnBreakParticles(world, player, pos, state);
        world.emitGameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Emitter.of(player, state));
        return state;
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if(state.equals(newState)) return;

        if(state.get(PARENT)) {
            for (int i = 0; i < childBlocks.length; i++) {
                BlockPos rotatedChildPos = rotateBlockPos(childBlocks[i], state.get(FACING));
                BlockPos childWorldPos = pos.add(rotatedChildPos);

                BlockState childState = world.getBlockState(childWorldPos);
                if (childState.getBlock() instanceof LibyMultiBlock && !childState.get(PARENT)) {
                    world.setBlockState(childWorldPos, state.with(PARENT, false).with(DESTROYED, true));
                }
            }
            //world.setBlockState(pos, Blocks.AIR.getDefaultState());
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
            super.onStateReplaced(state, world, pos, newState, moved);
            return;
        }

        if(newState.getBlock() instanceof LibyMultiBlock) {
            if(newState.get(DESTROYED) && !newState.get(PARENT)) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }

        if(state.getBlock() instanceof LibyMultiBlock) {
            if(!state.get(DESTROYED) && !state.get(PARENT)) {
                LibyMultiBlockEntity entity = (LibyMultiBlockEntity)world.getBlockEntity(pos);

                if(entity == null) return;

                BlockPos parentPos = entity.parentPos;
                this.onStateReplaced(world.getBlockState(parentPos), world, parentPos, Blocks.AIR.getDefaultState(), moved);
            }
        }

        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if(!state.get(PARENT)) {
            LibyMultiBlockEntity entity = (LibyMultiBlockEntity)world.getBlockEntity(pos);
            if(entity.parentPos == null) return super.onUse(state, world, pos, player, hit);
            return onMultiBlockUse(world, state, world.getBlockState(entity.parentPos), pos, entity.parentPos, player, hit);
        }

        return onMultiBlockUse(world, state, state, pos, pos, player, hit);
    }

    public ActionResult onMultiBlockUse(World world, BlockState clickedState, BlockState parentState, BlockPos clickedPos, BlockPos parentPos, PlayerEntity player, BlockHitResult hit) {
        return ActionResult.PASS;
    }


    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction facing = state.get(FACING); // Get player's facing direction

        if (world.getBlockEntity(pos) instanceof LibyMultiBlockEntity multiblockEntity) {
            multiblockEntity.setParentPos(pos);
        }

        for (int i = 0; i < childBlocks.length; i++) {
            BlockPos rotatedChildPos = rotateBlockPos(childBlocks[i], facing); // Rotate based on facing
            BlockPos childWorldPos = pos.add(rotatedChildPos);

            if(!world.getBlockState(childWorldPos).isReplaceable()) return false;
        }

        return true;
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!state.get(PARENT)) {
            LibyMultiBlockEntity entity = (LibyMultiBlockEntity)world.getBlockEntity(pos);
            if(entity.parentPos == null) return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
            return onMultiBlockWithItem(world, stack, player, hand, hit, state, world.getBlockState(entity.parentPos), pos, entity.parentPos);
        }

        return onMultiBlockWithItem(world, stack, player, hand, hit, state, state, pos, pos);
    }

    public ItemActionResult onMultiBlockWithItem(World world, ItemStack stack, PlayerEntity player, Hand hand, BlockHitResult hit, BlockState clickedState, BlockState parentState, BlockPos clickedPos, BlockPos parentPos) {
        return ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    //@Override
    //protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    //    if(true) {
    //        return SHAPE;
    //    }
//
    //    LibyMultiBlockEntity entity = (LibyMultiBlockEntity)world.getBlockEntity(pos);
    //    if(entity == null) return super.getOutlineShape(state, world, pos, context);
//
    //    BlockPos parentPos = entity.parentPos;
    //    if(parentPos == null) return super.getOutlineShape(state, world, pos, context);
    //    BlockPos offset = pos.subtract(parentPos);
//
    //    return SHAPE.offset(offset.getX() * 16, offset.getY() * 16, offset.getZ() * 16);
    //}

    public VoxelShape createVoxelShape() {
        List<VoxelShape> shapes = new ArrayList<VoxelShape>();
        shapes.add(Block.createCuboidShape(0, 0, 0, 16, 16, 16));
        for(int i = 0;i<childBlocks.length;i++) {
            BlockPos childPos = childBlocks[i];

            shapes.add(Block.createCuboidShape(childPos.getX() * 16, childPos.getY() * 16, childPos.getZ() * 16,
                    childPos.getX() * 16 + 16, childPos.getY() * 16 + 16, childPos.getZ() * 16 + 16));
        }

        return shapes.stream().reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PARENT, FACING, DESTROYED); // Add FACING to block properties
    }


}
