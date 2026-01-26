package net.tom.icearmor.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.tom.icearmor.block.ModBlocks;
import net.tom.icearmor.item.ModItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        List<ItemConvertible> ICE_INGOT_SMELTABLES = List.of(ModItems.ICE_CLUMP, ModBlocks.ICE_ORE, ModBlocks.DEEPSLATE_ICE_ORE);

        offerSmelting(exporter, ICE_INGOT_SMELTABLES, RecipeCategory.MISC, ModItems.ICE_INGOT, 0.25f, 200, "ice_ingot");
        offerBlasting(exporter, ICE_INGOT_SMELTABLES, RecipeCategory.MISC, ModItems.ICE_INGOT, 0.25f, 100, "ice_ingot");

        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.ICE_NUGGET, RecipeCategory.MISC, ModItems.ICE_INGOT);

        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.ICE_APPLE)
                .pattern("RRR")
                .pattern("R#R")
                .pattern("RRR")
                .input('R', ModItems.ICE_INGOT)
                .input('#', Items.APPLE)
                .criterion(hasItem(ModItems.ICE_INGOT), conditionsFromItem(ModItems.ICE_INGOT))
                .offerTo(exporter);

    }
}
