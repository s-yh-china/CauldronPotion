package net.violetc.cauldronpotion;

import net.violetc.violetcpluginutil.JsonConfig;
import org.bukkit.Bukkit;

import java.io.File;

public class ConfigOBJ extends JsonConfig<ConfigOBJ> {

    public static ConfigOBJ config = new ConfigOBJ();
    private static File configFile = null;

    public DisableConfigPart disable;
    public DisplayConfigPart display;
    public CauldronConfigPart cauldron;
    public MiscConfigPart misc;
    public int configVersion;

    @Override
    protected ConfigOBJ setUp() {
        disable = new DisableConfigPart();
        display = new DisplayConfigPart();
        cauldron = new CauldronConfigPart();
        misc = new MiscConfigPart();
        configVersion = 1;
        return this;
    }

    public static void initConfig(File file) {
        if (configFile == null) {
            configFile = file;
            config = config.init(configFile);
        }
    }

    public static void reloadConfig() {
        if (configFile != null) {
            config = config.load(configFile);
        }
    }

    public static void saveConfig() {
        if (configFile != null) {
            config.save(configFile);
        }
    }

    public static class DisableConfigPart {
        public boolean disableBrewingStand = false;
        public boolean disableOldPotion = false;
    }

    public static class DisplayConfigPart {
        public boolean displayPotionMeta = true;
        public boolean displayPotionDamage = false;
    }

    public static class CauldronConfigPart {
        public boolean alwaysShowCauldronParticle = false;
        public boolean saveCauldronDataOnStop = true;
        public boolean disableCauldronMoveByPiston = true;
        public boolean enablePotionArrow = false;
        public boolean potionArrowNeedLingeringPotion = false;
        public boolean startBrewingWithNetherWart = true;
        public boolean randomPotionRecipe = true;
        public UnstableModeConfigPart unstableMode = new UnstableModeConfigPart();
    }

    public static class UnstableModeConfigPart {
        public boolean enable = false;
        public boolean hideUnstableMode = false;

        public int failureProbability = 10;
        public int invertProbability = 20;
    }

    public static class MiscConfigPart {
        public boolean giveAdvancement = true;
        public long potionSeed = Bukkit.getWorlds().get(0).getSeed();
    }
}
