package com.mixay.patuxmix.util;

import de.tr7zw.nbtapi.NBT;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.text.MessageFormat;
import java.util.Collections;

public class itemmethods {
    public static ItemStack getPickaxeStack (int radius) {
        ItemStack shovel = new ItemStack(Material.NETHERITE_SHOVEL, 1);
        shovel.lore(Collections.singletonList(Component.text(MessageFormat.format(ChatColor.translateAlternateColorCodes('&', "&fЛомает с радиусом &a{0} &fблоков"), radius))));
        NBT.modify(shovel, nbt -> {
            nbt.setInteger("NSradius", radius);
        });
        return shovel;
    }
}
