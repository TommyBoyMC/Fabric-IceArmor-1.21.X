package net.tom.icearmor.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.Identifier;
import net.tom.icearmor.block.ModBlocks;
import net.tom.icearmor.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.ICE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_ICE_ORE);

        blockStateModelGenerator.registerParentedItemModel(ModBlocks.ICE_ORE,
                Identifier.of("icearmor:block/ice_ore"));
        blockStateModelGenerator.registerParentedItemModel(ModBlocks.DEEPSLATE_ICE_ORE,
                Identifier.of("icearmor:block/deepslate_ice_ore"));
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.ICE_CLUMP, Models.GENERATED);
        itemModelGenerator.register(ModItems.ICE_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.ICE_NUGGET, Models.GENERATED);
        itemModelGenerator.register(ModItems.ICE_APPLE, Models.GENERATED);
        itemModelGenerator.register(ModItems.ICE_CARROT, Models.GENERATED);

        itemModelGenerator.register(ModItems.ICE_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.ICE_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.ICE_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.ICE_SHOVEL, Models.HANDHELD);

        itemModelGenerator.registerArmor((ArmorItem) ModItems.ICE_HELMET);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.ICE_CHESTPLATE);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.ICE_LEGGINGS);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.ICE_BOOTS);
    }
}
