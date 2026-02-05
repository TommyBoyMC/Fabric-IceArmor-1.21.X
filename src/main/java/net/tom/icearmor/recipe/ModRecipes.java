package net.tom.icearmor.recipe;

import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.tom.icearmor.IceArmor;

public class ModRecipes {
    public static final RecipeSerializer<FridgeRecipe> FRIDGE_SERIALIZER = Registry.register(
            Registries.RECIPE_SERIALIZER, Identifier.of(IceArmor.MOD_ID, "fridge"),
                    new FridgeRecipe.Serializer());
    public static final RecipeType<FridgeRecipe> FRIDGE_TYPE = Registry.register(
            Registries.RECIPE_TYPE, Identifier.of(IceArmor.MOD_ID, "fridge"), new RecipeType<FridgeRecipe>() {
                @Override
                public String toString() {
                    return "fridge";
                }});

    public static void registerRecipes() {
        IceArmor.LOGGER.info("Registering Custom Recipes for " + IceArmor.MOD_ID);
    }
}
