package com.beastsmc.nokillabuse;

import java.util.HashMap;
import java.util.UUID;

public class CoolDownHandler {


    private HashMap<UUID, Long> cooldowns;
    private long cooldownAmt = 60L*10L*1000L;

    public CoolDownHandler() {
        cooldowns = new HashMap<>();
    }

    public boolean isOnCooldown(UUID player) {
        Long expiration = cooldowns.get(player);
        if(expiration==null) {
            return false;
        }

        if(expiration > System.currentTimeMillis()) {
            return true;
        } else {
            cooldowns.remove(player);
            return false;
        }
    }

    public void addCooldown(UUID player) {
        cooldowns.put(player, this.cooldownAmt+System.currentTimeMillis());
    }

    public boolean removeCooldown(UUID player) {
        return cooldowns.remove(player)!=null;
    }

    public void setCooldownAmt(long seconds) {
        this.cooldownAmt = seconds*1000L;
    }
}
