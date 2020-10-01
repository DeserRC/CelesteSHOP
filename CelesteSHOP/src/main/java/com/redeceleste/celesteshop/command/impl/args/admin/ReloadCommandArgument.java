package com.redeceleste.celesteshop.command.impl.args.admin;

import com.redeceleste.celesteshop.Main;
import com.redeceleste.celesteshop.command.CommandArgument;
import com.redeceleste.celesteshop.manager.ConfigManager;
import com.redeceleste.celesteshop.model.ConfigType;
import com.redeceleste.celesteshop.util.impl.ChatUtil;
import org.bukkit.command.CommandSender;

public class ReloadCommandArgument extends CommandArgument {
    private final Main main;
    private final ConfigManager config;
    private final ChatUtil chat;

    public ReloadCommandArgument(Main main) {
        super(false, "reload", "recarregar");
        this.main = main;
        this.config = main.getConfigManager();
        this.chat = main.getMessageFactory().getChat();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission(config.get("Admin_Permission", ConfigType.config).toString())) {
            chat.send(sender, "NoPermission", ConfigType.config);
            return;
        }

        chat.send(sender, config.reload("Message.ReloadMessage"));
    }
}
