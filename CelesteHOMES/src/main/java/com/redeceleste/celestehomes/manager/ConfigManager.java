package com.redeceleste.celestehomes.manager;

import com.redeceleste.celestehomes.Main;
import com.redeceleste.celestehomes.model.InventoryArgument;
import com.redeceleste.celestehomes.model.impls.Inventory;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigManager {
    public static String Permission, NoPermission, Reload, MinimumCharactersHome, Delay, DelayFromOtherTeleport;
    public static String SetHomeArgumentsInvalid, BlackListWorldMessage, HomeLimitReached, FewCharacters, ContainsHome, HomeSucessCreate;
    public static String DelHomeArgumentsInvalid, DelHomeNotFound, HomeSucessDeleted;
    public static String HomeNotFound, HomeInvalidArgument, DelayFromOtherTeleportMessage, AHomeInvalidArgument, PlayerNotFound, MessageWaitingTeleportTitle, MessageWaitingTeleportSubTitle, MessageSucessTeleportTitle, MessageSucessTeleportSubTitle, MessageCancelTeleportTitle, MessageCancelTeleportSubTitle;
    public static String SoundWaitingTeleport, SoundSucessTeleport, SoundCancelTeleport;
    public static String TitleGUI;
    public static HashSet<InventoryArgument> Itens = new HashSet<>();
    public static HashSet<InventoryArgument> Template = new HashSet<>();
    public static List<String> BlackList = new ArrayList<>();

    private static List<String> lore, en = new ArrayList<>();

    public static void loadMessage() {
        BlackList = getList("BlackListWorld");

        Permission = get("Permission");
        NoPermission = get("NoPermission");
        Reload = get("Reload");
        MinimumCharactersHome = get("MinimumCharactersHome");
        Delay = get("Delay");
        DelayFromOtherTeleport = get("DelayFromOtherTeleport");
        
        SetHomeArgumentsInvalid = get("Message.SetHomeArgumentsInvalid");
        BlackListWorldMessage = get("Message.BlackListWorldMessage");
        HomeLimitReached = get("Message.HomeLimitReached");
        FewCharacters = get("Message.FewCharacters");
        ContainsHome = get("Message.ContainsHome");
        HomeSucessCreate = get("Message.HomeSucessCreate");

        DelHomeArgumentsInvalid = get("Message.DelHomeArgumentsInvalid");
        DelHomeNotFound = get("Message.DelHomeNotFound");
        HomeSucessDeleted = get("Message.HomeSucessDeleted");

        HomeInvalidArgument = get("Message.HomeInvalidArgument");
        HomeNotFound = get("Message.HomeNotFound");
        DelayFromOtherTeleportMessage = get("Message.DelayMessage");
        AHomeInvalidArgument = get("Message.AHomeInvalidArgument");
        PlayerNotFound = get("Message.PlayerNotFound");

        MessageWaitingTeleportTitle = get("Message.MessageWaitingTeleport.Title");
        MessageWaitingTeleportSubTitle = get("Message.MessageWaitingTeleport.SubTitle");
        MessageSucessTeleportTitle = get("Message.MessageSucessTeleport.Title");
        MessageSucessTeleportSubTitle = get("Message.MessageSucessTeleport.SubTitle");
        MessageCancelTeleportTitle = get("Message.MessageCancelTeleport.Title");
        MessageCancelTeleportSubTitle = get("Message.MessageCancelTeleport.SubTitle");

        SoundWaitingTeleport = get("Sounds.SoundWaitingTeleport");
        SoundSucessTeleport = get("Sounds.SoundSucessTeleport");
        SoundCancelTeleport = get("Sounds.SoundCancelTeleport");

        TitleGUI = get("Inventory.Title");

        getItens();
        getTemplate();
    }

    public static void reloadMessage() {
        Main.getInstance().reloadConfig();
        loadMessage();
    }

    private static String get(String path) {
        return Main.getInstance().getConfig().getString(path, ChatColor.DARK_RED + "There was an error loading the message: " + ChatColor.YELLOW + path).replace('&', '\u00A7');
    }

    private static List<String> getList(String path) {
        return Main.getInstance().getConfig().getStringList(path).stream().map(lore -> ChatColor.translateAlternateColorCodes('&', lore)).collect(Collectors.toList());
    }

    //Get Custom Itens
    private static void getItens() {
        if (!Itens.isEmpty()) {
            Itens.clear();
        }

        for (String menu : Main.getInstance().getConfig().getConfigurationSection("Inventory.CustomInventory").getKeys(false)) {
            Integer slot = Integer.parseInt(get("Inventory.CustomInventory." + menu + ".Slot"));
            Integer amount = Integer.parseInt(get("Inventory.CustomInventory." + menu + ".Amount"));
            Material material = Material.valueOf(get("Inventory.CustomInventory." + menu + ".Material"));
            Integer data = Integer.valueOf(get("Inventory.CustomInventory." + menu + ".Data"));
            String name = get("Inventory.CustomInventory." + menu + ".Name").replace("&", "§");
            Boolean glow = Boolean.parseBoolean(get("Inventory.CustomInventory." + menu + ".Glow"));

            lore = getList("Inventory.CustomInventory." + menu + ".Lore");
            en = getList("Inventory.CustomInventory." + menu + ".Enchantment");

            Itens.add(new Inventory(name, material, data, slot, amount, glow, lore, en));
        }
    }

    //Get Home Templates
    private static void getTemplate() {
        if (!Template.isEmpty()) {
            Template.clear();
        }

        Material material = Material.valueOf(get("Inventory.HomeTemplate.Material"));
        Integer data = Integer.valueOf(get("Inventory.HomeTemplate.Data"));
        String name = get("Inventory.HomeTemplate.Name");
        Boolean glow = Boolean.parseBoolean(get("Inventory.HomeTemplate.Glow"));
        lore = getList("Inventory.HomeTemplate.Lore");
        en = getList("Inventory.HomeTemplate.Enchantment");

        Template.add(new Inventory(name, material, data, null, null, glow, lore, en));
    }
}