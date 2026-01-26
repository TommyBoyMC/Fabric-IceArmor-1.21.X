package net.tom.icearmor.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.tom.icearmor.IceArmor;

public class ModItems {
    public static final Item ICE_CLUMP = registerItem("ice_clump", new Item(new Item.Settings()));
    public static final Item ICE_INGOT = registerItem("ice_ingot", new Item(new Item.Settings()));
    public static final Item ICE_NUGGET = registerItem("ice_nugget", new Item(new Item.Settings()));

    public static final Item ICE_APPLE = registerItem("ice_apple", new Item(new Item.Settings().food(ModFoodComponent.ICE_APPLE)));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(IceArmor.MOD_ID, name), item);
    }

    public static void registerModItems() {
        IceArmor.LOGGER.info("Registering mod items for " + IceArmor.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(ICE_NUGGET);
            entries.add(ICE_CLUMP);
            entries.add(ICE_INGOT);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            entries.add(ICE_APPLE);
        });
    }
}