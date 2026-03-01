package net.tom.icearmor.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.tom.icearmor.item.ModItems;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class IceTotemMixin {

    @SuppressWarnings("unused")
    @Inject(method = "tryUseTotem", at = @At("HEAD"), cancellable = true)
    private void useIceTotem(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity)(Object)this;
        World world = entity.getWorld();

        if (world.isClient) return;

        for (Hand hand : Hand.values()) {
            ItemStack stack = entity.getStackInHand(hand);

            if (stack.isOf(ModItems.ICE_TOTEM)) {

                stack.decrement(1);

                entity.setHealth(1.0F);
                entity.clearStatusEffects();
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));

                world.sendEntityStatus(entity, (byte)35);

                cir.setReturnValue(true);
                return;
            }
        }
    }
}
