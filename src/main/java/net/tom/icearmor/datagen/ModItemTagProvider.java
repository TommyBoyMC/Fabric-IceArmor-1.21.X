package net.tom.icearmor.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.tom.icearmor.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ItemTags.SWORDS)
                .add(ModItems.ICE_SWORD);
        getOrCreateTagBuilder(ItemTags.PICKAXES)
                .add(ModItems.ICE_PICKAXE);
        getOrCreateTagBuilder(ItemTags.AXES)
                .add(ModItems.ICE_AXE);
        getOrCreateTagBuilder(ItemTags.SHOVELS)
                .add(ModItems.ICE_SHOVEL);

        getOrCreateTagBuilder(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.ICE_HELMET)
                .add(ModItems.ICE_CHESTPLATE)
                .add(ModItems.ICE_LEGGINGS)
                .add(ModItems.ICE_BOOTS);
    }
}
