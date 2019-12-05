package me.perotin.rustified.objects;

import me.perotin.rustified.Rustified;
import me.perotin.rustified.utils.ItemBuilder;
import me.perotin.rustified.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

/* Created by Perotin on 2/18/19 */

/**
 * Container class for static information
 */
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
    /**
     * @apiNote outer map is level of workbench, inner map key is material of item used as input for the workbench and inner inner value is the amount of items needed for input.
     */
    private Map<Integer, LinkedHashMap<Material, Integer>> workbenchInputs;


    private BluePrintData() {
        Rustified plugin = Rustified.getInstance();
        FileConfiguration config = plugin.getConfig();
        this.levelOneBluePrints = convertStringListToBluePrint2(config.getStringList("level-1-items"));
        this.levelTwoBluePrints = convertStringListToBluePrint2(config.getStringList("level-2-items"));
        this.levelThreeBluePrints = convertStringListToBluePrint2(config.getStringList("level-3-items"));
        this.workbenchComponents = new HashMap<>();
        this.workbenchInputs = new LinkedHashMap<>();

        //TODO finish inputs loading from config


        try {
            for (int x = 1; x < 4; x++) {
                Map<Material, Material> innerMap1 = new HashMap<>();
                innerMap1.put(Material.valueOf(config.getString("level-" + x)), Material.valueOf(config.getString("level-" + x + "-item")));
                workbenchComponents.put(x, innerMap1);

                LinkedHashMap<Material, Integer> inputMapInner = new LinkedHashMap<>();
                inputMapInner.put(Material.valueOf(config.getString("level-" + x + "-item")), config.getInt("level-" + x + "-amount"));
                workbenchInputs.put(x, inputMapInner);

            }
        } catch (IllegalArgumentException ex) {
            Bukkit.getLogger().severe("Invalid material name for workbench materials! Check config.yml and make sure you are using the right names: https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html");
            ex.printStackTrace();
            Bukkit.getLogger().severe("Shutting plugin down. Please fix the material names in your config.yml");
            Bukkit.getPluginManager().disablePlugin(Rustified.getInstance());
        }


    }


    /**
     * @param player           to give map
     * @param levelOfWorkbench to get potential maps from
     * @return a new map
     */
    public BluePrint getRandomBluePrintFor(RustifiedPlayer player, int levelOfWorkbench) {
        List<BluePrint> potentialBluePrints = getBluePrintForLevel(levelOfWorkbench);
        // sort already-owned blueprints and remove them to retrieve a unique, new blueprint
        List<BluePrint> toRemove = new ArrayList<>();
        if (!player.getBlueprints().isEmpty()) {
            for (BluePrint map : player.getBlueprints()) {
                for (BluePrint map2 : potentialBluePrints) {
                    if (map.equals(map2)) {
                        toRemove.add(map);
                    }
                }
            }
        }
        potentialBluePrints.removeAll(toRemove);
        Random random = new Random();
        return potentialBluePrints.get(random.nextInt(potentialBluePrints.size()));


    }

    /**
     * @return map with level of workbench and map of material needed for input and value of how much of said item
     */
    public Map<Integer, LinkedHashMap<Material, Integer>> getWorkbenchInputs() {
        return workbenchInputs;
    }

    /**
     * @param level of workbench you want the potential blue prints for
     * @return list of blueprints
     */
    public List<BluePrint> getBluePrintForLevel(int level) {
        switch (level) {
            case 1:
                return levelOneBluePrints;
            case 2:
                return levelTwoBluePrints;
            case 3:
                return levelThreeBluePrints;
            default:
                return null;
        }

    }


    private List<String> bluePrintsForDisplay(int level, RustifiedPlayer player){
        List<String> display = new ArrayList<>();
        List<Material> printsObtained = player.getBlueprints().stream().map(BluePrint::getMaterial).collect(Collectors.toList());
        if(level == 1){
            for(BluePrint print : levelOneBluePrints){
                if(printsObtained.contains(print.getMaterial())) {
                    display.add(ChatColor.WHITE + "- " + ChatColor.GREEN + print.getMaterial().toString());
                } else {
                    display.add(ChatColor.WHITE + "- " + ChatColor.YELLOW + print.getMaterial().toString());
                }
            }
            return display;
        } else if (level == 2){
            for(BluePrint print : levelTwoBluePrints){
                if(printsObtained.contains(print.getMaterial())) {
                    display.add(ChatColor.WHITE + "- " + ChatColor.GREEN + print.getMaterial().toString());
                } else {
                    display.add(ChatColor.WHITE + "- " + ChatColor.YELLOW + print.getMaterial().toString());
                }            }
            return display;
        } else if(level == 3){
            for(BluePrint print : levelThreeBluePrints){
                if(printsObtained.contains(print.getMaterial())) {
                    display.add(ChatColor.WHITE + "- " + ChatColor.GREEN + print.getMaterial().toString());
                } else {
                    display.add(ChatColor.WHITE + "- " + ChatColor.YELLOW + print.getMaterial().toString());
                }            }
            return display;
        } else {
            return display;
        }
    }


    public void showInfoInventory(Player toShow) {
        Rustified plugin = Rustified.getInstance();
        RustifiedPlayer rustifiedPlayer = Rustified.getPlayerObjectFor(toShow);
        FileConfiguration config = plugin.getConfig();
        Inventory gui = Bukkit.createInventory(null, 27, plugin.getConfig().getString("info-gui-name"));
        String levelBench = Messages.getMessage("level-bench");
        String levelBenchLore = Messages.getMessage("level-bench-lore");
        String levelInput = Messages.getMessage("level-input");
        String levelInputAmount = Messages.getMessage("level-input-amount");
        String levelItemsBp = Messages.getMessage("level-items-to-bp");
        gui.setItem(0, new ItemBuilder(Material.CHEST_MINECART).setName(Messages.getMessage("how-to-build")).setLore(Messages.getStringLoreColorized("how-to-build-lore")).toItemStack());
        for (int x = 1; x < 4; x++) {
            Material benchType = Material.valueOf(config.getString("level-" + x));
            Material benchInputType = Material.valueOf(config.getString("level-" + x + "-item"));
            int benchInputAmount = config.getInt("level-" + x + "-amount");
            if (x == 1) {
                gui.setItem(3, new ItemBuilder(benchType).setName(levelBench.replace("$level$", x+"")).setLore(levelBenchLore.replace("$type$", benchType.toString())).toItemStack());
                gui.setItem(12, new ItemBuilder(benchInputType).setName(levelInput.replace("$type$", benchInputType.toString())).setLore(levelInputAmount.replace("$amount$", benchInputAmount+"")).toItemStack() );
                gui.setItem(21, new ItemBuilder(Material.CRAFTING_TABLE).setName(levelItemsBp).setLore(bluePrintsForDisplay(x, rustifiedPlayer)).toItemStack());
            } else if (x == 2) {
                gui.setItem(4, new ItemBuilder(benchType).setName(levelBench.replace("$level$", x+"")).setLore(levelBenchLore.replace("$type$", benchType.toString())).toItemStack());
                gui.setItem(13, new ItemBuilder(benchInputType).setName(levelInput.replace("$type$", benchInputType.toString())).setLore(levelInputAmount.replace("$amount$", benchInputAmount+"")).toItemStack() );
                gui.setItem(22, new ItemBuilder(Material.CRAFTING_TABLE).setName(levelItemsBp).setLore(bluePrintsForDisplay(x, rustifiedPlayer)).toItemStack());
            } else {
                gui.setItem(5, new ItemBuilder(benchType).setName(levelBench.replace("$level$", x+"")).setLore(levelBenchLore.replace("$type$", benchType.toString())).toItemStack());
                gui.setItem(14, new ItemBuilder(benchInputType).setName(levelInput.replace("$type$", benchInputType.toString())).setLore(levelInputAmount.replace("$amount$", benchInputAmount+"")).toItemStack() );
                gui.setItem(23, new ItemBuilder(Material.CRAFTING_TABLE).setName(levelItemsBp).setLore(bluePrintsForDisplay(x, rustifiedPlayer)).toItemStack());
            }
        }
        toShow.openInventory(gui);


    }

    /**
     * Gets the level of the workbench based off of the material blocks
     *
     * @param type
     * @return
     */
    public int getLevelForWorkbench(Material type) {
        if (getWorkbenchComponents().get(1).keySet().contains(type)) {
            return 1;
        }
        if (getWorkbenchComponents().get(2).keySet().contains(type)) {
            return 2;
        }
        if (getWorkbenchComponents().get(3).keySet().contains(type)) {
            return 3;
        }
        return 0;
    }


    /**
     * Returns a map with level and inner key material for workbench material and inner value of item used for workbench
     *
     * @return
     */
    public Map<Integer, Map<Material, Material>> getWorkbenchComponents() {
        return this.workbenchComponents;
    }

    /**
     * @return list of materials that workbench blocks can be made out of
     */
    public List<Material> getWorkbenchBlocks() {
        List<Material> potentialMaterials = new ArrayList<>();
        potentialMaterials.addAll(getWorkbenchComponents().get(1).keySet());
        potentialMaterials.addAll(getWorkbenchComponents().get(2).keySet());
        potentialMaterials.addAll(getWorkbenchComponents().get(3).keySet());
        return potentialMaterials;
    }

    /**
     * Get the level of workbench needed to research a blue print
     *
     * @param bp
     * @return
     */
    public int getLevelFor(Material bp) {
        if (levelThreeBluePrints.stream().map(BluePrint::getMaterial).collect(Collectors.toList()).contains(bp))
            return 3;
        if (levelTwoBluePrints.stream().map(BluePrint::getMaterial).collect(Collectors.toList()).contains(bp))
            return 2;
        if (levelOneBluePrints.stream().map(BluePrint::getMaterial).collect(Collectors.toList()).contains(bp))
            return 1;
        return 0;
    }

    /**
     * Returns a boolean of whether a material requires a blue print
     *
     * @param type
     * @return
     */
    public boolean isMaterialBluePrintable(Material type) {
        for (Material material : convertToMaterial(levelThreeBluePrints)) {
            if (material == type) {
                return true;
            }
        }
        for (Material material : convertToMaterial(levelTwoBluePrints)) {
            if (material == type) {
                return true;
            }
        }
        for (Material material : convertToMaterial(levelOneBluePrints)) {
            if (material == type) {
                return true;
            }
        }
        return false;

    }


    /**
     * converts a List of blue prints to a list of material
     *
     * @param bluePrintList
     * @return
     */
    private List<Material> convertToMaterial(List<BluePrint> bluePrintList) {
        List<Material> materials = new ArrayList<>();
        for (BluePrint print : bluePrintList) {
            materials.add(print.getMaterial());
        }
        return materials;
    }

    /**
     * @param convert list of strings to convert to a blueprint collection
     * @return arraylist of converted strings
     */
    private ArrayList<BluePrint> convertStringListToBluePrint2(List<String> convert) {
        ArrayList<BluePrint> bluePrintArrayList = new ArrayList<>();
        for (String s : convert) {
            if (Material.getMaterial(s) != null) {
                bluePrintArrayList.add(new BluePrint(Material.getMaterial(s)));
            } else {
                Bukkit.getLogger().log(Level.WARNING, "Invalid material name '" + s + "' entered in Rustified/config.yml! To see proper names visit https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html\n" +
                        "level");
            }
        }
        return bluePrintArrayList;
    }


    /**
     * @return singleton instance
     */
    public static BluePrintData getSingleton() {
        if (singleton == null) {
            singleton = new BluePrintData();
        }
        return singleton;
    }


}
