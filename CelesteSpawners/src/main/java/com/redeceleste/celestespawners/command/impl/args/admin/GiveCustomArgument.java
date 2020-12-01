package com.redeceleste.celestespawners.command.impl.args.admin;

import com.redeceleste.celestespawners.CelesteSpawners;
import com.redeceleste.celestespawners.command.CommandArgument;
import com.redeceleste.celestespawners.manager.ConfigManager;
import com.redeceleste.celestespawners.manager.impl.SpawnerCustomManager;
import com.redeceleste.celestespawners.type.MobType;
import com.redeceleste.celestespawners.util.impl.BarUtil;
import com.redeceleste.celestespawners.util.impl.ChatUtil;
import com.redeceleste.celestespawners.util.impl.TitleUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.regex.Pattern;

public class GiveCustomArgument extends CommandArgument {
    private final CelesteSpawners main;
    private final ConfigManager config;
    private final SpawnerCustomManager spawnerCustom;
    private final ChatUtil chat;
    private final BarUtil bar;
    private final TitleUtil title;

    public GiveCustomArgument(CelesteSpawners main) {
        super(false, "givecustom", "customgive", "givesuper", "supergive", "darcustom", "darsuper", "enviarcustom", "enviarsuper");
        this.main = main;
        this.config = main.getConfigManager();
        this.spawnerCustom = main.getSpawnerFactory().getSpawnerCustom();
        this.chat = main.getMessageFactory().getChat();
        this.bar = main.getMessageFactory().getBar();
        this.title = main.getMessageFactory().getTitle();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        String perm = config.getConfig("Permission.Admin");
        if (!sender.hasPermission(perm)) {
            chat.send(sender, "No-Permission.Admin");
            return;
        }

        if (!(args.length == 3) || Pattern.compile("[^0-9]").matcher(args[2]).find() || Integer.parseInt(args[2]) < 1) {
            chat.send(sender, "GiveCustom.Invalid-Argument");
            bar.send(sender, "GiveCustom.Invalid-Argument-Bar");
            title.send(sender, "GiveCustom.Invalid-Argument-Title");
            return;
        }

        Player t = Bukkit.getPlayer(args[0]);
        if (t == null) {
            chat.send(sender, "GiveCustom.Player-Not-Found",
                    chat.build("{player}", args[0]));
            bar.send(sender, "GiveCustom.Player-Not-Found-Bar",
                    chat.build("{player}", args[0]));
            title.send(sender, "GiveCustom.Player-Not-Found-Title",
                    chat.build("{player}", args[0]));
            return;
        }

        boolean spawnerExist = spawnerCustom.isExist(args[1]);
        if (!spawnerExist) {
            chat.send(sender, "GiveCustom.Spawner-Not-Found",
                    chat.build("{type}", args[1]));
            bar.send(sender, "GiveCustom.Spawner-Not-Found-Bar",
                    chat.build("{type}", args[1]));
            title.send(sender, "GiveCustom.Spawner-Not-Found-Title",
                    chat.build("{type}", args[1]));
            return;
        }

        String name = args[1];
        MobType type = spawnerCustom.getMob(name);
        long amount = Long.parseLong(args[2]);

        ItemStack item = spawnerCustom.getSpawner(name, amount, type);
        t.getInventory().addItem(item);

        chat.send(sender, "GiveCustom.Success",
                chat.build("{player}", t.getName()),
                chat.build("{amount}", amount),
                chat.build("{type}", type.getName()));
        bar.send(sender, "GiveCustom.Success-Bar",
                chat.build("{player}", t.getName()),
                chat.build("{amount}", amount),
                chat.build("{type}", type.getName()));
        title.send(sender, "GiveCustom.Success-Title",
                chat.build("{player}", t.getName()),
                chat.build("{amount}", amount),
                chat.build("{type}", type.getName()));

        chat.send(t, "GiveCustom.Receive",
                chat.build("{executor}", sender.getName()),
                chat.build("{amount}", amount),
                chat.build("{type}", type.getName()));
        bar.send(t, "GiveCustom.Receive-Bar",
                chat.build("{executor}", sender.getName()),
                chat.build("{amount}", amount),
                chat.build("{type}", type.getName()));
        title.send(t, "GiveCustom.Receive-Title",
                chat.build("{executor}", sender.getName()),
                chat.build("{amount}", amount),
                chat.build("{type}", type.getName()));
    }
}