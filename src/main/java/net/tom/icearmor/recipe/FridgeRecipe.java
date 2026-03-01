package net.tom.icearmor.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public record FridgeRecipe(Ingredient inputItem, ItemStack output) implements Recipe<FridgeRecipeInput> {

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.of();
        list.add(this.inputItem);
        return list;
    }



    @Override
    public boolean matches(FridgeRecipeInput input, World world) {
        if(world.isClient()) {
            return false;
        }

        return inputItem.test(input.getStackInSlot(0));
    }

    @Override
    public ItemStack craft(FridgeRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.FRIDGE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.FRIDGE_TYPE;
    }

    public static class Serializer implements RecipeSerializer<FridgeRecipe> {
        public static final MapCodec<FridgeRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(FridgeRecipe::inputItem),
                ItemStack.CODEC.fieldOf("result").forGetter(FridgeRecipe::output)
        ).apply(inst, FridgeRecipe::new));

        public static final PacketCodec<RegistryByteBuf, FridgeRecipe> STREAM_CODEC =
                PacketCodec.tuple(
                        Ingredient.PACKET_CODEC, FridgeRecipe::inputItem,
                        ItemStack.PACKET_CODEC, FridgeRecipe::output,
                        FridgeRecipe::new);

        @Override
        public MapCodec<FridgeRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, FridgeRecipe> packetCodec() {
            return STREAM_CODEC;
        }
    }
}
