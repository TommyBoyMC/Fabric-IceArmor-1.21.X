package net.tom.icearmor;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.util.ActionResult;
import net.tom.icearmor.block.ModBlocks;
import net.tom.icearmor.block.entity.ModBlockEntities;
import net.tom.icearmor.effect.ModEffects;
import net.tom.icearmor.item.IceSwordItem;
import net.tom.icearmor.item.ModItems;
import net.tom.icearmor.recipe.ModRecipes;
import net.tom.icearmor.screen.ModScreenHandlers;
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

		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			IceSwordItem.applyCooldown(player, entity);

			return ActionResult.PASS;
		});

		UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			IceSwordItem.applyFreezingEffect(player, entity);

			return ActionResult.PASS;
		});
	}
}