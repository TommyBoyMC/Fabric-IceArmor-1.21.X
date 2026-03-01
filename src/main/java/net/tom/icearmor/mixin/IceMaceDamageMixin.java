package net.tom.icearmor.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.tom.icearmor.IceArmor;
import net.tom.icearmor.item.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class IceMaceDamageMixin {

    private static final List<String> COLD_BIOMES = List.of(
            "minecraft:frozen_river", "minecraft:snowy_plains", "minecraft:ice_spikes",
            "minecraft:grove", "minecraft:frozen_peaks", "minecraft:jagged_peaks",
            "minecraft:snowy_slopes", "minecraft:snowy_taiga", "minecraft:snowy_beach",
            "minecraft:frozen_ocean", "minecraft:deep_frozen_ocean", "minecraft:windswept_hills",
            "minecraft:windswept_gravelly_hills", "minecraft:windswept_forest"
    );

    @ModifyVariable(method = "damage", at = @At("HEAD"), argsOnly = true)
    private float addIceBiomeDamage(float amount, DamageSource source) {
        Entity attacker = source.getAttacker();
        LivingEntity victim = (LivingEntity)(Object)this;

        if (attacker instanceof LivingEntity livingAttacker) {
            if (livingAttacker.getMainHandStack().isOf(ModItems.ICE_MACE)) {

                RegistryEntry<Biome> biome = attacker.getWorld().getBiome(attacker.getBlockPos());
                String biomeIdentifier = biome.getKeyOrValue().map(key -> key.getValue().toString(), value -> "unknown");

                if (COLD_BIOMES.contains(biomeIdentifier)) {
                    float finalDamage = amount + 4.0f;

                    if (!attacker.getWorld().isClient && attacker.getWorld() instanceof ServerWorld serverWorld) {
                        IceArmor.LOGGER.debug("Spawning snowflake particles");
                        serverWorld.spawnParticles(
                                ParticleTypes.SNOWFLAKE,
                                victim.getX(), victim.getY() + 1.0, victim.getZ(),
                                45,
                                0.3, 0.3, 0.3, 
                                0.1
                        );
                    }

                    return finalDamage;
                }
            }
        }
        return amount;
    }
}