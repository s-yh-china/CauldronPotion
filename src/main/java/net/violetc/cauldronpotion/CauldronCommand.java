package net.violetc.cauldronpotion;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CauldronCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (args.length == 0) {
            onError(sender, args);
            return true;
        }

        switch (args[0]) {
            case "reload" -> onReload(sender, args);
            case "help" -> onHelp(sender, args);
            default -> onError(sender, args);
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        var list = new ArrayList<String>();

        if (args.length == 1) {
            list.add("help");
            list.add("reload");
        }

        return list;
    }

    private void onError(@NotNull CommandSender sender, @NotNull String[] args) {
        sender.sendMessage(ChatColor.RED + "错误的参数 输入/cauldronpotion help获得帮助");
    }

    private void onReload(@NotNull CommandSender sender, @NotNull String[] args) {
        ConfigOBJ.reloadConfig();
        DataOBJ.reloadData();
        sender.sendMessage(ChatColor.GREEN + "配置与数据重载完成");
    }

    private void onHelp(@NotNull CommandSender sender, @NotNull String[] args) {
        sender.sendMessage(ChatColor.GRAY + "----CauldronPotion----");
        sender.sendMessage(ChatColor.GRAY + "/cauldronpotion help 获得此帮助");
        sender.sendMessage(ChatColor.GRAY + "/cauldronpotion reload 重载配置和数据(不包括炼药锅内数据)");
    }
}
