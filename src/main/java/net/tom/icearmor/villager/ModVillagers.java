package net.tom.icearmor.villager;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;
import net.tom.icearmor.IceArmor;
import net.tom.icearmor.block.ModBlocks;

public class ModVillagers {
    public static final RegistryKey<PointOfInterestType> ICEKEEPER_POI_KEY = registerPoiKey("icekeeper_poi");
    public static final PointOfInterestType ICEKEEPER_POI = registerPOI("icekeeper_poi", ModBlocks.FRIDGE);

    public static final VillagerProfession ICEKEEPER = registerProfession("icekeeper", ICEKEEPER_POI_KEY);

    private static VillagerProfession registerProfession(String name, RegistryKey<PointOfInterestType> type) {
        return Registry.register(Registries.VILLAGER_PROFESSION, Identifier.of(IceArmor.MOD_ID, name),
                new VillagerProfession(name, entry -> entry.matchesKey(type), entry -> entry.matchesKey(type),
                        ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ENTITY_VILLAGER_WORK_FLETCHER));
    }

    private static PointOfInterestType registerPOI(String name, Block block) {
        return PointOfInterestHelper.register(Identifier.of(IceArmor.MOD_ID, name),
                1, 1, block);
    }

    private static RegistryKey<PointOfInterestType> registerPoiKey(String name) {
        return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, Identifier.of(IceArmor.MOD_ID, name));
    }

    public static void registerVillagers() {
        IceArmor.LOGGER.info("Registering Mod Villagers for " + IceArmor.MOD_ID);
    }
}
