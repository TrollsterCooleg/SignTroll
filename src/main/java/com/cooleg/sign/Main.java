package com.cooleg.sign;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getCommand("chaos").setExecutor(new CommandHandle());
        Bukkit.getPluginManager().registerEvents(new EventHandle(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutd
    }

}
