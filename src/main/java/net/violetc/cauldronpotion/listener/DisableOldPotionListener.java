package net.violetc.cauldronpotion.listener;

import net.violetc.cauldronpotion.ConfigOBJ;
import net.violetc.cauldronpotion.NamespaceSave;
import net.violetc.violetcpluginutil.PersistentDataUtil;
import net.violetc.violetcpluginutil.itembuilder.PotionBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;

public class DisableOldPotionListener implements Listener {

    @EventHandler
    public void onUseBrewingStand(@NotNull InventoryOpenEvent event) {
        if (event.getInventory().getType() == InventoryType.BREWING) {
            if (ConfigOBJ.config.disableBrewingStand) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrinkPotion(@NotNull PlayerItemConsumeEvent event) {
        ItemStack item = event.getItem();
        if (item.getType() == Material.POTION) {
            if (!PersistentDataUtil.getBoolData(PersistentDataUtil.getItemHolder(item), NamespaceSave.NEW_POTION_FLAG)) {
                if (ConfigOBJ.config.disableOldPotion) {
                    event.setItem(new PotionBuilder().setBasePotionData(new PotionData(PotionType.WATER)).build());
                    // TODO mes
                }
            }
        }
    }

    @EventHandler
    public void onDropPotion(@NotNull ProjectileLaunchEvent event) {
        if (event.getEntity() instanceof ThrownPotion potion && potion.getShooter() instanceof Player player) {
            ItemStack item = potion.getItem();
            if (!PersistentDataUtil.getBoolData(PersistentDataUtil.getItemHolder(item), NamespaceSave.NEW_POTION_FLAG)) {
                if (ConfigOBJ.config.disableOldPotion) {
                    event.setCancelled(true);
                    // TODO mes
                }
            }
        }
    }
}
