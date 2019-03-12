package me.perotin.rustified.events;

import me.perotin.rustified.Rustified;
import me.perotin.rustified.objects.WorkbenchLocations;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

/* Created by Perotin on 3/9/19 */
public class BossBarMoveEvent implements Listener {

    private Rustified plugin;
    private List<BossBar> bars = new ArrayList<>();
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
                bars.add(bar);

            }
        }
        //TODO make boss bar go away if player is more than 10 blocks from
//        for(BossBar bar : bars){
//            while(bar.get)
//        }


    }
}
