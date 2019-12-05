package me.perotin.rustified.commands;

import me.perotin.rustified.Rustified;
import me.perotin.rustified.objects.BluePrint;
import me.perotin.rustified.objects.BluePrintData;
import me.perotin.rustified.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;

import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

/* Created by Perotin on 8/4/19 */
public class ResearchCommand implements CommandExecutor {

    private Rustified plugin;
    public static HashMap<UUID, String> researching = new HashMap<>();

    public ResearchCommand(Rustified rustified) {
        this.plugin = rustified;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        BluePrintData data = BluePrintData.getSingleton();
        if(sender.hasPermission("rustified.research")){
            if(sender instanceof  Player) {
                Player player = (Player) sender;
                if(data.isMaterialBluePrintable(player.getInventory().getItemInMainHand().getType())){
                    Material type = player.getInventory().getItemInMainHand().getType();
                    // open inventory and trade for certain amount
                    // see which level the material is for
                    int level = data.getLevelFor(type);
                    String title = "Researching: " + type.toString();
                    Merchant menu = Bukkit.createMerchant(title);
                    BluePrint print = new BluePrint(type);
                    Material material = data.getWorkbenchInputs().get(level).keySet().iterator().next();
                    int amount = data.getWorkbenchInputs().get(level).get(material);
                    MerchantRecipe recipe =  new MerchantRecipe(print.getItem(), 0, 999, false);
                    recipe.addIngredient(new ItemStack(material, amount));
                    menu.setRecipes(Collections.singletonList(recipe));
                    plugin.activeBluePrints.add(print);
                    player.openMerchant(menu, false);
                    researching.put(player.getUniqueId(), title);
                } else {
                    // tell them to hold proper item

                    Messages.sendMessage("hold-item-to-research", sender);
                    return true;
                }
            } else {
                sender.sendMessage("Must be player!");
                return true;
            }

        } else {
            Messages.sendMessage("no-permission", sender);

        }

        return true;
    }
}
