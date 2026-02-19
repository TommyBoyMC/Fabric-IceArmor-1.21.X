package net.tom.icearmor.block.entity.custom;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.tom.icearmor.block.custom.FridgeBlock;
import net.tom.icearmor.block.entity.ImplementedInventory;
import net.tom.icearmor.block.entity.ModBlockEntities;
import net.tom.icearmor.item.ModItems;
import net.tom.icearmor.recipe.FridgeRecipe;
import net.tom.icearmor.recipe.FridgeRecipeInput;
import net.tom.icearmor.recipe.ModRecipes;
import net.tom.icearmor.screen.custom.DoubleFridgeScreenHandler;
import net.tom.icearmor.screen.custom.FridgeScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static net.tom.icearmor.block.custom.FridgeBlock.PART;

public class FridgeBlockEntity extends BlockEntity implements ImplementedInventory, ExtendedScreenHandlerFactory<BlockPos> {
    // private int size = 4; CHANGE 4 TO size!!!!!                               v

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    private static final int INPUT_SLOT_2 = 2;
    private static final int OUTPUT_SLOT_2 = 3;

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 120;

    public FridgeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FRIDGE_BE, pos, state);

        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> FridgeBlockEntity.this.progress;
                    case 1 -> FridgeBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0: FridgeBlockEntity.this.progress = value;
                    case 1: FridgeBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 2;
            }
        };
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return getMaster().inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        nbt.putInt("fridge.progress", progress);
        nbt.putInt("fridge.max_progress", maxProgress);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        Inventories.readNbt(nbt, inventory, registryLookup);
        progress = nbt.getInt("fridge.progress");
        maxProgress = nbt.getInt("fridge.max_progress");
        super.readNbt(nbt, registryLookup);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return this.pos;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.icearmor.fridge");
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {

        if (getCachedState().get(PART) != FridgeBlock.FridgePart.SINGLE) {
            return new DoubleFridgeScreenHandler(syncId, playerInventory, this.pos);
        }

        return new FridgeScreenHandler(syncId, playerInventory, this.pos);
    }



    public void tick(World world, BlockPos pos, BlockState state) {
        if (getMaster() != this) return;

        if(hasRecipe(state)) {
            increaseCraftingProgress(state);
            markDirty(world, pos, state);

            if(hasCraftingFinished()) {
                craftItem();
                resetProgress(state);
            }
        } else {
            resetProgress(state);
        }
    }

    private void craftItem() {
        Optional<RecipeEntry<FridgeRecipe>> recipe = getCurrentRecipe();

        ItemStack output = recipe.get().value().output();
        this.removeStack(INPUT_SLOT, 1);
        this.removeStack(INPUT_SLOT_2, 1);
        this.setStack(OUTPUT_SLOT, new ItemStack(output.getItem(),
                this.getStack(OUTPUT_SLOT).getCount() + output.getCount()));
        this.setStack(OUTPUT_SLOT_2, new ItemStack(output.getItem(),
                this.getStack(OUTPUT_SLOT_2).getCount() + output.getCount()));
    }

    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void resetProgress(BlockState state) {
        this.progress = 0;
        this.maxProgress = getMaxProgressFromState(state);
    }

    private void increaseCraftingProgress(BlockState state) {
        this.maxProgress = getMaxProgressFromState(state);
        this.progress++;
    }

    private boolean hasRecipe(BlockState state) {
        ItemStack stack1 = this.getStack(INPUT_SLOT);
        ItemStack stack2 = this.getStack(INPUT_SLOT_2);
        FridgeBlock.FridgePart part = state.get(PART);

        if(part != FridgeBlock.FridgePart.SINGLE) {
            if (!stack1.isOf(stack2.getItem())) return false;
        }

        Optional<RecipeEntry<FridgeRecipe>> recipe = getCurrentRecipe();
        if(recipe.isEmpty()) {
            return false;
        }

        ItemStack output = recipe.get().value().output();
        return canInsertAmountIntoOutputSlot(output.getCount(), state) && canInsertItemIntoOutputSlot(output);
    }


    private Optional<RecipeEntry<FridgeRecipe>> getCurrentRecipe() {
        return this.getWorld().getRecipeManager()
                .getFirstMatch(ModRecipes.FRIDGE_TYPE,
                        new FridgeRecipeInput(inventory.get(INPUT_SLOT)),
                        this.getWorld());
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getItem() == output.getItem()
                && this.getStack(OUTPUT_SLOT_2).isEmpty() || this.getStack(OUTPUT_SLOT_2).getItem() == output.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int count, BlockState state) {
        int maxCount = this.getStack(OUTPUT_SLOT).isEmpty() ? 64 : this.getStack(OUTPUT_SLOT).getMaxCount();
        int currentCount = this.getStack(OUTPUT_SLOT).getCount();

        FridgeBlock.FridgePart part = state.get(PART);
        int maxCount_2 = 1000;
        int currentCount_2 = 0;
        if (part == FridgeBlock.FridgePart.BOTTOM) {
            maxCount_2 = this.getStack(OUTPUT_SLOT_2).isEmpty() ? 64 : this.getStack(OUTPUT_SLOT_2).getMaxCount();
            currentCount_2 = this.getStack(OUTPUT_SLOT_2).getCount();
        }

        return maxCount >= currentCount + count && maxCount_2 >= currentCount_2 + count;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    public FridgeBlockEntity getMaster() {
        if (this.getCachedState().get(PART) == FridgeBlock.FridgePart.TOP) {

            if (world == null) return this;

            BlockEntity be = world.getBlockEntity(pos.down());

            if (be instanceof FridgeBlockEntity fridge) {
                return fridge;
            }
        }

        return this;
    }

    public PropertyDelegate getPropertyDelegate() {
        return this.propertyDelegate;
    }

    private int getMaxProgressFromState(BlockState state) {
        FridgeBlock.FridgePart part = state.get(PART);

        if (part == FridgeBlock.FridgePart.SINGLE) {
            return 120;
        } else {
            return 100;
        }
    }
}
