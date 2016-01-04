package com.beastsmc.nokillabuse;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class NoKillAbuse extends JavaPlugin {

    private CoolDownHandler cooldowns;
    private PlayerListener listener;
    private Messager messager;

    private List<String> blackListedWorlds;

    public void onEnable() {
        this.messager = new Messager();
        this.messager.runTaskTimerAsynchronously(this, 20, 20*2);

        this.listener = new PlayerListener(this);
        this.getServer().getPluginManager().registerEvents(listener, this);

        this.cooldowns = new CoolDownHandler();

        this.getCommand("safetyoff").setExecutor(this);

        saveDefaultConfig();
        cooldowns.setCooldownAmt(getConfig().getLong("cooldown(seconds)"));
        blackListedWorlds = getConfig().getStringList("world-blacklist");
    }

    public void onDisable() {

    }

    public CoolDownHandler getCoolDowns() {
        return this.cooldowns;
    }

    public List<String> getBlackListedWorlds() {
        return this.blackListedWorlds;
    }

    public Messager getMessager() {
        return this.messager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("safetyoff")) {
            if(sender instanceof Player) {
                if(getCoolDowns().removeCooldown(((Player) sender).getUniqueId())) {
                    sender.sendMessage(ChatColor.GREEN + "Your PvP death cooldown has been removed!");
                } else {
                    sender.sendMessage(ChatColor.RED + "You have no PvP death cooldown!");
                }
            } else {
                sender.sendMessage("This command must be used by a player.");
            }
        }
        return true;
    }
}
