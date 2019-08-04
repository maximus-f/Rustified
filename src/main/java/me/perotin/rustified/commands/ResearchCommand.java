package me.perotin.rustified.commands;

import me.perotin.rustified.Rustified;
import me.perotin.rustified.objects.BluePrintData;
import me.perotin.rustified.utils.Messages;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/* Created by Perotin on 8/4/19 */
public class ResearchCommand implements CommandExecutor {

    private Rustified plugin;

    public ResearchCommand(Rustified rustified) {
        this.plugin = rustified;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        BluePrintData data = BluePrintData.getSingleton();
        if(sender.hasPermission("rustified.research")){
            // check if holding item in main hand
            // compare if it is a Blue-printable item
            // open villager inv for that specific blueprint
            if(sender instanceof  Player) {
                Player player = (Player) sender;
                if(data.isMaterialBluePrintable(player.getInventory().getItemInMainHand().getType())){
                    // open inventory and trade for certain amount
                }
                // tell them to hold proper item





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
