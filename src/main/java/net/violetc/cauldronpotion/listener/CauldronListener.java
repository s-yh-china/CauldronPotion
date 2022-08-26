package net.violetc.cauldronpotion.listener;

import net.violetc.cauldronpotion.ConfigOBJ;
import net.violetc.cauldronpotion.NamespaceSave;
import net.violetc.cauldronpotion.cauldron.CauldronEntity;
import net.violetc.cauldronpotion.cauldron.CauldronEntityManger;
import net.violetc.violetcpluginutil.PersistentDataUtil;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.CauldronLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;

public class CauldronListener implements Listener {

    @EventHandler
    public void onPlayerPlace(@NotNull BlockPlaceEvent event) {
        if (event.getBlockPlaced().getType() == Material.CAULDRON) {
            if (!CauldronEntityManger.getManger().hasEntity(event.getBlock())) {
                CauldronEntityManger.getManger().addEntity(event.getBlock());
            }
        }
    }

    @EventHandler
    public void onPistonExtend(@NotNull BlockPistonExtendEvent event) {
        if (ConfigOBJ.config.disableCauldronMoveByPiston) {
            for (Block block : event.getBlocks()) {
                if (block.getType() == Material.CAULDRON || block.getType() == Material.WATER_CAULDRON) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onPistonRetract(@NotNull BlockPistonRetractEvent event) {
        if (ConfigOBJ.config.disableCauldronMoveByPiston) {
            for (Block block : event.getBlocks()) {
                if (block.getType() == Material.CAULDRON || block.getType() == Material.WATER_CAULDRON) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onCauldronChange(@NotNull CauldronLevelChangeEvent event) {
        if (event.getReason() == CauldronLevelChangeEvent.ChangeReason.BANNER_WASH
                || event.getReason() == CauldronLevelChangeEvent.ChangeReason.ARMOR_WASH
                || event.getReason() == CauldronLevelChangeEvent.ChangeReason.SHULKER_WASH) {
            event.setCancelled(true);
        }

        CauldronEntity entity = CauldronEntityManger.getManger().getOrAddEntity(event.getBlock());

        if (event.getReason() == CauldronLevelChangeEvent.ChangeReason.NATURAL_FILL) {
            if (CauldronEntityManger.getManger().hasEntity(event.getBlock())) {
                if (entity.isCanBrewing()) {
                    entity.setAddWater(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerUse(@NotNull PlayerInteractEvent event) {
        if (event.hasBlock() && event.getClickedBlock() != null && event.hasItem() && event.getItem() != null && !event.getItem().getType().isAir() && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            ItemStack item = event.getItem();
            if (block.getType() == Material.CAULDRON || block.getType() == Material.WATER_CAULDRON) {
                CauldronEntity entity = CauldronEntityManger.getManger().getOrAddEntity(block);

                if (item.getType() == Material.WATER_BUCKET) {
                    if (entity.isCanBrewing()) {
                        entity.setAddWater(true);
                    }
                } else if (item.getType() == Material.POTION) {
                    PotionMeta meta = (PotionMeta) item.getItemMeta();
                    if (meta != null) {
                        if (meta.getBasePotionData().getType() == PotionType.WATER) {
                            if (meta.hasCustomEffects() || PersistentDataUtil.getBoolData(meta, NamespaceSave.NEW_POTION_FLAG)) {
                                event.setCancelled(true);
                                if (entity.isCanBrewing() && entity.getDamage() != 0) {
                                    block.getWorld().createExplosion(block.getLocation(), 1, true, false, event.getPlayer());
                                    entity.cleanPotion();

                                    block.setType(Material.CAULDRON);
                                    item.setType(Material.GLASS_BOTTLE);
                                    item.setItemMeta(null);

                                    block.getWorld().playSound(block.getLocation(), Sound.ITEM_BOTTLE_EMPTY, 1, 1);
                                }
                            } else if (entity.isCanBrewing()) {
                                entity.setAddWater(true);
                            }
                        } else {
                            event.setCancelled(true);
                            if (entity.isCanBrewing()) {
                                block.getWorld().createExplosion(block.getLocation(), 2, true, false, event.getPlayer());
                                entity.cleanPotion();

                                block.setType(Material.CAULDRON);
                                item.setType(Material.GLASS_BOTTLE);
                                item.setItemMeta(null);

                                block.getWorld().playSound(block.getLocation(), Sound.ITEM_BOTTLE_EMPTY, 1, 1);
                            }
                        }
                    }
                }

                if ((item.getType() == Material.GLASS_BOTTLE || item.getType() == Material.ARROW) && entity.isCanBrewing() && block.getType() == Material.WATER_CAULDRON) {
                    event.setCancelled(true);
                    Levelled level = (Levelled) block.getBlockData();

                    if (!ConfigOBJ.config.enablePotionArrow && item.getType() == Material.ARROW) {
                        return;
                    }

                    if (level.getLevel() != 0) {
                        if (level.getLevel() == 1) {
                            block.setType(Material.CAULDRON);
                        } else {
                            level.setLevel(level.getLevel() - 1);
                            block.setBlockData(level);
                        }

                        if (item.getType() == Material.GLASS_BOTTLE) {
                            block.getWorld().playSound(block.getLocation(), Sound.ITEM_BOTTLE_FILL, 1, 1);

                            item.setAmount(item.getAmount() - 1);
                            event.getPlayer().getInventory().addItem(entity.getPotion());
                        } else if (item.getType() == Material.ARROW && entity.getDamage() != 0) {
                            int number = Math.min(item.getAmount(), 8);
                            block.getWorld().playSound(block.getLocation(), Sound.ITEM_BOTTLE_FILL, 1, 1);

                            item.setAmount(item.getAmount() - number);
                            event.getPlayer().getInventory().addItem(entity.getPotionArrow(number));
                        }
                    }
                }
            }
        }
    }
}
