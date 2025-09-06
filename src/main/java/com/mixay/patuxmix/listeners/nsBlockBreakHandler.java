package com.mixay.patuxmix.listeners;

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
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.MessageFormat;

public class nsBlockBreakHandler implements Listener {
    @EventHandler
    public void onNSBlockBreak (BlockBreakEvent e) {
        Player p = e.getPlayer();
        ItemStack breakitem = p.getInventory().getItemInMainHand();
        Block b = e.getBlock();
        int breakradius = NBT.get(breakitem, nbt -> (Integer) nbt.getOrDefault("NSradius", 0));
        if (breakradius != 0 && breakitem.getType() == Material.NETHERITE_SHOVEL && b.isValidTool(breakitem) && b.isPreferredTool(breakitem)) {
            // ломаем блоки 3х3
            Block center = e.getBlock().getRelative(BlockFace.SELF);
            ItemMeta meta = breakitem.getItemMeta();
            //Bukkit.getLogger().info(MessageFormat.format("changing durability, current {0}, damage {1}", ((Damageable) meta).getDamage(), (blockmethods.breakBlocksInRadius(center, 2, breakitem, false))));
            ((Damageable) meta).setDamage(((Damageable) meta).getDamage() + blockmethods.breakBlocksInRadius(center, breakradius, breakitem));
            breakitem.setItemMeta(meta);
            if (((Damageable) meta).getDamage() > breakitem.getType().getMaxDurability()) {
                p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            } else {
                p.getInventory().setItemInMainHand(breakitem);
            }
        }
    }
}
