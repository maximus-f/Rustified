package me.perotin.rustified.commands;

import me.perotin.rustified.Rustified;
import me.perotin.rustified.files.RustFile;
import me.perotin.rustified.objects.BluePrint;
import me.perotin.rustified.objects.BluePrintData;
import me.perotin.rustified.objects.RustifiedPlayer;
import me.perotin.rustified.utils.Messages;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.stream.Collectors;

/* Created by Perotin on 4/28/19 */
public class BluePrintCommand implements CommandExecutor {

    private Rustified plugin;

    public BluePrintCommand(Rustified rustified) {
        this.plugin = rustified;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            RustifiedPlayer rustifiedPlayer = Rustified.getPlayerObjectFor(player);
            RustFile messages = new RustFile(RustFile.RustFileType.MESSAGES);
            if (sender.hasPermission("rustified.blueprint")) {
                if(args.length == 0) {
                    sender.sendMessage(ChatColor.GREEN + "---------------- " + ChatColor.AQUA + "Rustified " + ChatColor.GREEN + "----------------");
                    sender.sendMessage(Messages.getMessage("click-msg-copy"));
                    Messages.sendMessageTextComponent("blueprint-help", "/blueprint help", player);
                    Messages.sendMessageTextComponent("blueprint-list", "/blueprint list", player);
                } else if(args.length == 1){
                    String arg1 = args[0];
                    if(arg1.equalsIgnoreCase("list")){
                        //display list
                        List<String> bluePrints = rustifiedPlayer.getBlueprints().stream().map(bp -> bp.getMaterial().toString()).collect(Collectors.toList());
                        String message = messages.getString("blueprint-list-message");
                        message = message.replace("$prefix$", messages.getString("prefix")).replace("$amount$", bluePrints.size()+"");
                        String holder = "";
                        for(String print : bluePrints){
                            holder = print +", ";
                        }
                       holder = holder.trim().substring(0, holder.length());
                        player.sendMessage(message.replace("$list$", holder));
                    } else if(arg1.equalsIgnoreCase("help")){
                        List<String> helpMessages = messages.getConfiguration().getStringList("blueprint-help-message");
                        int counter = 0;
                        for(String s : helpMessages){
                            if(counter == 0){
                                counter++;
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
                            } else {
                                new BukkitRunnable(){
                                    @Override
                                    public void run(){
                                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', s));

                                    }
                                }.runTaskLater(plugin, 20*3);
                            }

                        }
                    } else if(arg1.equalsIgnoreCase("info")){
                        // display GUI with details about server
                        BluePrintData.getSingleton().showInfoInventory(player);
                    }
                }



            } else {
                Messages.sendMessage("no-permission", player);
            }
            return true;
        }
        return true;
    }
}
