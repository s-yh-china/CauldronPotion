package net.violetc.cauldronpotion;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class NamespaceSave {

    public static NamespacedKey NEW_POTION_FLAG;

    public static void init(JavaPlugin plugin) {
        NEW_POTION_FLAG = new NamespacedKey(plugin, "new_potion_flag");
    }
}
