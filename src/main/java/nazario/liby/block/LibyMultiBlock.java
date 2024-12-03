package nazario.liby.block;

import nazario.liby.interfaces.LibySetBlockListener;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.command.argument.BlockStateArgument;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

public abstract class LibyMultiBlock extends BlockWithEntity implements LibySetBlockListener {
    public static final BooleanProperty PARENT = BooleanProperty.of("parent");
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING; // Add facing property for horizontal placement

    public final VoxelShape SHAPE;

    public final BlockPos[] childBlocks;

    protected LibyMultiBlock(Settings settings, BlockPos[] childBlocks) {
        super(settings);

        this.childBlocks = childBlocks;
        this.SHAPE = createVoxelShape();
    }

    @Override
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.BLOCK;
    }

    @Override
    public void onPlaced(World world, BlockPos masterPos, BlockState masterState, LivingEntity placer, @Nullable ItemStack itemStack) {
        Direction facing;
        if(placer != null) {
            facing = placer.getHorizontalFacing(); // Get player's facing direction
        } else {
            facing = masterState.get(FACING);
        }

        world.setBlockState(masterPos, Blocks.AIR.getDefaultState());

        for (int i = 0; i < childBlocks.length; i++) {
            BlockPos rotatedChildPos = rotateBlockPos(childBlocks[i], facing); // Rotate based on facing
            BlockPos childWorldPos = masterPos.add(rotatedChildPos);

            world.setBlockState(childWorldPos, masterState.with(PARENT, i == 0));

            ((LibyMultiBlockEntity) world.getBlockEntity(childWorldPos)).setParentPos(masterPos.add(childBlocks[0]));
        }

        if (world.getBlockEntity(childBlocks[0]) instanceof LibyMultiBlockEntity multiblockEntity) {
            multiblockEntity.setParentPos(childBlocks[0]);
        }
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, Direction.fromRotation(ctx.getPlayerYaw()));
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
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if(state.equals(newState)) return;

        if(state.get(PARENT)) {
            for (BlockPos relativeChildPos : childBlocks) {
                BlockPos rotatedChildPos = rotateBlockPos(relativeChildPos, state.get(FACING));
                BlockPos childWorldPos = pos.add(rotatedChildPos);

                BlockState childState = world.getBlockState(childWorldPos);
                if (childState.getBlock() instanceof LibyMultiBlock && !childState.get(PARENT)) {
                    // Set child block to air
                    world.setBlockState(childWorldPos, Blocks.AIR.getDefaultState());

                    // Safely handle the block entity
                    LibyMultiBlockEntity childEntity = getEntity(world, childWorldPos);
                    if (childEntity != null) {
                        childEntity.setDestroyed(true);
                    }
                }
            }
            // Set parent block to air
            world.setBlockState(pos, Blocks.AIR.getDefaultState());

            // Call super after custom logic
            super.onStateReplaced(state, world, pos, newState, moved);
            return;
        }

        if(newState.getBlock() instanceof LibyMultiBlock) {
            LibyMultiBlockEntity entity = getEntity(world, pos);
            if(entity != null && entity.isDestroyed() && !newState.get(PARENT)) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }

        if(state.getBlock() instanceof LibyMultiBlock) {
            LibyMultiBlockEntity entity = getEntity(world, pos);
            if(entity != null && !entity.isDestroyed() && !state.get(PARENT)) {
                BlockPos parentPos = entity.parentPos;
                BlockState parentState = world.getBlockState(parentPos);
                this.onStateReplaced(parentState, world, parentPos, Blocks.AIR.getDefaultState(), moved);
            }
        }

        super.onStateReplaced(state, world, pos, newState, moved);
    }


    @Override @ApiStatus.Internal
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!state.get(PARENT)) {
            LibyMultiBlockEntity entity = (LibyMultiBlockEntity)world.getBlockEntity(pos);
            if(entity.parentPos == null) return super.onUse(state, world, pos, player, hand, hit);
            return onMultiBlockUse(world, state, world.getBlockState(entity.parentPos), pos, entity.parentPos, player, hit);
        }

        return onMultiBlockUse(world, state, state, pos, pos, player, hit);
    }

    public ActionResult onMultiBlockUse(World world, BlockState clickedState, BlockState parentState, BlockPos clickedPos, BlockPos parentPos, PlayerEntity player, BlockHitResult hit) {
        return ActionResult.PASS;
    }


    @Override @ApiStatus.Internal
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction facing = state.get(FACING); // Get player's facing direction

        if (world.getBlockEntity(pos) instanceof LibyMultiBlockEntity multiblockEntity) {
            multiblockEntity.setParentPos(pos);
        }

        for (int i = 0; i < childBlocks.length; i++) {
            BlockPos rotatedChildPos = rotateBlockPos(childBlocks[i], facing); // Rotate based on facing
            BlockPos childWorldPos = pos.add(rotatedChildPos);

            if(!world.getBlockState(childWorldPos).isAir()) return false;
        }

        return true;
    }

    @Override
    public void setBlockStateEventHead(BlockStateArgument blockStateArgument, ServerWorld world, BlockPos pos, int flags, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockState = LibyMultiBlock.postProcessState(blockStateArgument.getBlockState(), world, pos);
        this.onPlaced(world, pos, blockState, null, null);
        cir.setReturnValue(true);
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
        builder.add(PARENT, FACING); // Add FACING to block properties
    }

    public LibyMultiBlockEntity getParent(World world, BlockPos pos) {
        if(world.getBlockEntity(pos) instanceof LibyMultiBlockEntity multiBlockEntity) {
            return (LibyMultiBlockEntity)world.getBlockEntity(multiBlockEntity.getParentPos());
        }

        return null;
    }

    public LibyMultiBlockEntity getEntity(World world, BlockPos pos) {
        return (LibyMultiBlockEntity)world.getBlockEntity(pos);
    }
}
