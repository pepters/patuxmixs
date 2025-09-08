package com.mixay.patuxmix.commands;

import com.mixay.patuxmix.Patuxmix;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import com.mixay.patuxmix.util.*;

public class GetSmartPearl implements CommandExecutor {
    public Patuxmix plugin;
    private FileConfiguration config;
    public GetSmartPearl (Patuxmix pl) {
        this.plugin = pl;
        this.config = plugin.getConfig();
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("patuxmix.getpearl")) {
                if (p.getInventory().firstEmpty() != -1) {
                    p.getInventory().addItem(ItemMethods.getPearlStack());
                    p.sendMessage(ChatColor.GREEN + "Успешное получение умного жемчуга края!");
                } else {
                    sender.sendMessage(ChatColor.RED + "У вас нет свободного места для получения предмета!");
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + "This is player only command!");
        }
        return false;
    }
}
