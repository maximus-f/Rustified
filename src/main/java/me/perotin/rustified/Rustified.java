package me.perotin.rustified;
import org.bukkit.plugin.java.JavaPlugin;

/* Created by Perotin on 2/9/19 */
public class Rustified extends JavaPlugin {


    private static Rustified instance;

    @Override
    public void onEnable(){
        instance = this;
        saveResource("players.yml", false);
    }

    public static Rustified getInstance(){
        return instance;
    }

}
