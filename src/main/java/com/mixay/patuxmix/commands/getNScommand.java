package com.mixay.patuxmix.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class getNScommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            ItemStack shovel = new ItemStack(Material.NETHERITE_SHOVEL, 1);
            if (p.getInventory().firstEmpty() != -1) {
                p.getInventory().addItem(shovel);
                p.sendMessage(ChatColor.translateAlternateColorCodes( '&', "&aУспешное получение"));
            } else {
                p.sendMessage(ChatColor.RED + "У вас нет свободного места для получения лопаты!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "This is player only command!");
        }
        return false;
    }
}
