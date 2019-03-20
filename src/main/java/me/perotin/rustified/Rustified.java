package me.perotin.rustified;

import me.perotin.rustified.events.*;
import me.perotin.rustified.files.RustFile;
import me.perotin.rustified.objects.BluePrint;
import me.perotin.rustified.objects.BluePrintData;
import me.perotin.rustified.objects.RustifiedPlayer;
import me.perotin.rustified.objects.WorkbenchLocations;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static me.perotin.rustified.events.BossBarMoveEvent.wrappers;

/* Created by Perotin on 2/9/19 */
public class Rustified extends JavaPlugin {


    /*

    Boss bar keeps happening, check not working
    TODO
    1. Make boss bar go away if farther than 10 blocks.
    2. Make deleting workbenches delete them from memory and file

    5. Color of signs are not staying
    Boss Bar is buggy, also file deletion of workbenches
     */
    private static Rustified instance;
    private HashSet<RustifiedPlayer> players;
    @Override
    public void onEnable(){
        instance = this;
        this.players = new HashSet<>();
        BluePrintData.getSingleton();
        RustFile.loadFiles();
        saveDefaultConfig();
        setup();
        Bukkit.getOnlinePlayers().forEach(Rustified::getPlayerObjectFor);
        WorkbenchLocations.getWorkBenchLocations();
        runBarRemovalTask();

    }


     void runBarRemovalTask(){
        new BukkitRunnable(){
            @Override
            public void run() {
                List<BossBarMoveEvent.BarLocationWrapper> removal = new ArrayList<>();
                if (!wrappers.isEmpty()) {
                    for (BossBarMoveEvent.BarLocationWrapper wrapper : wrappers) {
                        UUID uuid = wrapper.getUuid();
                        Validate.notNull(Bukkit.getPlayer(uuid), "Moving player is null!");
                        Player player = Bukkit.getPlayer(uuid);
                        if (wrapper.getLoc().distance(player.getLocation()) > 10) {
                            wrapper.getBar().removePlayer(player);
                        }
                        removal.add(wrapper);

                    }
                    wrappers.removeAll(removal);
                }
            }
        }.runTaskTimer(this, 0, 20*60*3);
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
        Bukkit.getPluginManager().registerEvents(new WorkbenchUseEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new WorkbenchMenuClickEvent(), this);
        Bukkit.getPluginManager().registerEvents(new BossBarMoveEvent(this), this);

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
