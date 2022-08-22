package net.violetc.cauldronpotion.cauldron;

import net.violetc.cauldronpotion.ConfigOBJ;
import net.violetc.cauldronpotion.NamespaceSave;
import net.violetc.cauldronpotion.PotionHelper;
import net.violetc.violetcpluginutil.itembuilder.PotionBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CauldronEntity {

    private final World world;

    private final Block block;
    private final Block nextBlock;
    private final BoundingBox box;

    private int damage = 0;
    private boolean isCanBrewing = false;
    private boolean isSplash = false;
    private boolean isLingering = false;
    private boolean isAddWater = false;

    private boolean isRemove = false;

    public CauldronEntity(@NotNull Block block) {
        this.block = block;
        this.world = block.getWorld();
        this.box = new BoundingBox(block.getX() + 0.125, block.getY() + 0.15, block.getZ() + 0.125, block.getX() + 0.825, block.getY() + 0.825, block.getZ() + 0.825);
        this.nextBlock = world.getBlockAt(block.getLocation().add(0, -1, 0));
    }

    public void remove() {
        isRemove = true;
    }

    public ItemStack getPotion() {
        if (isCanBrewing) {
            if (damage == 0) {
                return new PotionBuilder().setBasePotionData(new PotionData(PotionType.WATER)).build();
            }

            ItemStack potion;
            if (isSplash) {
                if (isLingering) {
                    potion = new ItemStack(Material.LINGERING_POTION);
                } else {
                    potion = new ItemStack(Material.SPLASH_POTION);
                }
            } else {
                potion = new ItemStack(Material.POTION);
            }
            PotionMeta meta = (PotionMeta) potion.getItemMeta();

            if (meta != null) {
                meta.setBasePotionData(new PotionData(PotionType.WATER));
                meta.setColor(PotionHelper.getPotionColor(damage));
                meta.getPersistentDataContainer().set(NamespaceSave.NEW_POTION_FLAG, PersistentDataType.INTEGER, 1);
                meta.setDisplayName(PotionHelper.getPotionPrefix(damage) + "药水");

                for (PotionEffect effect : PotionHelper.getPotionEffect(damage)) {
                    meta.addCustomEffect(effect, false);
                }

                if (!ConfigOBJ.config.displayPotionMeta) {
                    meta.setLore(List.of(ChatColor.GRAY + "你并不知晓其中的效果"));
                    meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                }
            }
            potion.setItemMeta(meta);

            return potion;
        } else {
            return null;
        }
    }

    public boolean isCanBrewing() {
        return isCanBrewing;
    }

    public boolean isLingering() {
        return isLingering;
    }

    public boolean isSplash() {
        return isSplash;
    }

    public boolean isRemove() {
        return isRemove;
    }

    public boolean isAddWater() {
        return isAddWater;
    }

    public void setAddWater(boolean addWater) {
        isAddWater = addWater;
    }

    public Block getBlock() {
        return block;
    }

    public int getDamage() {
        return damage;
    }

    public void cleanPotion() {
        isLingering = false;
        isSplash = false;
        damage = 0;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("isCanBrewing", isCanBrewing);
        map.put("isLingering", isLingering);
        map.put("isSplash", isSplash);
        map.put("isAddWater", isAddWater);
        map.put("damage", damage);

        map.put("world", world.getName());
        map.put("x", block.getX());
        map.put("y", block.getY());
        map.put("z", block.getZ());

        return map;
    }

    public void loadMap(@NotNull Map<String, Object> map) {
        isCanBrewing = (boolean) map.get("isCanBrewing");
        isLingering = (boolean) map.get("isLingering");
        isSplash = (boolean) map.get("isSplash");
        isAddWater = (boolean) map.get("isAddWater");
        damage = ((Number) map.get("damage")).intValue();
    }

    public void tick() {
        if (isRemove || !world.isChunkLoaded(block.getChunk())) {
            return;
        }

        boolean flag = block.getType() == Material.WATER_CAULDRON;
        boolean flag1 = block.getType() == Material.CAULDRON;

        if (!flag1 && !flag) {
            remove();
            return;
        }

        if (!isCanBrewing) {
            if (flag && nextBlock.getType() == Material.FIRE) {
                isCanBrewing = true;
                cleanPotion();
            }
        } else {
            if (flag1 || nextBlock.getType() != Material.FIRE) {
                isCanBrewing = false;
                cleanPotion();
                return;
            }
        }

        if (isCanBrewing) {
            if (isAddWater) {
                isAddWater = false;
                this.damage = PotionHelper.parsePotionItemData(PotionHelper.waterPotionData, damage);
            }

            Collection<Entity> items = world.getNearbyEntities(box);
            if (!items.isEmpty()) {
                for (Entity entity : items) {
                    if (entity instanceof Item item) {
                        if (!item.isDead()) {
                            if (PotionHelper.isBrewingItem(item.getItemStack())) {
                                item.remove();
                                if (item.getItemStack().getType() == Material.GUNPOWDER) {
                                    isSplash = true;
                                    continue;
                                }

                                if (item.getItemStack().getType() == Material.DRAGON_BREATH) {
                                    isLingering = true;
                                    continue;
                                }

                                for (int i = item.getItemStack().getAmount(); i > 0; i--) {
                                    this.damage = PotionHelper.getDamageChange(item.getItemStack().getType(), damage);
                                    this.world.playSound(block.getLocation(), Sound.BLOCK_POINTED_DRIPSTONE_DRIP_WATER_INTO_CAULDRON, 2.5F, 1.0F);
                                    // TODO sound and particle
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
