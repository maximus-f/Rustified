package me.perotin.rustified.objects;

import java.util.ArrayList;
import java.util.UUID;

/* Created by Perotin on 2/9/19 */
public class RustifiedPlayer {

    private final UUID uuid;
    private String name;
    private ArrayList<Blueprint> blueprints;

    // new player constructor
    public RustifiedPlayer(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.blueprints = new ArrayList<Blueprint>();
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

    public ArrayList<Blueprint> getBlueprints() {
        return blueprints;
    }

    public void addBlueprint(Blueprint blueprint) {
        this.blueprints.add(blueprint);
    }

}
