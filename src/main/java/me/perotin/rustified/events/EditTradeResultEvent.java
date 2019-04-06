package me.perotin.rustified.events;

import me.perotin.rustified.Rustified;
import me.perotin.rustified.utils.Messages;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;

/* Created by Perotin on 4/5/19 */
public class EditTradeResultEvent implements Listener {
    private Rustified plugin;
    public EditTradeResultEvent(Rustified rustified) {
        this.plugin = rustified;
    }

    @EventHandler
    public void on(InventoryClickEvent event){
        if(event.getWhoClicked() instanceof  Player){
            Player trader = (Player) event.getWhoClicked();
            if ((event.getInventory() != null) && (event.getInventory().getType() == InventoryType.MERCHANT)) {
                MerchantInventory merchantInventory = (MerchantInventory) event.getInventory();
                 MerchantRecipe villagerTradeMeta = merchantInventory.getSelectedRecipe();
                if (villagerTradeMeta.getResult().getType() == Material.PAPER) {
                    ItemMeta meta = villagerTradeMeta.getResult().getItemMeta();
                    meta.setDisplayName(Messages.getMessage("blueprint-item", "$name$", villagerTradeMeta.getResult().getType().name()));
                    villagerTradeMeta.getResult().setItemMeta(meta);
                }
            }
        }

    }
}
