package me.perotin.rustified;
import me.perotin.rustified.files.RustFile;
import me.perotin.rustified.objects.BluePrint;
import me.perotin.rustified.objects.RustifiedPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

/* Created by Perotin on 2/9/19 */
public class Rustified extends JavaPlugin {


    private static Rustified instance;
    private HashSet<RustifiedPlayer> players;

    @Override
    public void onEnable(){
        this.players = new HashSet<>();
        instance = this;
        saveResource("players.yml", false);
        saveResource("messages.yml", false);
        RustFile.loadFiles();

    }

    @Override
    public void onDisable(){
        for(RustifiedPlayer rp : players){
            rp.savePlayer();
        }


    }

    public HashSet<RustifiedPlayer> getRustPlayers() {
        return players;
    }
    public void addRustPlayer(RustifiedPlayer player){
        players.add(player);
    }

    public static Rustified getInstance(){
        return instance;
    }

    public static RustifiedPlayer getPlayerObjectFor(Player toFind){
        UUID uuid = toFind.getUniqueId();
        if(!Rustified.getInstance().getRustPlayers().isEmpty()){
            for(RustifiedPlayer player : Rustified.getInstance().getRustPlayers()){
                if(player.getUuid().equals(uuid)) return player;
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

    private ArrayList<BluePrint> convertToBluePrintList(List<String> list){
        ArrayList<BluePrint> bluePrints = new ArrayList<>();
        for(String string : list){
            bluePrints.add(new BluePrint(Material.valueOf(string)));
        }
        return bluePrints;
    }

}
