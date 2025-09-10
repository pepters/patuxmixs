package com.mixay.patuxmix;

import com.mixay.patuxmix.commands.GetSuperShovel;
import com.mixay.patuxmix.commands.GetSmartPearl;
import com.mixay.patuxmix.listeners.*;
import com.mixay.patuxmix.util.*;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

public final class Patuxmix extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new ChickenClickHandler(), this);
        Bukkit.getPluginManager().registerEvents(new SuperShovelBlockBreakHandler(this), this);
        Bukkit.getPluginManager().registerEvents(new EnderPearlHandler(this), this);
        Bukkit.getPluginManager().registerEvents(new FoodEatHandler(), this);
        Bukkit.getPluginCommand("getshovel").setExecutor(new GetSuperShovel(this));
        Bukkit.getPluginCommand("getpearl").setExecutor(new GetSmartPearl(this));
        ShapedRecipe NSpickrecipie = new ShapedRecipe(new NamespacedKey(this, "NSpickaxer2"), ItemMethods.getPickaxeStack(2));
        NSpickrecipie.shape(" N ", " S ", " S ");
        NSpickrecipie.setIngredient('N', Material.NETHERITE_BLOCK);
        NSpickrecipie.setIngredient('S', Material.STICK);
        Bukkit.addRecipe(NSpickrecipie);
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskTimer(this, new Runnable() {
            @Override
            public void run() {
                //проверяем на шлем и собственна даэ
                Bukkit.getOnlinePlayers().forEach(player -> {
                    if (player.getInventory().getHelmet() != null) {
                        if (player.getInventory().getHelmet().getType().equals(Material.IRON_HELMET)) {
                            // знаем что надет нужный шлем, теперь проверяем на блок
                            Block potolok = player.getWorld().getBlockAt(player.getLocation()).getRelative(BlockFace.UP, 2);
                            if (!potolok.getType().isAir() && potolok.isSolid()) {
                                // все знаем, выдаем собственно невесомость
                                player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 12, 0));
                            }
                        }
                    }
                });
            }
        }, 20L, 10L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
