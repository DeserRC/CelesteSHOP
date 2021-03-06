package com.redeceleste.celesteessentials.command.impl.admin.args;

import com.redeceleste.celesteessentials.CelesteEssentials;
import com.redeceleste.celesteessentials.command.CommandArgument;
import com.redeceleste.celesteessentials.manager.ConfigManager;
import com.redeceleste.celesteessentials.util.impl.BarUtil;
import com.redeceleste.celesteessentials.util.impl.ChatUtil;
import com.redeceleste.celesteessentials.util.impl.TitleUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExpResetArg extends CommandArgument {
    private final CelesteEssentials main;
    private final ConfigManager config;
    private final ChatUtil chat;
    private final BarUtil bar;
    private final TitleUtil title;

    public ExpResetArg(CelesteEssentials main) {
        super(false, "reset", "clear", "resetar", "limpar");
        this.main = main;
        this.config = main.getConfigManager();
        this.chat = main.getMessageFactory().getChat();
        this.bar = main.getMessageFactory().getBar();
        this.title = main.getMessageFactory().getTitle();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player t = Bukkit.getPlayer(args[0]);
        if (args.length != 1) {
            chat.send(sender, "Exp.Invalid-Argument");
            bar.send(sender, "Exp.Invalid-Argument-Bar");
            title.send(sender, "Exp.Invalid-Argument-Title");
            return;
        }

        t.giveExpLevels(-t.getLevel());

        String mode = config.getMessage("Exp.Reset");
        mode = mode.replace("{player}", t.getName())
                .replace("{level}", String.valueOf(0));

        chat.send(sender, "Exp.Success",
                chat.build("{player}", t.getName()),
                chat.build("{level}", 0),
                chat.build("{mode}", mode));
        bar.send(sender, "Exp.Success-Bar",
                chat.build("{player}", t.getName()),
                chat.build("{level}", 0),
                chat.build("{mode}", mode));
        title.send(sender, "Exp.Success-Title",
                chat.build("{player}", t.getName()),
                chat.build("{level}", 0),
                chat.build("{mode}", mode));
    }
}
