package com.minecraftport.deathToken;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {

    public static main plugin;
    public final playerDeathListener pdl = new playerDeathListener(this);
    public final boolean tdc = getConfig().getBoolean("tellDeathCoords");
    public final boolean peps = getConfig().getBoolean("playEmeraldPickupSound");

    public void onDisable(){
        pdl.armor = null;
        pdl.items = null;
        System.out.println("DeathToken v " + getDescription().getVersion() + " has been disabled!");
    }

    public void onEnable(){
        PluginManager pm = getServer().getPluginManager();
        System.out.println("DeathToken v " + getDescription().getVersion() + " has been enabled!");
        pm.registerEvents(pdl, this);
        PluginDescriptionFile config = this.getDescription();
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

}