package com.mixay.patuxmix.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import com.mixay.patuxmix.util.*;

public class getPearlCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.getInventory().firstEmpty() != -1) {
                p.getInventory().addItem(itemmethods.getPearlStack());
                p.sendMessage(ChatColor.GREEN + "Успешное получение умного жемчуга края!");
            } else {
                sender.sendMessage(ChatColor.RED + "У вас нет свободного места для получения предмета!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "This is player only command!");
        }
        return false;
    }
}
