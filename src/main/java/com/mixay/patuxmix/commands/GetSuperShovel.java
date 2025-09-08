package com.mixay.patuxmix.commands;

import com.mixay.patuxmix.Patuxmix;
import com.mixay.patuxmix.util.*;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


import java.text.MessageFormat;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import net.jodah.expiringmap.ExpiringMap;

public class GetSuperShovel implements CommandExecutor {
    public Patuxmix pl;
    private FileConfiguration config;
    Map <UUID, Long> cooldowns;
    public GetSuperShovel (Patuxmix plugin) {
        this.pl = plugin;
        this.config = this.pl.getConfig();
        cooldowns = ExpiringMap.builder().expiration(config.getInt("super-shovel.cooldown"), TimeUnit.SECONDS).build();
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("patuxmix.getshovel")) {
                if (!cooldowns.containsKey(p.getUniqueId())) {
                    int breakradius = 1;
                    if (args.length > 0) {
                        try {
                            if (Integer.parseInt(args[0]) > 0 && Integer.parseInt(args[0]) <= config.getInt("super-shovel.maxradius")) {
                                breakradius = Integer.parseInt(args[0]);
                            } else {
                                p.sendMessage(ChatColor.RED + MessageFormat.format("Неверное значение радиуса, допустимые: от 1 до {0}", config.getInt("super-shovel.maxradius")));
                                return false;
                            }
                        } catch (NumberFormatException | NullPointerException e) {
                            p.sendMessage(ChatColor.RED + "Неверный формат радиуса!");
                            return false;
                        }
                    }
                    if (p.getInventory().firstEmpty() != -1) {
                        p.getInventory().addItem(ItemMethods.getPickaxeStack(breakradius));
                        p.sendMessage(MessageFormat.format(ChatColor.GREEN + "Успешное получение лопаты которая копает радиусом {0}", breakradius));
                    } else {
                        p.sendMessage(ChatColor.RED + "У вас нет свободного места для получения лопаты!");
                    }
                    cooldowns.put(p.getUniqueId(), System.currentTimeMillis());
                } else {
                    p.sendMessage(MessageFormat.format(ChatColor.RED + "Вам надо подождить еще {0} секунд для повторного использования команды", TimeUnit.SECONDS.convert(TimeUnit.MILLISECONDS.convert(5, TimeUnit.SECONDS) - (System.currentTimeMillis() - cooldowns.get(p.getUniqueId())), TimeUnit.MILLISECONDS)));
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + "This is player only command!");
        }
        return false;
    }
}
