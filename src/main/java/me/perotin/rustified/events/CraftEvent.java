package me.perotin.rustified.events;

import me.perotin.rustified.Rustified;
import me.perotin.rustified.objects.BluePrint;
import me.perotin.rustified.objects.RustifiedPlayer;
import me.perotin.rustified.utils.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

/* Created by Perotin on 2/12/19 */
public class CraftEvent implements Listener {

    @EventHandler
    public void onCraft(CraftItemEvent event){
        if(event.getWhoClicked() instanceof Player){
            Player clicker = (Player) event.getWhoClicked();
            if(!clicker.hasPermission("rustified.blueprint.exempt")){
                Recipe recipe = event.getRecipe();
                RustifiedPlayer rustifiedPlayer = Rustified.getPlayerObjectFor(clicker);
                if(!rustifiedPlayer.getBlueprints().contains(new BluePrint(recipe.getResult().getType()))){
                    // cannot craft it!
                    event.setCancelled(true);
                    for(ItemStack item : event.getInventory().getMatrix()){
                        clicker.getInventory().addItem(item);
                    }
                    clicker.closeInventory();
                    String msg = Messages.getMessage("crafting-denied");
                    if(msg.contains("/")){
                        String first = msg.split("/")[0];
                        String second = msg.split("/")[1];
                        clicker.sendMessage(first);
                        clicker.sendMessage(second);
                        return;
                    }
                    clicker.sendMessage(Messages.getMessage("crafting-denied"));
                }

            }
        }
    }
}
