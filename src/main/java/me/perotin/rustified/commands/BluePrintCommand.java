package me.perotin.rustified.commands;

import me.perotin.rustified.Rustified;
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
            if (sender.hasPermission("rustified.blueprint")) {
                sender.sendMessage(ChatColor.GREEN + "---------------- " + ChatColor.AQUA + "Rustified " + ChatColor.GREEN + "----------------");
                sender.sendMessage(Messages.getMessage("click-msg-copy"));
                Messages.sendMessageTextComponent("list-command", player);



            } else {
                Messages.sendMessage("no-permission", player);
            }
            return true;
        }
        return true;
    }
}
