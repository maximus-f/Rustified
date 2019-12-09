package me.perotin.rustified.utils;

import me.perotin.rustified.files.RustFile;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

/* Created by Perotin on 2/12/19 */
public class Messages {
    private static RustFile file = new RustFile(RustFile.RustFileType.MESSAGES);
    private Messages(){}

    public static String getMessage(String path){
        String msg =  ChatColor.translateAlternateColorCodes('&', file.getString(path));
        if(msg.contains("$prefix$")) msg = msg.replace("$prefix$", ChatColor.translateAlternateColorCodes('&', file.getString("prefix")));
        return msg;
    }

    public static String getMessage(String path, String placeholder, String newValue){
        String message = file.getString(path).replace(placeholder, newValue);
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> getStringLoreColorized(String path){
        List<String> basic = file.getConfiguration().getStringList(path);
        return basic.stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList());
    }

    public static void sendMessage(String path, CommandSender sender){

        sender.sendMessage(getMessage(path));
    }

    public static void sendMessage(String path, String placeholder, String replace, CommandSender sender){

        sender.sendMessage(getMessage(path).replace(placeholder, replace));
    }


    // Instead of just displaying 'click to copy' make it display somethng pertaining to the command
    public static void sendMessageTextComponent(String path, Player sender){
        String message = getMessage(path);
        TextComponent textComponent = new TextComponent(TextComponent.fromLegacyText(message));
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Messages.getMessage("click-to-copy")).create()));
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, message));
        sender.spigot().sendMessage(textComponent);


    }
}
