package me.perotin.rustified.objects;

import me.perotin.rustified.utils.Messages;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *  Class that will be added to a RustifiedPlayer object that will be used in checking for blue print requirements.
 */
/* Created by Perotin on 2/9/19 */
public class BluePrint implements ConfigurationSerializable  {

    private Material type;
    private ItemStack item;


    /**
     * @param type of material to make a blueprint for
     */
    public BluePrint(Material type) {
        this.type = type;
        this.item = new ItemStack(Material.MAP);
        ItemMeta meta = item.getItemMeta();
        // will eventually make this more user friendly by parsing it or something
        meta.setDisplayName(Messages.getMessage("blueprint-item", "$name$", type.name()));
        meta.setLore(Collections.singletonList(Messages.getMessage("blueprint-lore")));
        item.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
    }

    /**
     * @return material for blueprint
     */
    public Material getMaterial(){
        return this.type;
    }

    /**
     *
     * @param values
     * @return Blue print from serialized map
     */
    public static BluePrint valueOf(Map<String, Object> values){
        return new BluePrint(Material.valueOf((String) values.get("type")));
    }


    /**
     *
     * @param print to compare
     * @return boolean if map is equal to other map
     */
    public boolean equals(BluePrint print){
        if(type.equals(print.getMaterial())){
            return true;
        }
        return false;
    }
    /**
     * Give a player the blue print
     * @param toGive
     */
    public void giveTo(Player toGive){
        toGive.getInventory().addItem(item);
    }


    /**
     *
     * @return serialized map
     */
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", type.toString());
        return map;
    }

    //TODO get random blue print, might need to re-evaluate the ownership of this method
    public static BluePrint getRandomBluePrint(){
        return new BluePrint(Material.AIR);
    }
}
