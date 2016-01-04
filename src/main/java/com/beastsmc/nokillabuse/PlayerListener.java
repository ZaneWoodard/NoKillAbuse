package com.beastsmc.nokillabuse;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerListener implements Listener {

    private final NoKillAbuse plugin;

    public PlayerListener(NoKillAbuse plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority= EventPriority.MONITOR, ignoreCancelled=true)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        if(victim.equals(killer) || killer==null || plugin.getBlackListedWorlds().contains(victim.getWorld().getName())) {
            return;
        }

        plugin.getCoolDowns().addCooldown(victim.getUniqueId());
        victim.sendMessage(ChatColor.GREEN + "You have died in PvP and been granted temporary protection!");

    }

    @EventHandler(priority= EventPriority.HIGHEST, ignoreCancelled=true)
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        Entity victim = event.getEntity();
        Entity damager = event.getDamager();
        if(plugin.getBlackListedWorlds().contains(victim.getWorld().getName())) {
            return;
        }
        if(victim instanceof Player && damager instanceof Player) {
            boolean cancelled = false;
            if(plugin.getCoolDowns().isOnCooldown(victim.getUniqueId())) {
                cancelled = true;
                plugin.getMessager().sendMessage((Player)damager, ChatColor.GREEN + "Player is currently protected by PvP cooldown!");
            }
            if(plugin.getCoolDowns().isOnCooldown(damager.getUniqueId())) {
                cancelled = true;
                plugin.getMessager().sendMessage((Player)victim, ChatColor.GREEN + "You have died in PvP recently and are protected! Use /safetyoff to enable combat!");

            }
            if(cancelled) event.setCancelled(cancelled);
        }
    }
}
