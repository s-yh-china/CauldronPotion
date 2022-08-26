package net.violetc.cauldronpotion;

import net.violetc.cauldronpotion.cauldron.CauldronEntityManger;
import net.violetc.cauldronpotion.listener.CauldronListener;
import net.violetc.cauldronpotion.listener.DisableOldPotionListener;
import net.violetc.violetcpluginutil.BaseJavaPlugin;

import java.io.File;

public final class CauldronPotion extends BaseJavaPlugin<CauldronPotion> {

    @Override
    public void onEnable() {
        super.onEnable();

        ConfigOBJ.initConfig(new File(getDataFolder() + File.separator + "config.json"));
        DataOBJ.initData(new File(getDataFolder() + File.separator + "data.json"));

        NamespaceSave.init(this);
        CauldronEntityManger.init(this);

        ConfigOBJ.saveConfig();
        DataOBJ.saveData();

        registerListener(new DisableOldPotionListener());
        registerListener(new CauldronListener());

        registerCommands(new CauldronCommand(), "cauldronpotion");
    }

    @Override
    public void onDisable() {
        super.onDisable();

        if (ConfigOBJ.config.saveCauldronDataOnStop) {
            CauldronEntityManger.save();
        }

        ConfigOBJ.saveConfig();
        DataOBJ.saveData();
    }
}
