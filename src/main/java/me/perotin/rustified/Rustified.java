package me.perotin.rustified;

import me.perotin.rustified.commands.BluePrintCommand;
import me.perotin.rustified.commands.ResearchCommand;
import me.perotin.rustified.events.*;
import me.perotin.rustified.files.RustFile;
import me.perotin.rustified.objects.BluePrint;
import me.perotin.rustified.objects.BluePrintData;
import me.perotin.rustified.objects.RustifiedPlayer;
import me.perotin.rustified.objects.WorkbenchLocations;
import me.perotin.rustified.utils.Metrics;
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
    2. Make deleting workbenches delete them from memory and file
    4. Make a /blueprint command that lists blueprints and does other thigns

    5. Color of signs are not staying
    also file deletion of workbenches


    4/06/19 Multiple errors, but if you ignore em the blue prints kinda work... Just do fine tuning and more testing

    12/05/19 - Yeeeah big jump lol... doing 1.14 support and working so far, for some reason when I took two bp's out the second one didn't work tho
     */
    private static Rustified instance;
    private HashSet<RustifiedPlayer> players;
    public  List<BluePrint> activeBluePrints;
    @Override
    public void onEnable(){
        instance = this;
        this.players = new HashSet<>();
        activeBluePrints = new ArrayList<>();
        saveDefaultConfig();
        RustFile.loadFiles();
        setup();
        Bukkit.getOnlinePlayers().forEach(Rustified::getPlayerObjectFor);
        WorkbenchLocations.getWorkBenchLocations();
        BluePrintData.getSingleton();
        new Metrics(this, 19456);


    }



    // save each player
    @Override
    public void onDisable(){
        players.forEach(RustifiedPlayer::savePlayer);
        WorkbenchLocations.getWorkBenchLocations().writeToFile();

    }

    private void setup(){
        Bukkit.getServer().getPluginManager().registerEvents(new CraftEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CreateWorkbenchEvent(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new BreakWorkbenchEvent(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CancelInfoClick(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ResearchClickEvent(this), this);



        Bukkit.getServer().getPluginManager().registerEvents(new LearnBluePrintEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new WorkbenchUseEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new WorkbenchClickEvent(this), this);

        getCommand("blueprint").setExecutor(new BluePrintCommand(this));
        getCommand("research").setExecutor(new ResearchCommand(this));

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

    public BluePrint getBluePrint(UUID uuid){
        for(BluePrint print : activeBluePrints){
            if(print.getUuid().equals(uuid)){
                return print;
            }
        }
        return null;
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
