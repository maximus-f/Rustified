package me.perotin.rustified.objects;

import me.perotin.rustified.files.RustFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;

/* Created by Perotin on 3/9/19 */
public class WorkbenchLocations {

    private static WorkbenchLocations instance;
    private ArrayList<Location> signLocations;

    private WorkbenchLocations(){
        this.signLocations = new ArrayList<>();
        RustFile file = new RustFile(RustFile.RustFileType.WORKBENCHES);
        for(String key : file.getConfiguration().getConfigurationSection("locations").getKeys(false)){
            Bukkit.broadcastMessage(key);

        }

    }

    public void writeToFile(){
        RustFile file = new RustFile(RustFile.RustFileType.WORKBENCHES);

        signLocations.forEach(location -> file.getConfiguration().set("locations", location));
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
