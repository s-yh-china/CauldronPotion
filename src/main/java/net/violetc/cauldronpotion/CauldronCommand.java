package net.violetc.cauldronpotion;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CauldronCommand implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "输入/cauldronpotion help获得帮助");
            return true;
        }

        switch (args[0]) {
            case "reload" -> onReload(sender, args);
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }

    private void onReload(@NotNull CommandSender sender, @NotNull String[] args) {
        ConfigOBJ.reloadConfig();
        DataOBJ.reloadData();
        sender.sendMessage(ChatColor.GREEN + "配置与数据重载完成");
    }
}
