package me.perotin.rustified.objects;

import me.perotin.rustified.files.RustFile;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/* Created by Perotin on 2/9/19 */
public class RustifiedPlayer {

    private final UUID uuid;
    private String name;
    private ArrayList<BluePrint> blueprints;

    // new player constructor
    public RustifiedPlayer(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.blueprints = new ArrayList<>();
    }

    /**
     * Constructor used for players already saved
     * @param uuid
     * @param name
     * @param blueprints
     */
    public RustifiedPlayer(UUID uuid, String name, ArrayList<BluePrint> blueprints){
        this.uuid = uuid;
        this.name = name;
        this.blueprints = blueprints;
    }

    /**
     * @return uuid of player
     */
    public UUID getUuid() {
        return uuid;
    }


    /**
     * @return name of player
     */
    public String getName() {
        return name;
    }

    /**
     * Only use when player joins and has a new name
     * @param name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return blueprints that player has
     */
    public ArrayList<BluePrint> getBlueprints() {
        return blueprints;
    }

    /**
     *
     * @param blueprint to add to array
     */
    public void addBlueprint(BluePrint blueprint) {
        this.blueprints.add(blueprint);
    }

    /**
     * Save the player to file with all of its blue prints
     */
    public void savePlayer(){
        RustFile file = new RustFile(RustFile.RustFileType.PLAYERS);
        file.set(uuid.toString()+".name", name);
        List<String> stringList = blueprints.stream().map(bp -> bp.getMaterial().toString()).collect(Collectors.toList());
        file.set(uuid.toString()+".blueprints", stringList);
        file.save();

    }

    public boolean isAbleToCraft(Material type){
        List<Material> craftable = blueprints.stream().map(BluePrint::getMaterial).collect(Collectors.toList());
        if(!craftable.isEmpty()){
            for(Material mat : craftable){
                if(mat.equals(type)) return true;
            }
        }
        return false;
    }

}
