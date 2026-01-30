package net.tom.icearmor.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.tom.icearmor.IceArmor;

public class ModEffects {
    public static final RegistryEntry<StatusEffect> FREEZING = registerStatusEffect("freezing",
            new FreezingEffect(StatusEffectCategory.HARMFUL, 0x9ec0fe)
                    .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,
                            Identifier.of(IceArmor.MOD_ID, "freezing_movement"), -1.0f,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(EntityAttributes.GENERIC_JUMP_STRENGTH,
                            Identifier.of(IceArmor.MOD_ID, "freezing_jumping"), -1.0f,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE,
                            Identifier.of(IceArmor.MOD_ID, "freezing_attack"), -1.0f,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE,
                            Identifier.of(IceArmor.MOD_ID, "freezing_knockback"), 10.0f,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    private static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(IceArmor.MOD_ID, name), statusEffect);
    }

    public static void registerEffects() {
        IceArmor.LOGGER.info("Registering Mod Effect for " + IceArmor.MOD_ID);
    }
}
