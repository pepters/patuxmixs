package com.mixay.patuxmix.listeners;

import com.mixay.patuxmix.Patuxmix;
import de.tr7zw.nbtapi.NBT;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.text.MessageFormat;
import java.util.Arrays;

public class EnderPearlHandler implements Listener {
    //Logger logger = Bukkit.getLogger();
    Patuxmix plugin;
    FileConfiguration config;
    public EnderPearlHandler (Patuxmix pl) {
        this.plugin = pl;
        this.config = plugin.getConfig();
    }
    @EventHandler (ignoreCancelled = true)
    public void onPearlBlockSet (PlayerInteractEvent e) {
        if (e.getHand() == EquipmentSlot.HAND && e.getItem() != null && e.getAction().equals(Action.LEFT_CLICK_BLOCK) && e.getClickedBlock()!=null) {
            if (e.getItem().getType() == Material.ENDER_PEARL) {
                // знаем что перл, сохраняем локацию проверяя есть ли нбт тег
                ItemStack pearl = e.getItem();
                Player p = e.getPlayer();
                Block b = e.getClickedBlock();
                boolean isnoloc = NBT.get(pearl, nbt -> (Boolean) nbt.hasTag("patuxmix:PearlNoLoc"));
                if (isnoloc) {
                    // уверены что у жемчуга нет заданной локации и соответственно что он с плагина и можно записать новую
                    //e.setCancelled(true);
                    Location newloc = b.getLocation();
                    newloc.setY(newloc.getY()+1);
                    // записываем новую локацию в нбт
                    NBT.modify(pearl, nbt -> {
                        nbt.setInteger("patuxmix:Xtploc", newloc.getBlockX());
                        nbt.setInteger("patuxmix:Ytploc", newloc.getBlockY());
                        nbt.setInteger("patuxmix:Ztploc", newloc.getBlockZ());
                        nbt.setString("patuxmix:Wtploc", newloc.getWorld().getName());
                        nbt.removeKey("patuxmix:PearlNoLoc");
                        nbt.setBoolean("patuxmix:locready", true);
                    });
                    String plore2 = ChatColor.GREEN + Integer.toString(newloc.getBlockX()) + " " + newloc.getBlockY() + " " + newloc.getBlockZ();
                    pearl.lore(Arrays.asList(Component.text(ChatColor.WHITE + "Этот жемчуг телепортирует на координаты:"), Component.text(plore2)));
                    p.getInventory().setItemInMainHand(pearl);
                    p.sendMessage(ChatColor.GREEN + "Локация была успешно задана");
                }
            }
        }
    }
    @EventHandler
    public void onPearlUse (PlayerInteractEvent e) {
        if (e.getHand() == EquipmentSlot.HAND && e.getItem() != null && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            if (e.getItem().getType() == Material.ENDER_PEARL) {
                ItemStack pearl = e.getItem();
                Player p = e.getPlayer();
                boolean locinitem = NBT.get(pearl, nbt -> (Boolean) nbt.getOrDefault("patuxmix:locready", false));
                if (NBT.get(pearl, nbt -> (Boolean) nbt.getOrDefault("patuxmix:tppearl", false))) {
                    if (locinitem) {
                        // нбт локации есть в предмете, создаем и тпхаем
                        Integer x = NBT.get(pearl, nbt -> (Integer) nbt.getInteger("patuxmix:Xtploc"));
                        Integer y = NBT.get(pearl, nbt -> (Integer) nbt.getInteger("patuxmix:Ytploc"));
                        Integer z = NBT.get(pearl, nbt -> (Integer) nbt.getInteger("patuxmix:Ztploc"));
                        World w = Bukkit.getWorld((String) NBT.get(pearl, nbt -> (String) nbt.getString("patuxmix:Wtploc")));
                        Location tploc = new Location(w, x, y, z);
                        int distance;
                        try {
                            distance = (int) Math.round(tploc.distance(p.getLocation()));
                            if (distance <= config.getInt("smart-pearl.maxdistance")) {
                                p.teleport(tploc);
                                p.getInventory().remove(pearl);
                                p.sendMessage(ChatColor.GREEN + "Вы были успешно телепортированы по сохраненной локации!");
                            } else {
                                p.sendMessage(ChatColor.RED + MessageFormat.format("Расстояние до сохраненной точки больше ({0}), чем максимальное ({1}), телепортация невозможна!", distance, config.getInt("smart-pearl.maxdistance")));
                            }
                        } catch (IllegalArgumentException error) {
                            p.sendMessage(ChatColor.RED + "Вы находитесь в другом мире, нельзя телепортироватся между мирами!");
                        }
                    }
                    e.setCancelled(true);
                }
            }
        }
    }
}
