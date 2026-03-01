package net.tom.icearmor.block.entity;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.tom.icearmor.IceArmor;
import net.tom.icearmor.block.ModBlocks;
import net.tom.icearmor.block.entity.custom.FridgeBlockEntity;

public class ModBlockEntities {
    public static final BlockEntityType<FridgeBlockEntity>  FRIDGE_BE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(IceArmor.MOD_ID, "fridge_be"),
                    BlockEntityType.Builder.create(FridgeBlockEntity::new, ModBlocks.FRIDGE).build(null));

    public static void registerBlockEntities() {
        IceArmor.LOGGER.info("Registering Block Entities for " + IceArmor.MOD_ID);
    }
}
