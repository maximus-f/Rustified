package me.perotin.rustified.utils;

import me.perotin.rustified.files.RustFile;
import org.bukkit.ChatColor;

/* Created by Perotin on 2/12/19 */
public class Messages {
    private static RustFile file = new RustFile(RustFile.RustFileType.MESSAGES);
    private Messages(){}

    public static String getMessage(String path){
        return ChatColor.translateAlternateColorCodes('&', file.getString(path));
    }

    public static String getMessage(String path, String placeholder, String newValue){
        String message = file.getString(path).replace(placeholder, newValue);
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
