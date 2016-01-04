package com.beastsmc.nokillabuse;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.HashSet;

public class Messager extends BukkitRunnable {
    private HashMap<Player, HashSet<String>> bufferedMessages;

    public Messager() {
        bufferedMessages = new HashMap<>();
    }

    @Override
    public void run() {
        for(Player p : bufferedMessages.keySet()) {
            if(p.isOnline()) {
                bufferedMessages.get(p).stream().forEachOrdered(p::sendMessage);
            }
        }
        bufferedMessages.clear();
    }

    public void sendMessage(Player player, String message) {
        HashSet<String> messages = bufferedMessages.get(player);
        if(messages==null) {
            messages = new HashSet<>();
        }
        messages.add(message);
        bufferedMessages.put(player, messages);
    }


}
