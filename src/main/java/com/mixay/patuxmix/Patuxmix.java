package com.mixay.patuxmix;

import com.mixay.patuxmix.commands.getNScommand;
import com.mixay.patuxmix.listeners.*;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Patuxmix extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(new chickenClickHandler(), this);
        Bukkit.getPluginManager().registerEvents(new nsBlockBreakHandler(), this);
        Bukkit.getPluginCommand("GetShovel").setExecutor(new getNScommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
