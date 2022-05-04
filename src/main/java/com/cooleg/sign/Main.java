package com.cooleg.sign;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getCommand("chaos").setExecutor(new CommandHandle());
        Bukkit.getPluginManager().registerEvents(new EventHandle(this), this);
        autoFix();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown
    }

    public void autoFix() {
        new BukkitRunnable() {
            @Override
            public void run() {
                World world;
                Location loc;

                try {
                    ConfigurationSection config = getConfig();
                    double x = config.getDouble("x");
                    double y = config.getDouble("y");
                    double z = config.getDouble("z");
                    world = Bukkit.getWorld(config.getString("world"));
                    loc = new Location(world, x, y, z);
                    if (loc.getBlock().isEmpty()) {return;}
                    Sign sign = (Sign) loc.getBlock().getState();
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        String[] signView = {" ", player.getName() + "'s IP:", player.getAddress().getAddress().getHostAddress(), " "};
                        player.sendSignChange(loc, signView);
                    }
                } catch (Exception e) {
                    Bukkit.getLogger().severe(e.getMessage());
                }

            }
        }.runTaskTimer(this, 100L, 100L);

    }

}
