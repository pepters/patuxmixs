package com.mixay.patuxmix.listeners;

import com.mixay.patuxmix.Patuxmix;
import com.mixay.patuxmix.util.*;
import de.tr7zw.nbtapi.NBT;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

public class SuperShovelBlockBreakHandler implements Listener {
    Patuxmix plugin;
    public SuperShovelBlockBreakHandler (Patuxmix pl) {
        this.plugin = pl;
    }

    @EventHandler (ignoreCancelled = true)
    public void onNSBlockBreak (BlockBreakEvent e) {
        Player p = e.getPlayer();
        ItemStack breakitem = p.getInventory().getItemInMainHand();
        if (breakitem.getType() == Material.NETHERITE_SHOVEL) {
            Block b = e.getBlock();
            int breakradius = NBT.get(breakitem, nbt -> (Integer) nbt.getOrDefault("patuxmix:NSradius", 0));
            if (breakradius != 0 && b.isValidTool(breakitem) && b.isPreferredTool(breakitem)) {
                // ломаем блоки 3х3
                Block center = e.getBlock().getRelative(BlockFace.SELF);
                //Bukkit.getLogger().info(MessageFormat.format("changing durability, current {0}, damage {1}", ((Damageable) meta).getDamage(), (blockmethods.breakBlocksInRadius(center, 2, breakitem, false))));
                BukkitScheduler scheduler = Bukkit.getScheduler();
                scheduler.runTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        BlockMethods.breakBlocksInRadius(center, breakradius, breakitem, p.getInventory());
                    }
                });
            }
        }
    }
}
