package net.tom.icearmor.item;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class ModFoodComponent {
    public static final FoodComponent ICE_APPLE = new FoodComponent.Builder().nutrition(4).saturationModifier(1.2f)
            .statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 1),1.0f)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 250, 1), 1.0f)
            .statusEffect(new StatusEffectInstance(StatusEffects.HASTE, 250, 2), 0.4f)
            .statusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 2400), 0.1f)
            .alwaysEdible().build();
}
