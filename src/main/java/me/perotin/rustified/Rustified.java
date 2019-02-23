package me.perotin.rustified;

import me.perotin.rustified.events.*;
import me.perotin.rustified.files.RustFile;
import me.perotin.rustified.objects.BluePrint;
import me.perotin.rustified.objects.RustifiedPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

/* Created by Perotin on 2/9/19 */
public class Rustified extends JavaPlugin {


    /*
    TODO
    1. Interact with workbenches event
    1.2 Workbench creation broken, casting sign to something wrong etc.
    2. DRY code on BluePrintData
    3. Inventory#getName is deprecated, look into sometime.
     */
    private static Rustified instance;
    private HashSet<RustifiedPlayer> players;

    @Override
    public void onEnable(){
        this.players = new HashSet<>();
        instance = this;
        RustFile.loadFiles();
        saveDefaultConfig();
        setup();
        Bukkit.getOnlinePlayers().forEach(Rustified::getPlayerObjectFor);


    }

    // save each player
    @Override
    public void onDisable(){
        players.forEach(RustifiedPlayer::savePlayer);
    }

    private void setup(){
        Bukkit.getServer().getPluginManager().registerEvents(new CraftEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CreateWorkbenchEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new WorkbenchUseEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new WorkbenchMenuClickEvent(), this);

    }

    public HashSet<RustifiedPlayer> getRustPlayers() {
        return players;
    }
    private void addRustPlayer(RustifiedPlayer player){
        players.add(player);
    }

    public static Rustified getInstance(){
        return instance;
    }

    /**
     *
     * @param toFind
     * @return player object
     * @apiNote this method will always return a RustifiedPlayer object by consturcting a new one. It will load
     * a new player in if it is already not stored in mem.
     */
    public static RustifiedPlayer getPlayerObjectFor(Player toFind){
        UUID uuid = toFind.getUniqueId();
        if(!Rustified.getInstance().getRustPlayers().isEmpty()){
            for(RustifiedPlayer player : Rustified.getInstance().getRustPlayers()){
                if(player.getUuid().equals(uuid)) {
                    if(!toFind.getName().equals(player.getName())){
                        player.setName(toFind.getName());
                    }
                    return player;
                }
            }
        }
        // not already loaded
        RustFile file = new RustFile(RustFile.RustFileType.PLAYERS);
        for(String path : file.getConfiguration().getKeys(false)){
            if(path.equals(uuid.toString())){
                String name = file.getConfiguration().getString(path+".name");
                ArrayList<BluePrint> bps = Rustified.getInstance().convertToBluePrintList(file.getConfiguration().getStringList(path +".blueprints"));
                RustifiedPlayer rustifiedPlayer = new RustifiedPlayer(uuid, name, bps);
                Rustified.getInstance().addRustPlayer(rustifiedPlayer);
                return rustifiedPlayer;
            }
        }

        // new player
        RustifiedPlayer newPlayer = new RustifiedPlayer(toFind.getUniqueId(), toFind.getName());
        Rustified.getInstance().addRustPlayer(newPlayer);
        return newPlayer;

    }

    /**
     *
     * @param list of string
     * @return list of blue prints from string
     */
    private ArrayList<BluePrint> convertToBluePrintList(List<String> list){
        ArrayList<BluePrint> bluePrints = new ArrayList<>();
        for(String string : list){
            bluePrints.add(new BluePrint(Material.valueOf(string)));
        }
        return bluePrints;
    }

}
