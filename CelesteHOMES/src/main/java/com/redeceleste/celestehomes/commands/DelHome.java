package com.redeceleste.celestehomes.commands;

import com.redeceleste.celestehomes.managers.HomeManager;
import com.redeceleste.celestehomes.managers.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelHome implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        Player p = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("delhome")) {
            if (args.length != 1) {
                p.sendMessage(ConfigManager.DelHomeArgumentsInvalid);
                return false;
            }

            if (!HomeManager.isHome(p, args[0])) {
                p.sendMessage(ConfigManager.DelHomeNotFound);
                return false;
            }

            HomeManager.delHome(p, args[0]);
            p.sendMessage(ConfigManager.HomeSucessDeleted);
        }
        return false;
    }
}
