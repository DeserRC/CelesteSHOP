package com.redeceleste.celestehomes.manager;

import com.redeceleste.celestehomes.CelesteHomes;
import com.redeceleste.celestehomes.event.TeleportEvent;
import com.redeceleste.celestehomes.util.impls.ActionBarUtil;
import com.redeceleste.celestehomes.util.impls.TitleUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class TeleportManager {
    public static HashMap<String, Long> cd = new HashMap<>();

    public static void teleportPlayer(Player p, String name, String loc) {
        if (PermissionManager.hasDelayOtherTeleportBypass(p)) {
            Bukkit.getPluginManager().callEvent(new TeleportEvent(p, name, loc));
            return;
        }

        if (cd.containsKey(p.getName())) {
            if (cd.get(p.getName()) >= System.currentTimeMillis()) {
                ActionBarUtil.sendMessage(p, ConfigManager.DelayFromOtherTeleportMessage
                        .replace("%delay%", String.valueOf(TimeUnit.MILLISECONDS.toSeconds(cd.get(p.getName())) - TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()))));
                return;
            }
            cd.remove(p.getName());
        }

        if (PermissionManager.hasDelayBypass(p)) {
            Bukkit.getPluginManager().callEvent(new TeleportEvent(p, name, loc));
            return;
        }

        CelesteHomes.getInstance().getExecutorService().scheduleAtFixedRate(new BukkitRunnable() {
            Integer delay = Integer.parseInt(ConfigManager.Delay);
            Location pos1 = p.getLocation();
            @Override
            public void run() {
                if (delay == 0) {
                    Bukkit.getPluginManager().callEvent(new TeleportEvent(p, name, loc));
                    cancel();
                }

                TitleUtil.sendTitle(p, ConfigManager.MessageWaitingTeleportTitle
                        .replace("%delay%", delay.toString()), ConfigManager.MessageWaitingTeleportSubTitle
                        .replace("%delay%", delay.toString()), 1,1,1);
                p.playSound(p.getLocation(), Sound.valueOf(ConfigManager.SoundWaitingTeleport), 1, 1);
                delay--;

                Location pos2 = p.getLocation();
                if (pos1.getX() != pos2.getX() || pos1.getY() != pos2.getY() || pos1.getZ() != pos2.getZ() || pos1.getWorld() != pos2.getWorld()) {
                    TitleUtil.sendTitle(p, ConfigManager.MessageCancelTeleportTitle, ConfigManager.MessageCancelTeleportSubTitle,1,1,1);
                    p.playSound(p.getLocation(), Sound.valueOf(ConfigManager.SoundCancelTeleport), 1, 1);
                    cancel();
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
}