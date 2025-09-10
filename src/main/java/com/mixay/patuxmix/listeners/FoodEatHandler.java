package com.mixay.patuxmix.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ThreadLocalRandom;

public class FoodEatHandler implements Listener {
    @EventHandler (ignoreCancelled = true)
    public void onFoodEat (PlayerItemConsumeEvent e) {
        if (e.getItem().getType().isEdible()) {
                // знаем что предмет - еда
                int rndeffect = ThreadLocalRandom.current().nextInt(PotionEffectType.values().length);
                Player p = e.getPlayer();
                p.addPotionEffect(new PotionEffect(PotionEffectType.values()[rndeffect], ThreadLocalRandom.current().nextInt(5, 21)*20, ThreadLocalRandom.current().nextInt(1, 3)));
                p.sendMessage(ChatColor.LIGHT_PURPLE + "Ого, вместе с едой вы получили еще что-то");
        }
    }
}
