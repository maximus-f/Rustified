package me.perotin.rustified.objects;

import me.perotin.rustified.utils.Messages;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 *  Class that will be added to a RustifiedPlayer object that will be used in checking for blue print requirements.
 */
/* Created by Perotin on 2/9/19 */
public class BluePrint   {

    private Material type;
    private ItemStack item;
    private final UUID uuid;


    /**
     * @param type of material to make a blueprint for
     */
    public BluePrint(Material type) {
        this.type = type;
        this.item = new ItemStack(Material.PAPER);
        this.uuid = UUID.randomUUID();
        ItemMeta meta = item.getItemMeta();
        // will eventually make this more user friendly by parsing it or something
        meta.setDisplayName(Messages.getMessage("blueprint-item", "$name$", type.name()));
        meta.setLore(Arrays.asList(Messages.getMessage("blueprint-lore"), ChatColor.GRAY + getUuid().toString()));
        item.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
    }


    public UUID getUuid() {
        return uuid;
    }

    public ItemStack getItem() {
        ItemMeta meta = item.getItemMeta();
        // will eventually make this more user friendly by parsing it or something
        meta.setDisplayName(Messages.getMessage("blueprint-item", "$name$", type.name()));

        item.setItemMeta(meta);
        return item;
    }

    /**
     * @return item with hidden name for viewing in workbench
     */
    public ItemStack getItemHidden(){
        ItemMeta meta = item.getItemMeta();
        // will eventually make this more user friendly by parsing it or something
        meta.setDisplayName(Messages.getMessage("blueprint-item", "$name$", "???"));

        item.setItemMeta(meta);
        return item;
    }

    /**
     * @return material for blueprint
     */
    public Material getMaterial(){
        return this.type;
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


    //TODO get random blue print, might need to re-evaluate the ownership of this method
    public static BluePrint getRandomBluePrint(){
        return new BluePrint(Material.AIR);
    }
}
