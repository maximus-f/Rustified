package me.perotin.rustified.events;

import me.perotin.rustified.Rustified;
import me.perotin.rustified.objects.WorkbenchLocations;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;

/* Created by Perotin on 3/9/19 */
public class BossBarMoveEvent implements Listener {

    private Rustified plugin;
    public static List<BarLocationWrapper> wrappers = new ArrayList<>();
    public BossBarMoveEvent(Rustified rustified) {
        this.plugin = rustified;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player mover = event.getPlayer();
        WorkbenchLocations locations = WorkbenchLocations.getWorkBenchLocations();
        for(Location location : locations.getSignLocations()){
            if(location.distance(mover.getLocation()) <= 10){
                if(!isDuplicateBar(location, mover)) {
                    Bukkit.broadcastMessage("123");
                    BossBar bar = Bukkit.createBossBar("Insert 20 Iron Ingots", BarColor.WHITE, BarStyle.SOLID);
                    bar.addPlayer(mover);
                    wrappers.add(new BarLocationWrapper(bar, location, mover.getUniqueId()));
                }

            }
        }




    }

    /**
     * Wrapper to store location of workbench and the boss bar object
     */
    public class BarLocationWrapper{
        private final BossBar bar;
        private final Location loc;
        private final UUID uuid;

       public BarLocationWrapper(BossBar bar, Location loc, UUID uuid) {
           this.bar = bar;
           this.loc = loc;
           this.uuid = uuid;
       }

       public UUID getUuid(){
           return uuid;
       }
       public BossBar getBar() {
           return bar;
       }

       public Location getLoc() {
           return loc;
       }
   }

   private boolean isDuplicateBar(Location locOfBench, Player player){
        boolean isDuplicate = false;
        for(BarLocationWrapper wrapper : wrappers){
            Bukkit.broadcastMessage("23");
            if(player.getUniqueId().equals(wrapper.getUuid())) Bukkit.broadcastMessage("1");
            if(wrapper.getLoc().equals(locOfBench)) Bukkit.broadcastMessage("@");
            if(wrapper.getLoc().equals(locOfBench) && player.getUniqueId().equals(wrapper.getUuid())) {
                isDuplicate = true;
            }

            }
            return isDuplicate;
        }
}
