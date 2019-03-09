package me.perotin.rustified.objects;

import me.perotin.rustified.files.RustFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* Created by Perotin on 3/9/19 */
public class WorkbenchLocations {

    private static WorkbenchLocations instance;
    private ArrayList<Location> signLocations;

    private WorkbenchLocations(){
        this.signLocations = loadLocations();
        signLocations.forEach(loc -> Bukkit.broadcastMessage(loc.toString()));


    }


    private ArrayList<Location> loadLocations(){
        RustFile file = new RustFile(RustFile.RustFileType.WORKBENCHES);
        ArrayList<Location> loaded = new ArrayList<>();
        for(String key : file.getConfiguration().getConfigurationSection("locations").getKeys(false)){
            Map<String, Object> deserialized = new HashMap<>();
            deserialized.put("world", file.getConfiguration().getString("locations."+key+".world"));
            deserialized.put("x", file.getConfiguration().getString("locations."+key+".x"));
            deserialized.put("y", file.getConfiguration().getString("locations."+key+".y"));
            deserialized.put("z", file.getConfiguration().getString("locations."+key+".z"));
            deserialized.put("pitch", file.getConfiguration().getString("locations."+key+".pitch"));
            deserialized.put("yaw", file.getConfiguration().getString("locations."+key+".yaw"));
            loaded.add(Location.deserialize(deserialized));

        }
        return loaded;
    }
    public void writeToFile(){
        RustFile file = new RustFile(RustFile.RustFileType.WORKBENCHES);

        int x = 0;
        for(Location location : signLocations){
            file.getConfiguration().createSection("locations."+x, location.serialize());
            x++;
        }
        file.save();
    }



    public void add(Location add){
        signLocations.add(add);
    }
    public static WorkbenchLocations getWorkBenchLocations(){
        if(instance == null){
            instance = new WorkbenchLocations();
        }
        return instance;
    }
}
