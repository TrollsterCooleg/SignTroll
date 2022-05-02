package com.cooleg.sign;

import org.bukkit.*;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class EventHandle implements Listener {

    Main main;

    public EventHandle(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        String lore;
        try {
            lore = event.getItemInHand().getItemMeta().getLore().get(0);
        } catch (Exception e) {return;}
        if (lore.equals("This sign is quite powerful...")) {
            event.getPlayer().sendMessage("The place has been set...");
            ConfigurationSection config = main.getConfig();
            Location loc = event.getBlockPlaced().getLocation();
            config.set("x", loc.getBlockX());
            config.set("y", loc.getBlockY());
            config.set("z", loc.getBlockZ());
            main.saveConfig();
            for (Player p : Bukkit.getOnlinePlayers()) {
                setSign(p);
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        ConfigurationSection config = main.getConfig();
        Location loc = e.getBlock().getLocation();
        if (loc.getBlockX() == config.getInt("x") && loc.getBlockY() == config.getInt("y") && loc.getBlockZ() == config.getInt("z")) {
            if (e.getPlayer().hasPermission("sign.troll")) {
                return;
            } else {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    setSign(p);
                }
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void playerLogin(PlayerLoginEvent e) {
        setSign(e.getPlayer());

    }

    public void setSign(Player p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                ConfigurationSection config = main.getConfig();
                double x;
                double y;
                double z;
                World world;
                Location loc;
                try {
                    x = config.getDouble("x");
                    y = config.getDouble("y");
                    z = config.getDouble("z");
                    world = Bukkit.getWorld(config.getString("world"));
                    loc = new Location(world, x, y, z);
                    Sign sign = (Sign) loc.getBlock().getState();
                    String[] signView = {" ", p.getName() + "'s IP:", p.getAddress().getAddress().toString().substring(1), " "};
                    p.sendSignChange(loc, signView);
                } catch (Exception e) {}

            }
        }.runTaskLater(main, 5L);
    }


}
