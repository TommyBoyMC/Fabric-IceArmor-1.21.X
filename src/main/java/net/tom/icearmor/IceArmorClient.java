package net.tom.icearmor;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.tom.icearmor.screen.ModScreenHandlers;
import net.tom.icearmor.screen.custom.DoubleFridgeScreen;
import net.tom.icearmor.screen.custom.FridgeScreen;

public class IceArmorClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.FRIDGE_SCREEN_HANDLER, FridgeScreen::new);
        HandledScreens.register(ModScreenHandlers.DOUBLE_FRIDGE_SCREEN_HANDLER, DoubleFridgeScreen::new);
    }
}
