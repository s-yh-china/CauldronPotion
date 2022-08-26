package net.violetc.cauldronpotion;

import net.violetc.cauldronpotion.cauldron.CauldronEntity;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PotionHelper {

    private static final String[] potionPrefixes = new String[]{
            "优雅的", // 0
            "混沌的", // 1
            "轻巧的", // 2
            "末影的", // 3
            "错误的", // 4
            "浓稠的", // 5
            "恋恋的", // 6
            "世俗的", // 7
            "无趣的", // 8
            "清淡的", // 9
            "清澈的", // 10
            "浑浊的", // 11
            "弥漫的", // 12
            "自然的", // 13
            "空灵的", // 14
            "尴尬的", // 15
            "平凡的", // 16
            "笨重的", // 17
            "笨拙的", // 18
            "平滑的", // 19
            "温和的", // 20
            "快乐的", // 21
            "沉重的", // 22
            "精致的", // 23
            "波光粼粼的", // 24
            "亲切的", // 25
            "烈性的", // 26
            "无味的", // 27
            "难闻的", // 28
            "刺鼻的", // 29
            "诡异的", // 30
    };

    public static final Material[] potionItems = new Material[]{
            Material.REDSTONE,
            Material.GLOWSTONE_DUST,
            Material.RABBIT_FOOT,
            Material.GOLDEN_CARROT,
            Material.GLISTERING_MELON_SLICE,
            Material.MAGMA_CREAM,
            Material.BLAZE_POWDER,
            Material.FERMENTED_SPIDER_EYE,
            Material.GHAST_TEAR,
            Material.PHANTOM_MEMBRANE,
            Material.WITHER_ROSE,
            Material.SUGAR,
            Material.HONEYCOMB,
            Material.GLOW_INK_SAC,
            Material.SPIDER_EYE,
            Material.POISONOUS_POTATO,
            Material.GLOW_BERRIES,
            Material.TROPICAL_FISH,
            Material.SCUTE,
            Material.PUFFERFISH,
            Material.EGG,
            Material.TURTLE_EGG,
            Material.BLAZE_ROD,
    };

    public static final Material[] potionUnDamageItems = new Material[]{
            Material.NETHER_WART,
            Material.GUNPOWDER,
            Material.DRAGON_BREATH,
            // Material.GLASS_BOTTLE,
    };

    private static final List<Material> potionItemList = new ArrayList<>() {{
        addAll(Arrays.asList(potionItems));
        addAll(Arrays.asList(potionUnDamageItems));
    }};

    public static final String waterPotionData = "0-2-4-6-8-10-12-";

    public static final Map<PotionEffectType, String> potionRequirements = new HashMap<>() {{
        put(PotionEffectType.SPEED, "!10 & !4 & 5*2+0 & >1 | !7 & !4 & 5*2+0 & >1");
        put(PotionEffectType.SLOW, "10 & 7 & !4 & 7+5+1-0");
        put(PotionEffectType.FAST_DIGGING, "2 & 12+2+6-1-7 & <8");
        put(PotionEffectType.SLOW_DIGGING, "!2 & !1*2-9 & 14-5");
        put(PotionEffectType.INCREASE_DAMAGE, "9 & 3 & 9+4+5 & <11");
        put(PotionEffectType.HEAL, "11 & <6");
        put(PotionEffectType.HARM, "!11 & 1 & 10 & !7");
        put(PotionEffectType.JUMP, "8 & 2+0 & <5");
        put(PotionEffectType.CONFUSION, "8*2-!7+4-11 & !2 | 13 & 11 & 2*3-1-5");
        put(PotionEffectType.REGENERATION, "!14 & 13*3-!0-!5-8");
        put(PotionEffectType.DAMAGE_RESISTANCE, "10 & 4 & 10+5+6 & <9");
        put(PotionEffectType.FIRE_RESISTANCE, "14 & !5 & 6-!1 & 14+13+12");
        put(PotionEffectType.WATER_BREATHING, "0+1+12 & !6 & 10 & !11 & !13");
        put(PotionEffectType.INVISIBILITY, "2+5+13-0-4 & !7 & !1 & >5");
        put(PotionEffectType.BLINDNESS, "9 & !1 & !5 & !3 & =3");
        put(PotionEffectType.NIGHT_VISION, "8*2-!7 & 5 & !0 & >3");
        put(PotionEffectType.HUNGER, ">4>6>8-3-8+2");
        put(PotionEffectType.WEAKNESS, "=1>5>7>9+3-7-2-11 & !10 & !0");
        put(PotionEffectType.POISON, "12+9 & !13 & !0");
        // TODO start remake
        put(PotionEffectType.WITHER, "11+8 & 13 & 0");
        put(PotionEffectType.HEALTH_BOOST, "9 & <3");
        put(PotionEffectType.ABSORPTION, "!3 & 2*3-7 & 11-1");
        put(PotionEffectType.SATURATION, "5 & 6 & 8+5+6 & <9");
        put(PotionEffectType.GLOWING, "8*2-7 & !5 & 0 & <3");
        put(PotionEffectType.LEVITATION, "6 & !5 & 0+4-10");
        put(PotionEffectType.LUCK, "!9 & 4 & 11 & !7");
        put(PotionEffectType.UNLUCK, "9 & !4 & !11 & 7");
        put(PotionEffectType.SLOW_FALLING, "!6 & 5 & 0-4+10");
        put(PotionEffectType.CONDUIT_POWER, "14 & 5+1 & <7");
        put(PotionEffectType.DOLPHINS_GRACE, "11+10 & 14 & !0 & 12-5");
        put(PotionEffectType.DARKNESS, "8*2+7 & !5 & 0 & <3");
        // TODO end remake
    }};

    public static final Map<PotionEffectType, String> potionAmplifiers = new HashMap<>() {{
        put(PotionEffectType.SPEED, "7+!3-!1");
        put(PotionEffectType.SLOW_DIGGING, "1+0-!11");
        put(PotionEffectType.INCREASE_DAMAGE, "2+7-!12");
        put(PotionEffectType.HEAL, "11+!0-!1-!14");
        put(PotionEffectType.HARM, "!11-!14+!0-!1");
        put(PotionEffectType.DAMAGE_RESISTANCE, "12-!2");
        put(PotionEffectType.POISON, "14>5");
        // TODO start remake
        put(PotionEffectType.WITHER, "13>6");
        put(PotionEffectType.ABSORPTION, "8-!1-3");
        put(PotionEffectType.LEVITATION, "!1+!13");
        put(PotionEffectType.LUCK, "9+!6-!3-!11");
        put(PotionEffectType.UNLUCK, "!9+6-3-11");
        // TODO end remake
    }};

    public static int getNumberInBinaryDamage(int damage, int number0, int number1, int number2, int number3, int number4) {
        String s = getFormatBinaryString(damage);
        return (checkFlag(s, number0) ? 16 : 0) |
                (checkFlag(s, number1) ? 8 : 0) |
                (checkFlag(s, number2) ? 4 : 0) |
                (checkFlag(s, number3) ? 2 : 0) |
                (checkFlag(s, number4) ? 1 : 0);
    }

    public static boolean checkFlag(int damage, int index) {
        return checkFlag(Integer.toBinaryString(damage), index);
    }

    public static boolean checkFlag(@NotNull String damage, int index) {
        return damage.length() > index && damage.charAt(index) == '1';
    }

    public static Color getPotionColor(int damage) {
        int red = (getNumberInBinaryDamage(damage, 2, 14, 11, 8, 5) ^ 3) << 3;
        int green = (getNumberInBinaryDamage(damage, 12, 3, 1, 7, 9) ^ 6) << 3;
        int blue = (getNumberInBinaryDamage(damage, 13, 10, 4, 1, 7) ^ 8) << 3;
        return Color.fromBGR(blue, green, red);
    }

    public static int getPrefixNumber(int damage) {
        return getNumberInBinaryDamage(damage, 14, 9, 7, 3, 2) % 30; // cycle
    }

    public static String getPotionPrefix(int damage) {
        int number = getPrefixNumber(damage);
        return potionPrefixes[number];
    }

    public static boolean isBrewingItem(@NotNull ItemStack item) {
        return potionItemList.contains(item.getType());
    }

    public static int getDamageChange(@NotNull Material material, int damage) {
        if (material == Material.NETHER_WART) {
            return getLiquidDataChangeWithNetherWarts(damage);
        }

        if (ConfigOBJ.config.randomPotionRecipe) {
            return parsePotionItemData(DataOBJ.data.itemPotionData.get(material), damage);
        }

        return damage;
    }

    public static int parsePotionItemData(@NotNull String data, int damage) {
        StringBuilder damageString = new StringBuilder(getFormatBinaryString(damage));

        StringBuilder value = new StringBuilder();
        boolean flag = false;
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            if (c == '+' || c == '-') {
                flag = c == '+';
                if (value.length() > 0) {
                    damageString.setCharAt(Integer.parseInt(value.toString()), flag ? '1' : '0');
                    value = new StringBuilder();
                }
            } else {
                value.append(c);
            }
        }

        return Integer.parseInt(damageString.toString(), 2);
    }

    @NotNull
    public static String getFormatBinaryString(int damage) {
        StringBuilder string = new StringBuilder(Integer.toBinaryString(damage));
        for (int i = 16 - string.length(); i > 0; i--) {
            string.insert(0, "0");
        }
        return string.toString();
    }

    private static boolean aCalculationOfBoolean(int liquidData, int var1) {
        return (liquidData & 1 << var1 % 15) != 0;
    }

    public static int aCalculationOfNetherWart1(int liquidData) {
        if ((liquidData & 1) == 0) {
            return liquidData;
        } else {
            int var1;
            for (var1 = 16; (liquidData & 1 << var1) == 0 && var1 >= 0; --var1) {

            }

            if (var1 >= 2 && (liquidData & 1 << var1 - 1) == 0) {
                if (var1 >= 0) {
                    liquidData &= ~(1 << var1);
                }

                liquidData <<= 1;
                if (var1 >= 0) {
                    liquidData |= 1 << var1;
                    liquidData |= 1 << var1 - 1;
                }

                return liquidData & 32767;
            } else {
                return liquidData;
            }
        }
    }

    public static int aCalculationOfNetherWart2(int liquidData) {
        int var1;
        for (var1 = 16; (liquidData & 1 << var1) == 0 && var1 >= 0; --var1) {
        }

        if (var1 >= 0) {
            liquidData &= ~(1 << var1);
        }

        int var2 = 0;

        for (int var3 = liquidData; var3 != var2; liquidData = var2) {
            var3 = liquidData;
            var2 = 0;

            for (int var4 = 0; var4 < 15; ++var4) {
                boolean var5 = aCalculationOfBoolean(liquidData, var4);
                if (var5) {
                    if (!aCalculationOfBoolean(liquidData, var4 + 1) && aCalculationOfBoolean(liquidData, var4 + 2)) {
                        var5 = false;
                    } else if (aCalculationOfBoolean(liquidData, var4 - 1) && aCalculationOfBoolean(liquidData, var4 - 2)) {
                        var5 = false;
                    }
                } else {
                    var5 = aCalculationOfBoolean(liquidData, var4 - 1) && aCalculationOfBoolean(liquidData, var4 + 1);
                }

                if (var5) {
                    var2 |= 1 << var4;
                }
            }
        }

        if (var1 >= 0) {
            var2 |= 1 << var1;
        }

        return var2 & 32767;
    }

    public static int getLiquidDataChangeWithNetherWarts(int liquidData) {
        if ((liquidData & 1) != 0) {
            liquidData = aCalculationOfNetherWart1(liquidData);
        }

        return aCalculationOfNetherWart2(liquidData);
    }

    private static int parsePotionRecipe(String string, int n, int n2, int n3) {
        if (n >= string.length() || n2 < 0 || n >= n2) {
            return 0;
        }
        int n4 = string.indexOf(124, n);
        if (n4 >= 0 && n4 < n2) {
            int n5 = PotionHelper.parsePotionRecipe(string, n, n4 - 1, n3);
            if (n5 > 0) {
                return n5;
            }
            int n6 = PotionHelper.parsePotionRecipe(string, n4 + 1, n2, n3);
            if (n6 > 0) {
                return n6;
            }
            return 0;
        }
        int n7 = string.indexOf(38, n);
        if (n7 >= 0 && n7 < n2) {
            int n8 = PotionHelper.parsePotionRecipe(string, n, n7 - 1, n3);
            if (n8 <= 0) {
                return 0;
            }
            int n9 = PotionHelper.parsePotionRecipe(string, n7 + 1, n2, n3);
            if (n9 <= 0) {
                return 0;
            }
            if (n8 > n9) {
                return n8;
            }
            return n9;
        }
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        boolean bl5 = false;
        int n10 = -1;
        int n11 = 0;
        int n12 = 0;
        int n13 = 0;
        for (int i = n; i < n2; ++i) {
            char c = string.charAt(i);
            if (c >= '0' && c <= '9') {
                if (bl) {
                    n12 = c - 48;
                    bl2 = true;
                    continue;
                }
                n11 *= 10;
                n11 += c - 48;
                bl3 = true;
                continue;
            }
            if (c == '*') {
                bl = true;
                continue;
            }
            if (c == '!') {
                if (bl3) {
                    n13 += PotionHelper.a(bl4, bl2, bl5, n10, n11, n12, n3);
                    bl4 = false;
                    bl5 = false;
                    bl = false;
                    bl2 = false;
                    bl3 = false;
                    n12 = 0;
                    n11 = 0;
                    n10 = -1;
                }
                bl4 = true;
                continue;
            }
            if (c == '-') {
                if (bl3) {
                    n13 += PotionHelper.a(bl4, bl2, bl5, n10, n11, n12, n3);
                    bl4 = false;
                    bl5 = false;
                    bl = false;
                    bl2 = false;
                    bl3 = false;
                    n12 = 0;
                    n11 = 0;
                    n10 = -1;
                }
                bl5 = true;
                continue;
            }
            if (c == '=' || c == '<' || c == '>') {
                if (bl3) {
                    n13 += PotionHelper.a(bl4, bl2, bl5, n10, n11, n12, n3);
                    bl4 = false;
                    bl5 = false;
                    bl = false;
                    bl2 = false;
                    bl3 = false;
                    n12 = 0;
                    n11 = 0;
                    n10 = -1;
                }
                if (c == '=') {
                    n10 = 0;
                    continue;
                }
                if (c == '<') {
                    n10 = 2;
                    continue;
                }
                if (c != '>') continue;
                n10 = 1;
                continue;
            }
            if (c != '+' || !bl3) continue;
            n13 += PotionHelper.a(bl4, bl2, bl5, n10, n11, n12, n3);
            bl4 = false;
            bl5 = false;
            bl = false;
            bl2 = false;
            bl3 = false;
            n12 = 0;
            n11 = 0;
            n10 = -1;
        }
        if (bl3) {
            n13 += PotionHelper.a(bl4, bl2, bl5, n10, n11, n12, n3);
        }
        return n13;
    }

    private static int a(boolean bl, boolean bl2, boolean bl3, int n, int n2, int n3, int n4) {
        int n5 = 0;
        if (bl) {
            n5 = PotionHelper.d(n4, n2);
        } else if (n != -1) {
            if (n == 0 && PotionHelper.h(n4) == n2) {
                n5 = 1;
            } else if (n == 1 && PotionHelper.h(n4) > n2) {
                n5 = 1;
            } else if (n == 2 && PotionHelper.h(n4) < n2) {
                n5 = 1;
            }
        } else {
            n5 = PotionHelper.c(n4, n2);
        }
        if (bl2) {
            n5 *= n3;
        }
        if (bl3) {
            n5 *= -1;
        }
        return n5;
    }

    private static int h(int n) {
        int n2 = 0;
        while (n > 0) {
            n &= n - 1;
            ++n2;
        }
        return n2;
    }

    private static boolean b(int n, int n2) {
        return (n & 1 << n2) != 0;
    }

    private static int c(int n, int n2) {
        return PotionHelper.b(n, n2) ? 1 : 0;
    }

    private static int d(int n, int n2) {
        return PotionHelper.b(n, n2) ? 0 : 1;
    }

    @NotNull
    public static List<PotionEffect> getPotionEffect(int damage) {
        List<PotionEffect> list = new ArrayList<>();

        for (PotionEffectType type : PotionEffectType.values()) {
            int duration;
            String string;

            if (type == null || (string = potionRequirements.get(type)) == null || (duration = PotionHelper.parsePotionRecipe(string, 0, string.length(), damage)) <= 0) {
                continue;
            }

            int amplifier = 0;
            String string2 = potionAmplifiers.get(type);
            if (string2 != null && (amplifier = PotionHelper.parsePotionRecipe(string2, 0, string2.length(), damage)) < 0) {
                amplifier = 0;
            }

            if (type.isInstant()) {
                duration = 1;
            } else {
                duration = 1200 * (duration * 3 + (duration - 1) * 2);

            }

            list.add(new PotionEffect(type, duration, amplifier));
        }

        return list;
    }

    public static void spawnPotionParticle(@NotNull CauldronEntity entity) {
        spawnPotionParticle(entity.getWorld(), getPotionColor(entity.getDamage()), entity.getBlock().getLocation().add(0.5, 0.85, 0.5));
    }

    public static void spawnPotionParticle(@NotNull World world, @NotNull Color color, @NotNull Location location) {
        world.spawnParticle(Particle.SPELL_MOB, location, 10, color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, 1);
    }

    public static void playCauldronAddItemSound(@NotNull CauldronEntity entity) {
        playCauldronAddItemSound(entity.getWorld(), entity.getBlock().getLocation());
    }

    public static void playCauldronAddItemSound(@NotNull World world, @NotNull Location location) {
        world.playSound(location, Sound.BLOCK_POINTED_DRIPSTONE_DRIP_WATER_INTO_CAULDRON, 2.5F, 1.0F);
    }
}
