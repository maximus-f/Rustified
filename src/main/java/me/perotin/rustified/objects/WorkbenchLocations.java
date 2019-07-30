package me.perotin.rustified.objects;

import me.perotin.rustified.files.RustFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/* Created by Perotin on 3/9/19 */
public class WorkbenchLocations {

    private static WorkbenchLocations instance;
    private ArrayList<Workbench> workbenches;

    private WorkbenchLocations(){
        this.workbenches = loadLocations();


    }

    public List<Location> getSignLocations(){
        return workbenches.stream().map(Workbench::getLocation).collect(Collectors.toList());
    }


    private ArrayList<Workbench> loadLocations(){
        RustFile file = new RustFile(RustFile.RustFileType.WORKBENCHES);
        ArrayList<Workbench> loaded = new ArrayList<>();

       // if(file.getConfiguration().getConfigurationSection("location") != null && !file.getConfiguration().getConfigurationSection("location").getKeys(false).isEmpty()) {
            for (String key : file.getConfiguration().getConfigurationSection("locations").getKeys(false)) {
                Map<String, Object> deserialized = new HashMap<>();
                deserialized.put("world", file.getConfiguration().getString("locations." + key + ".world"));
                deserialized.put("x", file.getConfiguration().getString("locations." + key + ".x"));
                deserialized.put("y", file.getConfiguration().getString("locations." + key + ".y"));
                deserialized.put("z", file.getConfiguration().getString("locations." + key + ".z"));
                deserialized.put("pitch", file.getConfiguration().getString("locations." + key + ".pitch"));
                deserialized.put("yaw", file.getConfiguration().getString("locations." + key + ".yaw"));
                loaded.add(new Workbench(Location.deserialize(deserialized), file.getConfiguration().getInt("locations." + key + ".level")));


            }
       // }

        return loaded;
    }

    public Workbench getWorkbenchWith(Location location) {
        if(workbenches.stream().filter(workbench -> workbench.getLocation().equals(location)).collect(Collectors.toList()).get(0) != null){
            return workbenches.stream().filter(workbench -> workbench.getLocation().equals(location)).collect(Collectors.toList()).get(0);
        }
        return null;
    }
    public void writeToFile(){
        RustFile file = new RustFile(RustFile.RustFileType.WORKBENCHES);
        // clear the file
        file.set("locations", null);
        file.save();

        int x = 0;
        for(Workbench bench : workbenches){
            file.getConfiguration().createSection("locations."+x, bench.getLocation().serialize());
            file.set("locations."+x+".level", bench.getLevel());
            x++;
        }
        file.save();
    }



    public void remove(Workbench remove) {workbenches.remove(remove);}
    public void add(Workbench add){
        workbenches.add(add);
    }
    public static WorkbenchLocations getWorkBenchLocations(){
        if(instance == null){
            instance = new WorkbenchLocations();
        }
        return instance;
    }
}
