package com.mixay.patuxmix.listeners;

import com.mixay.patuxmix.util.*;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.text.MessageFormat;
import java.util.logging.Logger;

public class chickenClickHandler implements Listener {
    Logger logger = Bukkit.getLogger();
    @EventHandler
    public void onChickenClick (PlayerInteractAtEntityEvent e) {
        if (e.getRightClicked() instanceof Chicken && e.getHand().equals(EquipmentSlot.HAND)) {
            // уверены что клик на курицу и событие от главной руки
            Entity chicken = e.getRightClicked();
            boolean usl = dataManager.increaseChicken(chicken.getUniqueId());
            logger.info(MessageFormat.format("Получен правый клик по курице от игрока {0}, какой по счету: {1}", e.getPlayer().getName(), dataManager.getClicks(chicken.getUniqueId())));
            if (!usl) {
                // знаем что обьект уже существует, проверяем скока там уже было кликнуто
                if (dataManager.getClicks(chicken.getUniqueId()) > 4) {
                    // обрабатываем взрыв и замену блоков
                    e.getPlayer().sendActionBar(Component.text(ChatColor.RED + "Бум!"));
                    dataManager.removeChicken(chicken.getUniqueId());
                    chicken.getWorld().createExplosion(chicken, 3F, false, true);
                    blockmethods.setFlowersInRadius(chicken.getLocation(), 4);
                    chicken.remove();
                } else {
                    String message = MessageFormat.format("Нажали: {0}/5", dataManager.getClicks(chicken.getUniqueId()));
                    e.getPlayer().sendActionBar(Component.text(ChatColor.GREEN + message));
                }
            } else {
                String message = MessageFormat.format("Нажали: {0}/5", dataManager.getClicks(chicken.getUniqueId()));
                e.getPlayer().sendActionBar(Component.text(ChatColor.GREEN + message));
            }
        }
    }
}
