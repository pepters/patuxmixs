package com.mixay.patuxmix.commands;

import com.mixay.patuxmix.util.*;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


import java.text.MessageFormat;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import net.jodah.expiringmap.ExpiringMap;

public class getNScommand implements CommandExecutor {
    Map <UUID, Long> cooldowns = ExpiringMap.builder().expiration(5, TimeUnit.SECONDS).build();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!cooldowns.containsKey(p.getUniqueId())) {
                int breakradius = 1;
                if (args.length > 0) {
                    try {
                        if (Integer.parseInt(args[0]) > 0 && Integer.parseInt(args[0]) < 10) {
                            breakradius = Integer.parseInt(args[0]);
                        } else {
                            p.sendMessage(ChatColor.RED + "Неверное значение радиуса, допустимые: от 1 до 9");
                            return false;
                        }
                    } catch (NumberFormatException | NullPointerException e) {
                        p.sendMessage(ChatColor.RED + "Неверный формат радиуса!");
                        return false;
                    }
                }
                    if (p.getInventory().firstEmpty() != -1) {
                        p.getInventory().addItem(itemmethods.getPickaxeStack(breakradius));
                        p.sendMessage(MessageFormat.format(ChatColor.GREEN + "Успешное получение лопаты которая копает радиусом {0}", breakradius));
                    } else {
                        p.sendMessage(ChatColor.RED + "У вас нет свободного места для получения лопаты!");
                    }
                cooldowns.put(p.getUniqueId(), System.currentTimeMillis());
            } else {
                p.sendMessage(MessageFormat.format(ChatColor.RED + "Вам надо подождить еще {0} секунд для повторного использования команды", TimeUnit.SECONDS.convert(TimeUnit.MILLISECONDS.convert(5, TimeUnit.SECONDS) - (System.currentTimeMillis() - cooldowns.get(p.getUniqueId())), TimeUnit.MILLISECONDS)));
            }
        } else {
            sender.sendMessage(ChatColor.RED + "This is player only command!");
        }
        return false;
    }
}
