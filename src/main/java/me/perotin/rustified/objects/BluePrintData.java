package me.perotin.rustified.objects;

import me.perotin.rustified.Rustified;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

/* Created by Perotin on 2/18/19 */
public class BluePrintData {

    private static BluePrintData singleton;

    private List<BluePrint> levelOneBluePrints;
    private List<BluePrint> levelTwoBluePrints;
    private List<BluePrint> levelThreeBluePrints;
    /**
     * @apiNote outer map is the level of workbench, inner key is the material used to make the workbench and inner inner value is type of items used to use
     * the workbench
     */
    private Map<Integer, Map<Material, Material>> workbenchComponents;


    private BluePrintData(){
        Rustified plugin = Rustified.getInstance();
        FileConfiguration config = plugin.getConfig();
        this.levelOneBluePrints = convertStringListToBluePrint(config.getStringList("level-1-items"));
        this.levelTwoBluePrints = convertStringListToBluePrint(config.getStringList("level-2-items"));
        this.levelThreeBluePrints = convertStringListToBluePrint(config.getStringList("level-3-items"));
        this.workbenchComponents = new HashMap<>();
        try {
            // violating DRY, try to fix
            Map<Material, Material> innerMap1 = new HashMap<>();
            innerMap1.put(Material.valueOf(config.getString("level-1")), Material.valueOf(config.getString("level-1-item")));
            workbenchComponents.put(1, innerMap1);

            Map<Material, Material> innerMap2 = new HashMap<>();
            innerMap1.put(Material.valueOf(config.getString("level-2")), Material.valueOf(config.getString("level-2-item")));
            workbenchComponents.put(2, innerMap2);

            Map<Material, Material> innerMap3 = new HashMap<>();
            innerMap1.put(Material.valueOf(config.getString("level-3")), Material.valueOf(config.getString("level-3-item")));
            workbenchComponents.put(3, innerMap3);
        } catch (IllegalArgumentException ex){
            Bukkit.getLogger().severe("Invalid material name for workbench materials! Check config.yml and make sure you are using the right names: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html");
            ex.printStackTrace();
            Bukkit.getLogger().severe("Shutting plugin down. Please fix the material names in your config.yml");
            Bukkit.getPluginManager().disablePlugin(Rustified.getInstance());
        }

    }

    /**
     * @param level of workbench you want the potential blue prints for
     * @return list of blueprints
     */
    public List<BluePrint> getBluePrintForLevel(int level){
        switch (level) {
            case 1: return levelOneBluePrints;
            case 2: return levelTwoBluePrints;
            case 3: return levelThreeBluePrints;
            default: return null;
        }

    }

    public int getLevelForWorkbench(Material type){
        if(getWorkbenchComponents().get(1).keySet().contains(type)){
            return 1;
        } if(getWorkbenchComponents().get(2).keySet().contains(type)){
            return 2;
        }if(getWorkbenchComponents().get(3).keySet().contains(type)){
            return 3;
        }
        return 0;
    }



    public Map<Integer, Map<Material, Material>> getWorkbenchComponents(){
        return this.workbenchComponents;
    }

    public List<Material> getWorkbenchBlocks(){
        List<Material> potentialMaterials = new ArrayList<>();
        potentialMaterials.addAll(getWorkbenchComponents().get(1).keySet());
        potentialMaterials.addAll(getWorkbenchComponents().get(2).keySet());
        potentialMaterials.addAll(getWorkbenchComponents().get(3).keySet());
        return potentialMaterials;
    }

    public int getLevelFor(BluePrint bp){
        if(levelThreeBluePrints.stream().map(BluePrint::getMaterial).collect(Collectors.toList()).contains(bp.getMaterial())) return 3;
        if(levelTwoBluePrints.stream().map(BluePrint::getMaterial).collect(Collectors.toList()).contains(bp.getMaterial())) return 2;
        if(levelOneBluePrints.stream().map(BluePrint::getMaterial).collect(Collectors.toList()).contains(bp.getMaterial())) return 1;
        return 0;

    }
    public boolean isMaterialBluePrintable(Material type){
        for(Material material : convertToMaterial(levelThreeBluePrints)){
            if(material == type){
                return true;
            }
        }
        for(Material material : convertToMaterial(levelTwoBluePrints)){
            if(material == type){
                return true;
            }
        }
        for(Material material : convertToMaterial(levelOneBluePrints)){
            if(material == type){
                return true;
            }
        }
        return false;

    }


    private List<Material> convertToMaterial(List<BluePrint> bluePrintList){
        List<Material> materials = new ArrayList<>();
        for(BluePrint print : bluePrintList){
            materials.add(print.getMaterial());
        }
        return materials;
    }
    /**
     *
     * @param convert list of strings to convert to a blueprint collection
     * @return arraylist of converted strings
     */
    private ArrayList<BluePrint> convertStringListToBluePrint(List<String> convert){
        ArrayList<BluePrint> bluePrintArrayList = new ArrayList<>();
        for(String s : convert){
            Material toAdd;
            try {
                 toAdd = Material.valueOf(s);
            } catch (IllegalArgumentException ex){
                Bukkit.getLogger().log(Level.WARNING, "Invalid material name '"+s+"' entered in Rustified/config.yml! To see proper names visit https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html\n" +
                        "level");
                ex.printStackTrace();
                continue;
            }
            BluePrint bp = new BluePrint(toAdd);
            bluePrintArrayList.add(bp);
        }
        return bluePrintArrayList;
    }

    public static BluePrintData getSingleton(){
        if(singleton == null){
            singleton = new BluePrintData();
        }
        return singleton;
    }
}
