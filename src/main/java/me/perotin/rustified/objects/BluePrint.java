package me.perotin.rustified.objects;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 *  Class that will be added to a RustifiedPlayer object that will be used in checking for blue print requirements.
 */
/* Created by Perotin on 2/9/19 */
public class BluePrint implements ConfigurationSerializable  {

    private Material type;


    public BluePrint(Material type) {
        this.type = type;
    }

    public Material getMaterial(){
        return this.type;
    }

    public static BluePrint valueOf(Map<String, Object> values){
        return new BluePrint(Material.valueOf((String) values.get("type")));
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type.toString());
        return map;
    }
}
