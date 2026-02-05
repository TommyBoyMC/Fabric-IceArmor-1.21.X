package net.tom.icearmor.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.tom.icearmor.block.entity.ModBlockEntities;
import net.tom.icearmor.block.entity.custom.FridgeBlockEntity;
import org.jetbrains.annotations.Nullable;

public class FridgeBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final MapCodec<FridgeBlock> CODEC = FridgeBlock.createCodec(FridgeBlock::new);

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    private static final VoxelShape NORTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0, 2, 2, 16, 16, 16),

            Block.createCuboidShape(14, 0, 2, 16, 2, 4),
            Block.createCuboidShape(0, 0, 2, 2, 2, 4),
            Block.createCuboidShape(14, 0, 14, 16, 2, 16),
            Block.createCuboidShape(0, 0, 14, 2, 2, 16),

            Block.createCuboidShape(12, 5, 0, 13, 6, 2),
            Block.createCuboidShape(12, 12, 0, 13, 13, 2),
            Block.createCuboidShape(12, 6, 0, 13, 12, 1)
    );

    private static final VoxelShape SOUTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0, 2, 0, 16, 16, 14),

            Block.createCuboidShape(14, 0, 0, 16, 2, 2),
            Block.createCuboidShape(0, 0, 0, 2, 2, 2),
            Block.createCuboidShape(14, 0, 12, 16, 2, 14),
            Block.createCuboidShape(0, 0, 12, 2, 2, 14),

            Block.createCuboidShape(3, 5, 14, 4, 6, 16),
            Block.createCuboidShape(3, 12, 14, 4, 13, 16),
            Block.createCuboidShape(3, 6, 15, 4, 12, 16)

    );

    private static final VoxelShape EAST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0, 2, 0, 14, 16, 16),

            Block.createCuboidShape(0, 0, 0, 2, 2, 2),
            Block.createCuboidShape(0, 0, 14, 2, 2, 16),
            Block.createCuboidShape(12, 0, 0, 14, 2, 2),
            Block.createCuboidShape(12, 0, 14, 14, 2, 16),

            Block.createCuboidShape(14, 5, 12, 16, 6, 13),
            Block.createCuboidShape(14, 12, 12, 16, 13, 13),
            Block.createCuboidShape(15, 6, 12, 16, 12, 13)

    );

    private static final VoxelShape WEST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(2, 2, 0, 16, 16, 16),

            Block.createCuboidShape(2, 0, 0, 4, 2, 2),
            Block.createCuboidShape(2, 0, 14, 4, 2, 16),
            Block.createCuboidShape(14, 0, 0, 16, 2, 2),
            Block.createCuboidShape(14, 0, 14, 16, 2, 16),

            Block.createCuboidShape(0, 5, 3, 2, 6, 4),
            Block.createCuboidShape(0, 12, 3, 2, 13, 4),
            Block.createCuboidShape(0, 6, 3, 1, 12, 4)

    );


    public FridgeBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);

        return switch (direction) {
            case SOUTH -> SOUTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
            default -> NORTH_SHAPE;
        };
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FridgeBlockEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if(state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(blockEntity instanceof FridgeBlockEntity) {
                ItemScatterer.spawn(world, pos, ((FridgeBlockEntity) blockEntity));
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos,
                                             PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(world.getBlockEntity(pos) instanceof FridgeBlockEntity fridgeBlockEntity) {
            if(!player.isSneaking() && !world.isClient()) {
                player.openHandledScreen(fridgeBlockEntity);
            }
        }

        return ItemActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if(world.isClient()) {
            return null;
        }

        return validateTicker(type, ModBlockEntities.FRIDGE_BE,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }
}

