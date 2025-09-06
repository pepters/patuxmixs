package com.mixay.patuxmix.listeners;

import com.mixay.patuxmix.util.*;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
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
                    e.getPlayer().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.RED + "Бум!"));
                    dataManager.removeChicken(chicken.getUniqueId());
                    chicken.getWorld().createExplosion(chicken, 3F, false, true);
                    blockmethods.setFlowersInRadius(chicken.getLocation(), 4);
                    chicken.remove();
                } else {
                    String message = MessageFormat.format("Нажали: {0}/5", dataManager.getClicks(chicken.getUniqueId()));
                    e.getPlayer().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + message));
                }
            } else {
                String message = MessageFormat.format("Нажали: {0}/5", dataManager.getClicks(chicken.getUniqueId()));
                e.getPlayer().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.GREEN + message));
            }
        }
    }
}
