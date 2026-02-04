package net.tom.icearmor.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.tom.icearmor.IceArmor;
import net.tom.icearmor.effect.ModEffects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class IceSwordItem extends SwordItem {

    private static final Map<UUID, Integer> HIT_COUNTS = new HashMap<>();
    private static final int REQUIRED_HITS = 10;

    public IceSwordItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    public static void applyCooldown(PlayerEntity player, Entity entity) {
        if (player.getWorld().isClient) return;

        switch (entity) {
            case PlayerEntity hitEntity -> {}
            case HostileEntity hitEntity -> {}
            case IronGolemEntity hitEntity -> {}
            default -> {
                return;
            }
        }

        boolean isCrit = player.fallDistance > 0.0F
                        && !player.isOnGround()
                        && !player.isClimbing()
                        && !player.isTouchingWater()
                        && !player.hasVehicle()
                        && !player.isFallFlying()
                        && !player.hasStatusEffect(StatusEffects.BLINDNESS);

        int hitCount = getHitCount(player);

        if (isCrit) {
            hitCount++;
        } else {
            return;
        }

        setHitCount(player, hitCount);

        IceArmor.LOGGER.info("HitCount (" + player.getName().getString() + ") = " + hitCount);

        if (hitCount < REQUIRED_HITS) {
            int hitsLeft = REQUIRED_HITS - hitCount;

            player.sendMessage(
                    Text.literal("❄ You need " + hitsLeft + " more hits for the ability ❄")
                            .styled(style -> style.withColor(0x9EC0FE)),
                    true
            );
        } else if (hitCount == 10){
            player.sendMessage(
                    Text.literal("❄ Your ability is ready ❄")
                            .styled(style -> style.withColor(0x9EC0FE)),
                    true
            );
        }
    }

    public static void applyFreezingEffect(PlayerEntity player, Entity entity) {
        if (player.getWorld().isClient) return;

        int hitCount = getHitCount(player);

        if (hitCount >= 10) {
            if (player.getMainHandStack().getItem() == ModItems.ICE_SWORD) {
                if (entity instanceof PlayerEntity hitEntity) {
                    hitEntity.addStatusEffect(new StatusEffectInstance(ModEffects.FREEZING, 60));
                    hitEntity.sendMessage(Text.literal("❄ You are Frozen ❄")
                            .styled(style -> style.withColor(0x9EC0FE)), true);
                } else if (entity instanceof ZombieEntity hitEntity) {
                    hitEntity.addStatusEffect(new StatusEffectInstance(ModEffects.FREEZING, 100));
                } else if (entity instanceof SkeletonEntity hitEntity) {
                    hitEntity.addStatusEffect(new StatusEffectInstance(ModEffects.FREEZING, 100));
                } else if (entity instanceof CreeperEntity hitEntity) {
                    hitEntity.addStatusEffect(new StatusEffectInstance(ModEffects.FREEZING, 100));
                } else if (entity instanceof SpiderEntity hitEntity) {
                    hitEntity.addStatusEffect(new StatusEffectInstance(ModEffects.FREEZING, 100));
                } else if (entity instanceof EndermanEntity hitEntity) {
                    hitEntity.addStatusEffect(new StatusEffectInstance(ModEffects.FREEZING, 100));
                } else if (entity instanceof HuskEntity hitEntity) {
                    hitEntity.addStatusEffect(new StatusEffectInstance(ModEffects.FREEZING, 100));
                } else if (entity instanceof StrayEntity hitEntity) {
                    hitEntity.addStatusEffect(new StatusEffectInstance(ModEffects.FREEZING, 100));
                } else if (entity instanceof BoggedEntity hitEntity) {
                    hitEntity.addStatusEffect(new StatusEffectInstance(ModEffects.FREEZING, 100));
                } else if (entity instanceof IronGolemEntity hitEntity) {
                    hitEntity.addStatusEffect(new StatusEffectInstance(ModEffects.FREEZING, 100));
                }
                player.sendMessage(Text.literal("❄ You used your ability ❄")
                        .styled(style -> style
                                .withColor(0x9EC0FE)), true);
                setHitCount(player, 0);
            }
        }
    }
    private static int getHitCount(PlayerEntity player) {
        return HIT_COUNTS.getOrDefault(player.getUuid(), 10);
    }

    private static void setHitCount(PlayerEntity player, int value) {
        HIT_COUNTS.put(player.getUuid(), value);
    }
}
