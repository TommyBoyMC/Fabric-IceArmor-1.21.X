package net.tom.icearmor.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import net.tom.icearmor.effect.ModEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class OverlayFreezing {

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(
            method = "render",
            at = @At("TAIL")
    )
    private void renderFreezingOverlay(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {

        if (client.player == null) return;

        if (client.player.hasStatusEffect(ModEffects.FREEZING)) {
            renderPowderSnowOverlay(context);
        }
    }

    @Unique
    private void renderPowderSnowOverlay(DrawContext context) {
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        context.drawTexture(
                Identifier.of("minecraft", "textures/misc/powder_snow_outline.png"),
                0, 0,
                0, 0,
                width, height,
                width, height
        );
    }
}