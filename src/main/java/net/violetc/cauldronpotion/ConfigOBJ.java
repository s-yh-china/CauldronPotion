package net.violetc.cauldronpotion;

import net.violetc.violetcpluginutil.JsonConfig;
import org.bukkit.Bukkit;

import java.io.File;

public class ConfigOBJ extends JsonConfig<ConfigOBJ> {

    public static ConfigOBJ config = new ConfigOBJ();
    private static File configFile = null;

    public boolean disableBrewingStand;
    public boolean disableOldPotion;

    public boolean displayPotionMeta;
    public boolean displayPotionDamage;

    public boolean alwaysShowCauldronParticle;
    public boolean saveCauldronDataOnStop;
    public boolean disableCauldronMoveByPiston;

    public boolean randomPotionRecipe;

    public long potionSeed;

    @Override
    protected ConfigOBJ setUp() {

        disableBrewingStand = true;
        disableOldPotion = false;

        displayPotionMeta = true;
        displayPotionDamage = false;

        alwaysShowCauldronParticle = false;
        saveCauldronDataOnStop = false;
        disableCauldronMoveByPiston = false;

        randomPotionRecipe = true;

        potionSeed = Bukkit.getWorlds().get(0).getSeed();

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
}
