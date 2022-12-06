package net.violetc.cauldronpotion.listener;

import net.violetc.cauldronpotion.ConfigOBJ;
import net.violetc.cauldronpotion.NamespaceSave;
import net.violetc.violetcpluginutil.PersistentDataUtil;
import net.violetc.violetcpluginutil.itembuilder.PotionBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;

public class DisableOldPotionListener implements Listener {

    @EventHandler
    public void onUseBrewingStand(@NotNull InventoryOpenEvent event) {
        if (event.getInventory().getType() == InventoryType.BREWING) {
            if (ConfigOBJ.config.disable.disableBrewingStand) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrinkPotion(@NotNull PlayerItemConsumeEvent event) {
        ItemStack item = event.getItem();
        if (item.getType() == Material.POTION) {
            if (!PersistentDataUtil.getBoolData(PersistentDataUtil.getItemHolder(item), NamespaceSave.NEW_POTION_FLAG)) {
                if (ConfigOBJ.config.disable.disableOldPotion) {
                    event.setItem(new PotionBuilder().setBasePotionData(new PotionData(PotionType.WATER)).build());
                    // TODO mes
                }
            }
        }
    }

    @EventHandler
    public void onDropPotion(@NotNull ProjectileLaunchEvent event) {
        if (ConfigOBJ.config.disable.disableOldPotion) {
            if (event.getEntity() instanceof ThrownPotion potion && potion.getShooter() instanceof Player) {
                if (!PersistentDataUtil.getBoolData(PersistentDataUtil.getItemHolder(potion.getItem()), NamespaceSave.NEW_POTION_FLAG)) {
                    ThrownPotion potion1 = (ThrownPotion) potion.getWorld().spawnEntity(potion.getLocation(), potion.getType());
                    potion1.setVelocity(potion.getVelocity());

                    ItemStack item = new ItemStack(Material.SPLASH_POTION);
                    PotionMeta meta = (PotionMeta) item.getItemMeta();
                    if (meta != null) {
                        meta.setBasePotionData(new PotionData(PotionType.WATER));
                    }
                    item.setItemMeta(meta);
                    potion1.setItem(item);

                    // event.setCancelled(true);
                    // TODO mes
                }
            }
        }
    }

    @EventHandler
    public void onShootPotionArrow(@NotNull EntityShootBowEvent event) {
        if (ConfigOBJ.config.disable.disableOldPotion) {
            if (event.getEntity() instanceof Player && event.getConsumable() != null) {
                if (event.getConsumable().getType() == Material.TIPPED_ARROW && event.getProjectile() instanceof Arrow arrow) {
                    if (!PersistentDataUtil.getBoolData(PersistentDataUtil.getItemHolder(event.getConsumable()), NamespaceSave.NEW_POTION_FLAG)) {
                        arrow.setBasePotionData(new PotionData(PotionType.WATER));
                        arrow.clearCustomEffects();
                        arrow.setColor(Color.WHITE);
                        arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
                    }
                }
            }
        }
    }
}
