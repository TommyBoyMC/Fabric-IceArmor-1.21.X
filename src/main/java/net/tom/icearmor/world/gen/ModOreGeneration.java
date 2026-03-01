package net.tom.icearmor.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.tom.icearmor.world.ModPlacedFeatures;

public class ModOreGeneration {
    public static void generateOres() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FROZEN_RIVER, BiomeKeys.SNOWY_PLAINS, BiomeKeys.ICE_SPIKES, BiomeKeys.GROVE
                        , BiomeKeys.FROZEN_PEAKS, BiomeKeys.JAGGED_PEAKS, BiomeKeys.SNOWY_SLOPES, BiomeKeys.SNOWY_TAIGA, BiomeKeys.SNOWY_BEACH
                        , BiomeKeys.FROZEN_OCEAN, BiomeKeys.DEEP_FROZEN_OCEAN, BiomeKeys.WINDSWEPT_HILLS, BiomeKeys.WINDSWEPT_GRAVELLY_HILLS
                        , BiomeKeys.WINDSWEPT_FOREST)
                , GenerationStep.Feature.UNDERGROUND_ORES,
                ModPlacedFeatures.ICE_ORE_PLACED_KEY);
    }
}
