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

/* Created by Perotin on 3/9/19 */
public class BossBarMoveEvent implements Listener {

    private Rustified plugin;
    private List<BarLocationWrapper> wrappers = new ArrayList<>();
    public BossBarMoveEvent(Rustified rustified) {
        this.plugin = rustified;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player mover = event.getPlayer();
        WorkbenchLocations locations = WorkbenchLocations.getWorkBenchLocations();
        for(Location location : locations.getSignLocations()){
            if(location.distance(mover.getLocation()) <= 10){
                BossBar bar = Bukkit.createBossBar("Insert 20 Iron Ingots", BarColor.WHITE, BarStyle.SOLID);
                bar.addPlayer(mover);
                wrappers.add(new BarLocationWrapper(bar, location, mover.getUniqueId()));

            }
        }

        // runnable checking whether players are out of range of workbenches, and hence remove the bar
      new BukkitRunnable(){
            @Override
          public void run(){
                List<BarLocationWrapper> removal = new ArrayList<>();
                for(BarLocationWrapper wrapper : wrappers){
                    UUID uuid = wrapper.getUuid();
                     Validate.notNull(Bukkit.getPlayer(uuid), "Moving player is null!");
                     Player player = Bukkit.getPlayer(uuid);
                     if(wrapper.getLoc().distance(player.getLocation()) > 10){
                         wrapper.getBar().removePlayer(player);
                     }
                     removal.add(wrapper);

                }
                wrappers.removeAll(removal);
            }
      }.runTaskTimer(plugin, 0, 20*60*3);


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
}
