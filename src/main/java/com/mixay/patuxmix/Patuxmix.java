package com.mixay.patuxmix;

import com.mixay.patuxmix.commands.GetSuperShovel;
import com.mixay.patuxmix.commands.GetSmartPearl;
import com.mixay.patuxmix.listeners.*;
import com.mixay.patuxmix.util.*;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public final class Patuxmix extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new ChickenClickHandler(), this);
        Bukkit.getPluginManager().registerEvents(new SuperShovelBlockBreakHandler(), this);
        Bukkit.getPluginManager().registerEvents(new EnderPearlHandler(this), this);
        Bukkit.getPluginCommand("getshovel").setExecutor(new GetSuperShovel(this));
        Bukkit.getPluginCommand("getpearl").setExecutor(new GetSmartPearl(this));

        ShapedRecipe NSpickrecipie = new ShapedRecipe(new NamespacedKey(this, "NSpickaxer2"), ItemMethods.getPickaxeStack(2));
        NSpickrecipie.shape(" N ", " S ", " S ");
        NSpickrecipie.setIngredient('N', Material.NETHERITE_BLOCK);
        NSpickrecipie.setIngredient('S', Material.STICK);
        Bukkit.addRecipe(NSpickrecipie);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
