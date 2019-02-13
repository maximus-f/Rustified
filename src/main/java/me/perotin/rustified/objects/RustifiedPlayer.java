package me.perotin.rustified.objects;

import me.perotin.rustified.files.RustFile;

import java.util.ArrayList;
import java.util.UUID;

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

    public RustifiedPlayer(UUID uuid, String name, ArrayList<BluePrint> blueprints){
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<BluePrint> getBlueprints() {
        return blueprints;
    }

    public void addBlueprint(BluePrint blueprint) {
        this.blueprints.add(blueprint);
    }

    public void savePlayer(){
        RustFile file = new RustFile(RustFile.RustFileType.PLAYERS);
        file.set(uuid.toString()+".name", name);
        file.set(uuid.toString()+".blueprints", blueprints);
        file.save();

    }

}
