package net.tom.icearmor.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.tom.icearmor.IceArmor;
import net.tom.icearmor.screen.custom.FridgeScreenHandler;

public class ModScreenHandlers {
    public static final ScreenHandlerType<FridgeScreenHandler> FRIDGE_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(IceArmor.MOD_ID, "fridge_screen_handler"),
                    new ExtendedScreenHandlerType<>(FridgeScreenHandler::new, BlockPos.PACKET_CODEC));

    public static void registerScreenHandlers() {
        IceArmor.LOGGER.info("Registering Screen Handlers for " + IceArmor.MOD_ID);
    }
}
