package net.tom.icearmor.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.tom.icearmor.IceArmor;
import net.tom.icearmor.item.custom.ModArmorItem;

public class ModItems {
    public static final Item ICE_CLUMP = registerItem("ice_clump", new Item(new Item.Settings()));
    public static final Item ICE_INGOT = registerItem("ice_ingot", new Item(new Item.Settings()));
    public static final Item ICE_NUGGET = registerItem("ice_nugget", new Item(new Item.Settings()));

    public static final Item ICE_APPLE = registerItem("ice_apple", new Item(new Item.Settings().food(ModFoodComponent.ICE_APPLE)));

    public static final Item ICE_SWORD = registerItem("ice_sword",
            new SwordItem(ModToolMaterials.ICE, new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(ModToolMaterials.ICE, 4, -2.4f))));
    public static final Item ICE_AXE = registerItem("ice_axe",
            new AxeItem(ModToolMaterials.ICE, new Item.Settings()
                    .attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterials.ICE, 6, -3.2f))));
    public static final Item ICE_PICKAXE = registerItem("ice_pickaxe",
            new PickaxeItem(ModToolMaterials.ICE, new Item.Settings()
                    .attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.ICE, 1, -2.8f))));
    public static final Item ICE_SHOVEL = registerItem("ice_shovel",
            new ShovelItem(ModToolMaterials.ICE, new Item.Settings()
                    .attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterials.ICE, 1.5f, -3.0f))));

    public static final Item ICE_HELMET = registerItem("ice_helmet",
            new ModArmorItem(ModArmorMaterial.ICE_INGOT_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Settings()
                    .maxDamage(ArmorItem.Type.HELMET.getMaxDamage(34))));
    public static final Item ICE_CHESTPLATE = registerItem("ice_chestplate",
            new ArmorItem(ModArmorMaterial.ICE_INGOT_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(34))));
    public static final Item ICE_LEGGINGS = registerItem("ice_leggings",
            new ArmorItem(ModArmorMaterial.ICE_INGOT_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(34))));
    public static final Item ICE_BOOTS = registerItem("ice_boots",
            new ArmorItem(ModArmorMaterial.ICE_INGOT_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(34))));



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

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(ICE_SWORD);
            entries.add(ICE_AXE);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.add(ICE_PICKAXE);
            entries.add(ICE_SHOVEL);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(ICE_HELMET);
            entries.add(ICE_CHESTPLATE);
            entries.add(ICE_LEGGINGS);
            entries.add(ICE_BOOTS);
        });
    }
}