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
        List<ItemConvertible> WATER_BOTTLE_SMELTABLES = List.of(ModItems.ICE_CLUMP, ModBlocks.ICE_ORE, ModBlocks.DEEPSLATE_ICE_ORE);

        offerSmelting(exporter, WATER_BOTTLE_SMELTABLES, RecipeCategory.MISC, Items.WATER_BUCKET, 0.25f, 200, "ice_ingot");
        offerBlasting(exporter, WATER_BOTTLE_SMELTABLES, RecipeCategory.MISC, Items.WATER_BUCKET, 0.25f, 100, "ice_ingot");

        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, ModItems.ICE_NUGGET, RecipeCategory.MISC, ModItems.ICE_INGOT);

        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.ICE_APPLE)
                .pattern("RRR")
                .pattern("R#R")
                .pattern("RRR")
                .input('R', ModItems.ICE_INGOT)
                .input('#', Items.APPLE)
                .criterion(hasItem(ModItems.ICE_INGOT), conditionsFromItem(ModItems.ICE_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.ICE_SWORD)
                .pattern(" D ")
                .pattern(" D ")
                .pattern(" R ")
                .input('R', Items.STICK)
                .input('D', ModItems.ICE_INGOT)
                .criterion(hasItem(ModItems.ICE_INGOT), conditionsFromItem(ModItems.ICE_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.ICE_AXE)
                .pattern("DD ")
                .pattern("DR ")
                .pattern(" R ")
                .input('R', Items.STICK)
                .input('D', ModItems.ICE_INGOT)
                .criterion(hasItem(ModItems.ICE_INGOT), conditionsFromItem(ModItems.ICE_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.ICE_PICKAXE)
                .pattern("DDD")
                .pattern(" R ")
                .pattern(" R ")
                .input('R', Items.STICK)
                .input('D', ModItems.ICE_INGOT)
                .criterion(hasItem(ModItems.ICE_INGOT), conditionsFromItem(ModItems.ICE_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.ICE_SHOVEL)
                .pattern(" D ")
                .pattern(" R ")
                .pattern(" R ")
                .input('R', Items.STICK)
                .input('D', ModItems.ICE_INGOT)
                .criterion(hasItem(ModItems.ICE_INGOT), conditionsFromItem(ModItems.ICE_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.ICE_HELMET)
                .pattern("DDD")
                .pattern("D D")
                .pattern("   ")
                .input('D', ModItems.ICE_INGOT)
                .criterion(hasItem(ModItems.ICE_INGOT), conditionsFromItem(ModItems.ICE_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.ICE_CHESTPLATE)
                .pattern("D D")
                .pattern("DDD")
                .pattern("DDD")
                .input('D', ModItems.ICE_INGOT)
                .criterion(hasItem(ModItems.ICE_INGOT), conditionsFromItem(ModItems.ICE_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.ICE_LEGGINGS)
                .pattern("DDD")
                .pattern("D D")
                .pattern("D D")
                .input('D', ModItems.ICE_INGOT)
                .criterion(hasItem(ModItems.ICE_INGOT), conditionsFromItem(ModItems.ICE_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.ICE_BOOTS)
                .pattern("   ")
                .pattern("D D")
                .pattern("D D")
                .input('D', ModItems.ICE_INGOT)
                .criterion(hasItem(ModItems.ICE_INGOT), conditionsFromItem(ModItems.ICE_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.FRIDGE)
                .pattern("ABA")
                .pattern("ACA")
                .pattern("ABA")
                .input('A', Items.QUARTZ)
                .input('B', Items.REDSTONE)
                .input('C', Items.PACKED_ICE)
                .criterion(hasItem(Items.PACKED_ICE), conditionsFromItem(Items.PACKED_ICE))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Items.PACKED_ICE)
                .pattern("AA ")
                .pattern("AA ")
                .pattern("   ")
                .input('A', ModItems.ICE_INGOT)
                .criterion(hasItem(ModItems.ICE_INGOT), conditionsFromItem(ModItems.ICE_INGOT))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Items.ICE)
                .pattern("AA ")
                .pattern("AA ")
                .pattern("   ")
                .input('A', ModItems.ICE_NUGGET)
                .criterion(hasItem(ModItems.ICE_NUGGET), conditionsFromItem(ModItems.ICE_NUGGET))
                .offerTo(exporter);

    }
}
