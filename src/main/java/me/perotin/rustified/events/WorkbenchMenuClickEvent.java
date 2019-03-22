package me.perotin.rustified.events;

import me.perotin.rustified.Rustified;
import me.perotin.rustified.objects.BluePrint;
import me.perotin.rustified.objects.BluePrintData;
import me.perotin.rustified.objects.RustifiedPlayer;
import me.perotin.rustified.objects.Workbench;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/* Created by Perotin on 2/18/19 */
public class WorkbenchMenuClickEvent implements Listener {

     static Map<UUID, Workbench> inWorkbenchMenu = new HashMap<>();
     static Map<UUID, Merchant> inMenu = new HashMap<>();

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(event.getWhoClicked() instanceof Player){
            Player clicker = (Player) event.getWhoClicked();
            Inventory clicked = event.getView().getTopInventory();

            boolean namesMatch = false;
            for(int x = 1; x < 3; x++){
                if(event.getView().getTitle().equals("Workbench Level: "+ x)) namesMatch = true;
            }

            if(namesMatch){
                // in the inventory
                if(event.getCursor() != null){
                    BluePrintData data = BluePrintData.getSingleton();
                    if(inWorkbenchMenu.keySet().contains(clicker.getUniqueId()) && inMenu.keySet().contains(clicker.getUniqueId())){
                        Workbench bench = inWorkbenchMenu.get(clicker.getUniqueId());
                        Merchant merchant = inMenu.get(clicker.getUniqueId());
                        int level = bench.getLevel();
                        Material material = data.getWorkbenchInputs().get(level).keySet().iterator().next();
                        int amount  = data.getWorkbenchInputs().get(level).get(material);
                        ItemStack cursor = event.getCursor();
                        RustifiedPlayer player = Rustified.getPlayerObjectFor(clicker);

                        BluePrint print = data.getRandomBluePrintFor(player, level);

                        if(event.getSlot() == 0 || event.getSlot() == 1){
                            MerchantRecipe recipe = new MerchantRecipe(print.getItem(), 1);
                            recipe.addIngredient(new ItemStack(material, amount));

                            merchant.getRecipes().add(recipe);
                            if(cursor.getType() == material && cursor.getAmount() >= amount){
                                InventoryType.SlotType slotType = event.getView().getSlotType(2);


                                //setting the item to slot 2, where the recipes would go iirc
                                clicked.setItem(2, print.getItem());

                                //sends
                                Bukkit.broadcastMessage("got here");
                            }
                        }

                    }


                }

            }

        }
    }
}
