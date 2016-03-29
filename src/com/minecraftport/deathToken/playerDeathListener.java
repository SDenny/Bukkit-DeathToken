package com.minecraftport.deathToken;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class playerDeathListener implements Listener {

    public static main plugin;

    public playerDeathListener(main instance){plugin = instance;}

    public HashMap<Player, ItemStack[]> items = new HashMap<Player, ItemStack[]>();
    public HashMap<Player, ItemStack[]> armor = new HashMap<Player, ItemStack[]>();

    public String tag = ChatColor.GOLD + "[*] ";

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event){
        if(items.containsKey(event.getPlayer())){
            event.getPlayer().getInventory().clear();
            for(ItemStack stack : items.get(event.getPlayer())){
                if(stack != null) {
                    event.getPlayer().getInventory().addItem(stack);
                }
            }


            items.remove(event.getPlayer());
        }
        if(armor.containsKey(event.getPlayer())){
            for(ItemStack astack : armor.get(event.getPlayer())){
                if(astack != null){
                    event.getPlayer().getEquipment().setArmorContents(armor.get(event.getPlayer()));
                }
            }
            armor.remove(event.getPlayer());
        }

        event.getPlayer().setFoodLevel(10);

    }

    public boolean has10Emeralds(Player p, ItemStack i, Number n){
        ItemStack[] inv = p.getInventory().getContents();
        for(ItemStack item:inv){
            if(item.getType().equals(i)){
                if(i.getAmount() >= 10){
                    return true;
                }}return false;}
        return false;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player p = event.getEntity();
        Location loc = p.getLocation();
        ItemStack ise = new ItemStack(Material.EMERALD);
        if(plugin.tdc){
            p.sendMessage("\n" + tag + ChatColor.GRAY + "You died at X:" + loc.getBlockX() + " Y:" + loc.getBlockY() + " Z:" + loc.getBlockZ());
        }else{
            return;
        }
        if(p.getInventory().containsAtLeast(ise, 10)){
            p.getInventory().removeItem(new ItemStack(Material.EMERALD, 10));
            ItemStack[] content = event.getEntity().getInventory().getContents();
            ItemStack[] contentArmor = event.getEntity().getInventory().getArmorContents();
            items.put(event.getEntity(), content);
            armor.put(event.getEntity(), contentArmor);
            event.getEntity().getInventory().clear();
            event.getDrops().clear();
            p.sendMessage(tag + ChatColor.GOLD + "Because you had 10 emeralds in your inventory, your items were kept." + "\n ");
        }else{
            p.sendMessage(tag + ChatColor.GRAY + "Having 10 emeralds allows you to keep your items on death." + "\n ");
        }
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent e){
        if(plugin.peps && e.getItem().getItemStack().getType().equals(Material.EMERALD)) {
            Player p = e.getPlayer();
            Location loc = p.getLocation();
            loc.getWorld().playSound(loc, Sound.ORB_PICKUP, 4, 2);
        }
    }
}