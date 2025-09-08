package com.mixay.patuxmix.util;

import de.tr7zw.nbtapi.NBT;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.MessageFormat;
import java.util.Collections;

public class ItemMethods {
    public static ItemStack getPickaxeStack (int radius) {
        ItemStack shovel = new ItemStack(Material.NETHERITE_SHOVEL, 1);
        shovel.lore(Collections.singletonList(Component.text(MessageFormat.format(ChatColor.translateAlternateColorCodes('&', "&fЛомает с радиусом &a{0} &fблоков"), radius))));
        NBT.modify(shovel, nbt -> {
            nbt.setInteger("patuxmix:NSradius", radius);
        });
        return shovel;
    }
    public static ItemStack getPearlStack () {
        ItemStack pearl = new ItemStack(Material.ENDER_PEARL, 1);
        pearl.lore(Collections.singletonList(Component.text(ChatColor.translateAlternateColorCodes('&', "&fТочка телепортации не установлена"))));
        ItemMeta newmeta = pearl.getItemMeta();
        newmeta.displayName(LegacyComponentSerializer.legacyAmpersand().deserialize("&x&F&F&0&0&E&EУ&x&F&3&0&0&F&0м&x&E&8&0&0&F&1н&x&D&C&0&0&F&3ы&x&D&1&0&0&F&4й&x&C&5&0&0&F&6 &x&B&A&0&0&F&7ж&x&A&E&0&0&F&9е&x&A&3&0&0&F&Aм&x&9&7&0&0&F&Cч&x&8&C&0&0&F&Dу&x&8&0&0&0&F&Fг"));
        pearl.setItemMeta(newmeta);
        NBT.modify(pearl, nbt -> {
            nbt.setBoolean("patuxmix:PearlNoLoc", true);
            nbt.setBoolean("patuxmix:tppearl", true);
        });
        return pearl;
    }
}
