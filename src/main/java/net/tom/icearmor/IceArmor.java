package net.tom.icearmor;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.tom.icearmor.block.ModBlocks;
import net.tom.icearmor.block.entity.ModBlockEntities;
import net.tom.icearmor.effect.ModEffects;
import net.tom.icearmor.item.IceSwordItem;
import net.tom.icearmor.item.ModItems;
import net.tom.icearmor.recipe.ModRecipes;
import net.tom.icearmor.screen.ModScreenHandlers;
import net.tom.icearmor.villager.ModVillagers;
import net.tom.icearmor.world.gen.ModWorldGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IceArmor implements ModInitializer {
	public static final String MOD_ID = "icearmor";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBLocks();
		ModEffects.registerEffects();

		ModWorldGeneration.generateModWorldGen();

		ModBlockEntities.registerBlockEntities();
		ModScreenHandlers.registerScreenHandlers();

		ModRecipes.registerRecipes();

		ModVillagers.registerVillagers();

		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			IceSwordItem.applyCooldown(player, entity);

			return ActionResult.PASS;
		});

		UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			IceSwordItem.applyFreezingEffect(player, entity);

			return ActionResult.PASS;
		});

		TradeOfferHelper.registerVillagerOffers(ModVillagers.ICEKEEPER, 1, factories -> {
			factories.add((entity, random) -> new TradeOffer(
					new TradedItem(ModItems.ICE_NUGGET, 4),
					new ItemStack(Items.EMERALD, 1), 50, 1, 0.02f));
			factories.add((entity, random) -> new TradeOffer(
					new TradedItem(Items.EMERALD, 3),
					new ItemStack(ModBlocks.ICE_ORE, 1), 12, 3, 0.04f));
			factories.add((entity, random) -> new TradeOffer(
					new TradedItem(Items.EMERALD, 5),
					new ItemStack(ModBlocks.DEEPSLATE_ICE_ORE, 1), 12, 3, 0.04f));
		});

		TradeOfferHelper.registerVillagerOffers(ModVillagers.ICEKEEPER, 2, factories -> {
			factories.add((entity, random) -> new TradeOffer(
					new TradedItem(Items.EMERALD, 3),
					new ItemStack(Items.ICE, 1), 25, 5, 0.04f));
			factories.add((entity, random) -> new TradeOffer(
					new TradedItem(Items.EMERALD, 6),
					new ItemStack(Items.PACKED_ICE, 1), 25, 5, 0.04f));
			factories.add((entity, random) -> new TradeOffer(
					new TradedItem(Items.EMERALD, 9),
					new ItemStack(Items.BLUE_ICE, 1), 25, 5, 0.04f));
		});

		TradeOfferHelper.registerVillagerOffers(ModVillagers.ICEKEEPER, 3, factories -> {
			factories.add((entity, random) -> new TradeOffer(
					new TradedItem(Items.EMERALD, 13),
					new ItemStack(ModItems.ICE_BOOTS, 1), 4, 10, 0.04f));
			factories.add((entity, random) -> new TradeOffer(
					new TradedItem(Items.EMERALD, 17),
					new ItemStack(ModItems.ICE_LEGGINGS, 1), 4, 10, 0.04f));
			factories.add((entity, random) -> new TradeOffer(
					new TradedItem(Items.EMERALD, 24),
					new ItemStack(ModItems.ICE_CHESTPLATE, 1), 4, 10, 0.04f));
			factories.add((entity, random) -> new TradeOffer(
					new TradedItem(Items.EMERALD, 15),
					new ItemStack(ModItems.ICE_HELMET, 1), 4, 10, 0.04f));
		});

		TradeOfferHelper.registerVillagerOffers(ModVillagers.ICEKEEPER, 4, factories -> {
			factories.add((entity, random) -> new TradeOffer(
					new TradedItem(Items.EMERALD, 13),
					new ItemStack(ModItems.ICE_SWORD, 1), 4, 10, 0.04f));
			factories.add((entity, random) -> new TradeOffer(
					new TradedItem(Items.EMERALD, 17),
					new ItemStack(ModItems.ICE_SHOVEL, 1), 4, 10, 0.04f));
			factories.add((entity, random) -> new TradeOffer(
					new TradedItem(Items.EMERALD, 24),
					new ItemStack(ModItems.ICE_AXE, 1), 4, 10, 0.04f));
			factories.add((entity, random) -> new TradeOffer(
					new TradedItem(Items.EMERALD, 15),
					new ItemStack(ModItems.ICE_PICKAXE, 1), 4, 10, 0.04f));
		});

		TradeOfferHelper.registerVillagerOffers(ModVillagers.ICEKEEPER, 5, factories -> {
			factories.add((entity, random) -> new TradeOffer(
					new TradedItem(Items.EMERALD, 64),
					new ItemStack(ModBlocks.FRIDGE, 1), 1, 5, 1f));
		});
	}
}