package net.violetc.cauldronpotion.cauldron;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.violetc.cauldronpotion.ConfigOBJ;
import net.violetc.cauldronpotion.NamespaceSave;
import net.violetc.cauldronpotion.PotionHelper;
import net.violetc.violetcpluginutil.itembuilder.PotionBuilder;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CauldronEntity {

    private final World world;

    private final Block block;
    private final Block nextBlock;
    private final BoundingBox box;
    private final Random random = new Random();

    private int damage = 0;
    private boolean isCanBrewing = false;
    private boolean isSplash = false;
    private boolean isLingering = false;
    private boolean isAddWater = false;
    private boolean isAddNetherWart = false;
    private boolean isInverted = false;

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

    @Nullable
    public ItemStack getPotion() {
        if (isCanBrewing) {
            PotionBuilder potion = getPotionBuilder();

            if (damage == 0) {
                potion.setBasePotionType(PotionType.WATER);
            } else {
                potion.setBasePotionType(PotionType.WATER);
                potion.setColor(PotionHelper.getPotionColor(damage));
                potion.addPersistentData(NamespaceSave.NEW_POTION_FLAG, PersistentDataType.INTEGER, 1);
                potion.setName(PotionHelper.getPotionPrefix(damage) + "药水");

                for (PotionEffect effect : PotionHelper.getPotionEffect(damage)) {
                    potion.addCustomEffect(effect, false);
                }

                if (!ConfigOBJ.config.display.displayPotionMeta) {
                    potion.setLore(0, Component.text("你并不知晓其中的效果").color(NamedTextColor.GRAY));
                    potion.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
                } else if (ConfigOBJ.config.display.displayPotionDamage) {
                    potion.setLore(0, Component.text("药水修饰值: " + damage).color(NamedTextColor.GRAY));
                }
            }

            return potion.build();
        } else {
            return null;
        }
    }

    @Nullable
    public ItemStack getPotionArrow(int number) {
        if (isCanBrewing) {
            if (damage == 0) {
                return new ItemStack(Material.TIPPED_ARROW, number);
            }

            PotionBuilder arrow = new PotionBuilder(Material.TIPPED_ARROW).setAmount(number);
            arrow.setBasePotionType(PotionType.WATER);
            arrow.setColor(PotionHelper.getPotionColor(damage));
            arrow.addPersistentData(NamespaceSave.NEW_POTION_FLAG, PersistentDataType.INTEGER, 1);
            arrow.setName(PotionHelper.getPotionPrefix(damage) + "药水之箭");

            for (PotionEffect effect : PotionHelper.getPotionEffect(damage)) {
                arrow.addCustomEffect(new PotionEffect(effect.getType(), effect.getDuration() / 8, effect.getAmplifier()), false);
            }

            if (!ConfigOBJ.config.display.displayPotionMeta) {
                arrow.setLore(0, Component.text("你并不知晓其中的效果").color(NamedTextColor.GRAY));
                arrow.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
            } else if (ConfigOBJ.config.display.displayPotionDamage) {
                arrow.setLore(0, Component.text("药水箭修饰值: " + damage).color(NamedTextColor.GRAY));
            }

            return arrow.build();
        } else {
            return null;
        }
    }

    public PotionBuilder getPotionBuilder() {
        if (isSplash) {
            if (isLingering) {
                return new PotionBuilder(Material.LINGERING_POTION);
            } else {
                return new PotionBuilder(Material.SPLASH_POTION);
            }
        } else {
            return new PotionBuilder(Material.POTION);
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

    public boolean isAddNetherWart() {
        return isAddNetherWart;
    }

    public boolean isInverted() {
        return isInverted;
    }

    public boolean isHasPotion() {
        return isCanBrewing && damage != 0;
    }

    public void setAddNetherWart(boolean addNetherWart) {
        isAddNetherWart = addNetherWart;
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

    public World getWorld() {
        return world;
    }

    public void cleanPotion() {
        isLingering = false;
        isSplash = false;
        isAddNetherWart = false;
        isAddWater = false;
        isInverted = false;
        damage = 0;
    }

    public Map<String, Object> saveMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("isCanBrewing", isCanBrewing);
        map.put("isLingering", isLingering);
        map.put("isSplash", isSplash);
        map.put("isAddWater", isAddWater);
        map.put("isAddNetherWart", isAddNetherWart);
        map.put("isInverted", isInverted);
        map.put("damage", damage);

        map.put("world", world.getName());
        map.put("x", block.getX());
        map.put("y", block.getY());
        map.put("z", block.getZ());

        return map;
    }

    public void loadMap(@NotNull Map<String, Object> map) {
        isCanBrewing = (boolean) map.getOrDefault("isCanBrewing", false);
        isLingering = (boolean) map.getOrDefault("isLingering", false);
        isSplash = (boolean) map.getOrDefault("isSplash", false);
        isAddWater = (boolean) map.getOrDefault("isAddWater", false);
        isAddNetherWart = (boolean) map.getOrDefault("isAddNetherWart", false);
        isInverted = (boolean) map.getOrDefault("isInverted", false);
        damage = ((Number) map.getOrDefault("damage", 0)).intValue();
    }

    public void tick() {
        if (isRemove || !world.isChunkLoaded(block.getChunk())) {
            return;
        }

        boolean flag = block.getType() == Material.WATER_CAULDRON;
        boolean flag1 = block.getType() == Material.CAULDRON;
        boolean flag2 = nextBlock.getType() == Material.SOUL_FIRE;
        boolean flag3 = flag2 || nextBlock.getType() == Material.FIRE;

        if (!flag1 && !flag) {
            remove();
            return;
        }

        if (!isCanBrewing) {
            if (flag && flag3) {
                isInverted = flag2;
                isCanBrewing = true;
                cleanPotion();
            }
        } else {
            if (flag1 || !flag3) {
                isCanBrewing = false;
                cleanPotion();
                return;
            }
        }

        if (isCanBrewing) {
            if (isAddWater) {
                isAddWater = false;
                this.damage = PotionHelper.parsePotionItemData(PotionHelper.waterPotionData, damage, false);
            }

            Collection<Entity> items = world.getNearbyEntities(box);
            if (!items.isEmpty()) {
                for (Entity entity : items) {
                    if (entity instanceof Item item) {
                        if (!item.isDead()) {
                            if (PotionHelper.isBrewingItem(item.getItemStack())) {
                                item.remove();

                                if (ConfigOBJ.config.cauldron.startBrewingWithNetherWart && item.getItemStack().getType() == Material.NETHER_WART) {
                                    if (!isAddNetherWart) {
                                        PotionHelper.spawnPotionParticle(this);
                                        PotionHelper.playCauldronAddItemSound(this);
                                        isAddNetherWart = true;
                                        continue;
                                    }
                                }

                                if (ConfigOBJ.config.cauldron.startBrewingWithNetherWart && !isAddNetherWart) {
                                    PotionHelper.playCauldronAddItemSound(this);
                                    continue;
                                }

                                if (ConfigOBJ.config.cauldron.unstableMode.enable) {
                                    boolean unstable = false;
                                    int newDamage = -1;
                                    if (random.nextInt(100) < ConfigOBJ.config.cauldron.unstableMode.failureProbability) {
                                        unstable = true;
                                    } else if (random.nextInt(100) < ConfigOBJ.config.cauldron.unstableMode.invertProbability) {
                                        unstable = true;
                                        newDamage = PotionHelper.getDamageChange(item.getItemStack().getType(), damage, !isInverted);
                                    }
                                    if (unstable) {
                                        PotionHelper.playCauldronAddItemSound(this);
                                        if (ConfigOBJ.config.cauldron.unstableMode.hideUnstableMode) {
                                            if (newDamage != damage || newDamage == -1 || ConfigOBJ.config.cauldron.alwaysShowCauldronParticle) {
                                                PotionHelper.spawnPotionParticle(this);
                                            }
                                        } else {
                                            world.spawnParticle(Particle.ENTITY_EFFECT, block.getLocation().add(0.5, 0.85, 0.5), 30, 0.5, 0.5, 0.5, Color.WHITE);
                                        }
                                        if (newDamage != -1) {
                                            this.damage = newDamage;
                                        }
                                        continue;
                                    }
                                }

                                if (item.getItemStack().getType() == Material.GUNPOWDER) {
                                    if (ConfigOBJ.config.cauldron.alwaysShowCauldronParticle || !isSplash) {
                                        PotionHelper.spawnPotionParticle(this);
                                    }
                                    PotionHelper.playCauldronAddItemSound(this);
                                    isSplash = true;
                                    continue;
                                }

                                if (item.getItemStack().getType() == Material.DRAGON_BREATH) {
                                    if (ConfigOBJ.config.cauldron.alwaysShowCauldronParticle || !isLingering) {
                                        PotionHelper.spawnPotionParticle(this);
                                    }
                                    PotionHelper.playCauldronAddItemSound(this);
                                    isLingering = true;
                                    continue;
                                }

                                for (int i = item.getItemStack().getAmount(); i > 0; i--) {
                                    int newDamage = PotionHelper.getDamageChange(item.getItemStack().getType(), damage, isInverted);
                                    if (ConfigOBJ.config.cauldron.alwaysShowCauldronParticle || newDamage != damage) {
                                        PotionHelper.spawnPotionParticle(this);
                                    }
                                    PotionHelper.playCauldronAddItemSound(this);
                                    this.damage = newDamage;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
