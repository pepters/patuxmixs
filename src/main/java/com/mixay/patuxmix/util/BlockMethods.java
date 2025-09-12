package com.mixay.patuxmix.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.concurrent.ThreadLocalRandom;

public class BlockMethods {
    public static void setFlowersInRadius (Location center, Integer radius) {
        Material[] flowers = {Material.SUNFLOWER, Material.POPPY, Material.ALLIUM, Material.DANDELION, Material.RED_TULIP, Material.OXEYE_DAISY, Material.LILY_OF_THE_VALLEY, Material.AZURE_BLUET};
        Block centblock = center.getBlock();
        for(int x = -radius; x <= radius; x++) {
            for(int y = -radius; y <= radius; y++) {
                for(int z = -radius; z <= radius; z++) {
                    Block b = centblock.getRelative(x, y, z);
                    if(centblock.getLocation().distance(b.getLocation()) <= radius) {
                        if (b.getRelative(BlockFace.DOWN).getType() == Material.DIRT || b.getRelative(BlockFace.DOWN).getType() == Material.GRASS_BLOCK) {
                            b.setType(flowers[ThreadLocalRandom.current().nextInt(flowers.length)]);
                        }
                    }
                }
            }
        }
    }
    public static void breakBlocksInRadius (Block center, Integer radius, ItemStack tool, PlayerInventory inv) {
        short durabilityloss = 0;
        int currdur = tool.getType().getMaxDurability() - ((Damageable) tool.getItemMeta()).getDamage();
        int unbreakinglvl = tool.getEnchantmentLevel(Enchantment.DURABILITY) + 2;
        for(int x = -radius; x <= radius; x++) {
            if (durabilityloss < currdur) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        Block b = center.getRelative(x, y, z);
                        if (!b.getType().isAir() && b.isPreferredTool(tool) && b.isValidTool(tool)) {
                            b.breakNaturally(tool, false);
                            if (ThreadLocalRandom.current().nextInt(unbreakinglvl) == 0 || unbreakinglvl == 2) {
                                durabilityloss++;
                            }
                        }
                    }
                }
            } else {
                break;
            }
        }
            // заменяем тул и делаем все шо нада
            ItemMeta meta = tool.getItemMeta();
            ((Damageable) meta).setDamage(((Damageable) meta).getDamage() + durabilityloss);
            tool.setItemMeta(meta);
            if (((Damageable) meta).getDamage() >= tool.getType().getMaxDurability()) {
                inv.setItemInMainHand(new ItemStack(Material.AIR));
            } else {
                inv.setItemInMainHand(tool);
            }

    }
}
