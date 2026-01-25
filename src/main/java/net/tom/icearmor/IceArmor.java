package net.tom.icearmor;

import net.fabricmc.api.ModInitializer;

import net.tom.icearmor.block.ModBlocks;
import net.tom.icearmor.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IceArmor implements ModInitializer {
	public static final String MOD_ID = "icearmor";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBLocks();
	}
}