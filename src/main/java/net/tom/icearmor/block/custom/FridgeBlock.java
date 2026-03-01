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
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.tom.icearmor.block.entity.ModBlockEntities;
import net.tom.icearmor.block.entity.custom.FridgeBlockEntity;
import org.jetbrains.annotations.Nullable;

public class FridgeBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final MapCodec<FridgeBlock> CODEC = FridgeBlock.createCodec(FridgeBlock::new);

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final EnumProperty<FridgePart> PART =
            EnumProperty.of("part", FridgePart.class);


    private static final VoxelShape SINGLE_NORTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0, 2, 2, 16, 16, 16),

            Block.createCuboidShape(14, 0, 2, 16, 2, 4),
            Block.createCuboidShape(0, 0, 2, 2, 2, 4),
            Block.createCuboidShape(14, 0, 14, 16, 2, 16),
            Block.createCuboidShape(0, 0, 14, 2, 2, 16),

            Block.createCuboidShape(12, 5, 0, 13, 6, 2),
            Block.createCuboidShape(12, 12, 0, 13, 13, 2),
            Block.createCuboidShape(12, 6, 0, 13, 12, 1)
    );

    private static final VoxelShape BOTTOM_NORTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0, 2, 2, 16, 16, 16),

            Block.createCuboidShape(14, 0, 2, 16, 2, 4),
            Block.createCuboidShape(0, 0, 2, 2, 2, 4),
            Block.createCuboidShape(14, 0, 14, 16, 2, 16),
            Block.createCuboidShape(0, 0, 14, 2, 2, 16),

            Block.createCuboidShape(12, 11, 0, 13, 12, 2),
            Block.createCuboidShape(12, 12, 0, 13, 16, 1)
    );

    private static final VoxelShape TOP_NORTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0, 0, 2, 16, 14, 16),

            Block.createCuboidShape(12, 4, 0, 13, 5, 2),
            Block.createCuboidShape(12, 0, 0, 13, 4, 1)
    );

    private static final VoxelShape SINGLE_SOUTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0, 2, 0, 16, 16, 14),

            Block.createCuboidShape(14, 0, 0, 16, 2, 2),
            Block.createCuboidShape(0, 0, 0, 2, 2, 2),
            Block.createCuboidShape(14, 0, 12, 16, 2, 14),
            Block.createCuboidShape(0, 0, 12, 2, 2, 14),

            Block.createCuboidShape(3, 5, 14, 4, 6, 16),
            Block.createCuboidShape(3, 12, 14, 4, 13, 16),
            Block.createCuboidShape(3, 6, 15, 4, 12, 16)
    );

    private static final VoxelShape BOTTOM_SOUTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0, 2, 0, 16, 16, 14),

            Block.createCuboidShape(14, 0, 0, 16, 2, 2),
            Block.createCuboidShape(0, 0, 0, 2, 2, 2),
            Block.createCuboidShape(14, 0, 12, 16, 2, 14),
            Block.createCuboidShape(0, 0, 12, 2, 2, 14),

            Block.createCuboidShape(3, 11, 14, 4, 12, 16),
            Block.createCuboidShape(3, 12, 15, 4, 16, 16)
    );

    private static final VoxelShape TOP_SOUTH_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0, 0, 0, 16, 14, 14),

            Block.createCuboidShape(3, 4, 14, 4, 5, 16),
            Block.createCuboidShape(3, 0, 15, 4, 4, 16)
    );

    private static final VoxelShape SINGLE_EAST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0, 2, 0, 14, 16, 16),

            Block.createCuboidShape(0, 0, 0, 2, 2, 2),
            Block.createCuboidShape(0, 0, 14, 2, 2, 16),
            Block.createCuboidShape(12, 0, 0, 14, 2, 2),
            Block.createCuboidShape(12, 0, 14, 14, 2, 16),

            Block.createCuboidShape(14, 5, 12, 16, 6, 13),
            Block.createCuboidShape(14, 12, 12, 16, 13, 13),
            Block.createCuboidShape(15, 6, 12, 16, 12, 13)
    );

    private static final VoxelShape BOTTOM_EAST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0, 2, 0, 14, 16, 16),

            Block.createCuboidShape(0, 0, 0, 2, 2, 2),
            Block.createCuboidShape(0, 0, 14, 2, 2, 16),
            Block.createCuboidShape(12, 0, 0, 14, 2, 2),
            Block.createCuboidShape(12, 0, 14, 14, 2, 16),

            Block.createCuboidShape(14, 11, 12, 16, 12, 13),
            Block.createCuboidShape(15, 12, 12, 16, 16, 13)
    );

    private static final VoxelShape TOP_EAST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(0, 0, 0, 14, 14, 16),

            Block.createCuboidShape(14, 4, 12, 16, 5, 13),
            Block.createCuboidShape(15, 0, 12, 16, 4, 13)
    );

    private static final VoxelShape SINGLE_WEST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(2, 2, 0, 16, 16, 16),

            Block.createCuboidShape(2, 0, 0, 4, 2, 2),
            Block.createCuboidShape(2, 0, 14, 4, 2, 16),
            Block.createCuboidShape(14, 0, 0, 16, 2, 2),
            Block.createCuboidShape(14, 0, 14, 16, 2, 16),

            Block.createCuboidShape(0, 5, 3, 2, 6, 4),
            Block.createCuboidShape(0, 12, 3, 2, 13, 4),
            Block.createCuboidShape(0, 6, 3, 1, 12, 4)
    );

    private static final VoxelShape BOTTOM_WEST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(2, 2, 0, 16, 16, 16),

            Block.createCuboidShape(2, 0, 0, 4, 2, 2),
            Block.createCuboidShape(2, 0, 14, 4, 2, 16),
            Block.createCuboidShape(14, 0, 0, 16, 2, 2),
            Block.createCuboidShape(14, 0, 14, 16, 2, 16),

            Block.createCuboidShape(0, 11, 3, 2, 12, 4),
            Block.createCuboidShape(0, 12, 3, 1, 16, 4)
    );

    private static final VoxelShape TOP_WEST_SHAPE = VoxelShapes.union(
            Block.createCuboidShape(2, 0, 0, 16, 14, 16),

            Block.createCuboidShape(0, 4, 3, 2, 5, 4),
            Block.createCuboidShape(0, 0, 3, 1, 4, 4)
    );


    public FridgeBlock(Settings settings) {
        super(settings);
        this.setDefaultState(
                this.stateManager.getDefaultState()
                        .with(FACING, Direction.NORTH)
                        .with(PART, FridgePart.SINGLE));
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        FridgePart part = state.get(PART);

        if(part == FridgePart.SINGLE) {
            return switch (direction) {
                case SOUTH -> SINGLE_SOUTH_SHAPE;
                case EAST -> SINGLE_EAST_SHAPE;
                case WEST -> SINGLE_WEST_SHAPE;
                default -> SINGLE_NORTH_SHAPE;
            };
        } else if(part == FridgePart.BOTTOM) {
            return switch (direction) {
                case SOUTH -> BOTTOM_SOUTH_SHAPE;
                case EAST -> BOTTOM_EAST_SHAPE;
                case WEST -> BOTTOM_WEST_SHAPE;
                default -> BOTTOM_NORTH_SHAPE;
            };
        } else {
            BlockPos masterPos = getMasterPos(state, pos);
            BlockState masterState = world.getBlockState(masterPos);

            if (masterState.getBlock() instanceof FridgeBlock) {
                Direction masterDirection = masterState.get(FACING);

                return switch (masterDirection) {
                    case SOUTH -> TOP_SOUTH_SHAPE;
                    case EAST -> TOP_EAST_SHAPE;
                    case WEST -> TOP_WEST_SHAPE;
                    default -> TOP_NORTH_SHAPE;
                };
            }
        }
        return SINGLE_NORTH_SHAPE;
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
        builder.add(FACING)
                .add(PART);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState()
                .with(FACING, ctx.getHorizontalPlayerFacing().getOpposite())
                .with(PART, FridgePart.SINGLE);
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
            BlockPos masterPos = getMasterPos(state, pos);
            BlockEntity be = world.getBlockEntity(masterPos);
            FridgePart part = state.get(PART);

            if(!player.isSneaking() && !world.isClient()) {
                if(part == FridgePart.SINGLE) {
                    player.openHandledScreen(fridgeBlockEntity);
                } else {
                    if(be instanceof FridgeBlockEntity fridgeBlockEntity1) {
                        player.openHandledScreen(fridgeBlockEntity1);
                    }
                }
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

    public enum FridgePart implements StringIdentifiable {
        SINGLE,
        TOP,
        BOTTOM;

        @Override
        public String asString() {
            return name().toLowerCase();
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state,
                                                Direction direction,
                                                BlockState neighborState,
                                                WorldAccess world,
                                                BlockPos pos,
                                                BlockPos neighborPos) {

        if (direction == Direction.UP && neighborState.isOf(this)) {
            return state
                    .with(PART, FridgePart.BOTTOM)
                    .with(FACING, neighborState.get(FACING));
        }

        if (direction == Direction.DOWN && neighborState.isOf(this)) {
            return state
                    .with(PART, FridgePart.TOP)
                    .with(FACING, neighborState.get(FACING));
        }

        if (state.get(PART) == FridgePart.TOP &&
                !world.getBlockState(pos.down()).isOf(this)) {
            return state.with(PART, FridgePart.SINGLE);
        }

        if (state.get(PART) == FridgePart.BOTTOM &&
                !world.getBlockState(pos.up()).isOf(this)) {
            return state.with(PART, FridgePart.SINGLE);
        }

        return state;
    }


    public static BlockPos getMasterPos(BlockState state, BlockPos pos) {
        if (state.get(PART) == FridgePart.TOP) {
            return pos.down();
        }
        return pos;
    }

}

