package net.violetc.cauldronpotion;

import net.violetc.violetcpluginutil.JsonConfig;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DataOBJ extends JsonConfig<DataOBJ> {

    public static DataOBJ data = new DataOBJ();
    private static File dataFile = null;

    public Map<Material, String> itemPotionData;
    public List<Map<String, Object>> cauldronDataSave;

    @Override
    protected DataOBJ setUp() {

        itemPotionData = randomItemPotionData(ConfigOBJ.config.misc.potionSeed);
        cauldronDataSave = new ArrayList<>();

        return this;
    }

    @NotNull
    private static Map<Material, String> randomItemPotionData(long seed) {
        Random random = new Random(seed);
        Map<Material, String> map = new HashMap<>();

        for (Material material : PotionHelper.potionItems) {
            int number = random.nextInt(4) + 1;
            StringBuilder data = new StringBuilder();

            for (int i = 0; i < number; i++) {
                boolean flag = random.nextInt(8) != 0;
                int index = random.nextInt(16);

                if (data.toString().contains(String.valueOf(index))) {
                    i--;
                    continue;
                }

                data.append(index).append(flag ? "+" : "-");
            }

            map.put(material, data.toString());
        }

        return map;
    }

    public static void initData(File file) {
        if (dataFile == null) {
            dataFile = file;
            data = data.init(dataFile);
        }
    }

    public static void reloadData() {
        if (dataFile != null) {
            data = data.load(dataFile);
        }
    }

    public static void saveData() {
        if (dataFile != null) {
            data.save(dataFile);
        }
    }
}
