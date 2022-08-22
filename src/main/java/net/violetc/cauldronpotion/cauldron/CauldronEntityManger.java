package net.violetc.cauldronpotion.cauldron;

import net.violetc.cauldronpotion.ConfigOBJ;
import net.violetc.cauldronpotion.DataOBJ;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CauldronEntityManger {

    private static CauldronEntityManger manger;

    private final Map<Block, CauldronEntity> entities;

    private CauldronEntityManger(JavaPlugin plugin) {
        entities = new HashMap<>();

        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            Iterator<Map.Entry<Block, CauldronEntity>> iterator = entities.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Block, CauldronEntity> entry = iterator.next();
                if (entry.getValue().isRemove()) {
                    iterator.remove();
                } else {
                    entry.getValue().tick();
                }
            }
        }, 1, 1);
    }

    @NotNull
    public CauldronEntity addEntity(@NotNull Block block) {
        if (!entities.containsKey(block)) {
            entities.put(block, new CauldronEntity(block));
        }
        return entities.get(block);
    }

    public void removeEntity(@NotNull Block block) {
        if (entities.containsKey(block)) {
            entities.get(block).remove();
        }
    }

    public boolean hasEntity(@NotNull Block block) {
        return entities.containsKey(block);
    }

    @Nullable
    public CauldronEntity getEntity(@NotNull Block block) {
        return entities.get(block);
    }

    public static void init(JavaPlugin plugin) {
        if (manger == null) {
            manger = new CauldronEntityManger(plugin);
            if (ConfigOBJ.config.saveCauldronDataOnStop) {
                load();
            }
        }
    }

    public static void load() {
        for (Map<String, Object> map : DataOBJ.data.cauldronDataSave) {
            int x = ((Number) map.get("x")).intValue();
            int y = ((Number) map.get("y")).intValue();
            int z = ((Number) map.get("z")).intValue();
            manger.addEntity(Bukkit.getWorld((String) map.get("world")).getBlockAt(x, y, z)).loadMap(map);
        }

        DataOBJ.data.cauldronDataSave.clear();
    }

    public static void save() {
        for (CauldronEntity entity : manger.entities.values()) {
            DataOBJ.data.cauldronDataSave.add(entity.toMap());
        }
    }

    public static CauldronEntityManger getManger() {
        return manger;
    }
}
