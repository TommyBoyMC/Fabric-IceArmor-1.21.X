package net.tom.icearmor.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.tom.icearmor.world.ModConfiguredFeatures;
import net.tom.icearmor.world.ModPlacedFeatures;

import java.util.concurrent.CompletableFuture;

public class ModRegistryDataGenerator extends FabricDynamicRegistryProvider {
    public ModRegistryDataGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        var configuredFeatures =
                registries.getWrapperOrThrow(RegistryKeys.CONFIGURED_FEATURE);

        ModConfiguredFeatures.ALL.forEach(key ->
                entries.add(configuredFeatures.getOrThrow(key))
        );

        var placedFeatures =
                registries.getWrapperOrThrow(RegistryKeys.PLACED_FEATURE);

        ModPlacedFeatures.ALL.forEach(key ->
                entries.add(placedFeatures.getOrThrow(key))
        );
    }

    @Override
    public String getName() {
        return "";
    }
}
