package com.mixay.patuxmix.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import java.util.Random;

public class blockmethods {
    public static void setFlowersInRadius (Location center, Integer radius) {
        Random random = new Random();
        Material[] flowers = {Material.SUNFLOWER, Material.POPPY, Material.ALLIUM, Material.DANDELION, Material.RED_TULIP, Material.OXEYE_DAISY, Material.LILY_OF_THE_VALLEY, Material.AZURE_BLUET};
        Block centblock = center.getBlock();
        for(int x = -radius; x <= radius; x++) {
            for(int y = -radius; y <= radius; y++) {
                for(int z = -radius; z <= radius; z++) {
                    Block b = centblock.getRelative(x, y, z);
                    if(centblock.getLocation().distance(b.getLocation()) <= radius) {
                        if (b.getRelative(BlockFace.DOWN).getType() == Material.DIRT || b.getRelative(BlockFace.DOWN).getType() == Material.GRASS_BLOCK) {
                            int randomchoice = random.nextInt(flowers.length);
                            b.setType(flowers[randomchoice]);
                        }
                    }
                }
            }
        }
    }
    public static short breakBlocksInRadius (Block center, Integer radius, ItemStack tool) {
        short durabilityloss = 0;
        for(int x = -radius; x <= radius; x++) {
            for(int y = -radius; y <= radius; y++) {
                for(int z = -radius; z <= radius; z++) {
                    Block b = center.getRelative(x, y, z);
                        if (!b.getType().isAir() && b.isPreferredTool(tool) && b.isValidTool(tool)) {
                            b.breakNaturally(tool, false);
                            durabilityloss++;
                        }
                }
            }
        }
        return durabilityloss;
    }
}
