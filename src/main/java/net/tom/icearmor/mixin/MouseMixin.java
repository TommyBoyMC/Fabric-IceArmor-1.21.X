package net.tom.icearmor.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.tom.icearmor.effect.ModEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public abstract class MouseMixin {

    @Shadow @Final
    private MinecraftClient client;

    @Inject(
            method = "updateMouse",
            at = @At("HEAD"),
            cancellable = true
    )
    private void freezeCamera(double timeDelta, CallbackInfo ci) {

        if (client.player == null) return;

        if (client.player.hasStatusEffect(ModEffects.FREEZING)) {
            ci.cancel();
        }
    }
}