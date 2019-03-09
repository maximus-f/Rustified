package me.perotin.rustified.events;

import me.perotin.rustified.Rustified;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/* Created by Perotin on 3/9/19 */
public class BossBarMoveEvent implements Listener {

    private Rustified plugin;

    public BossBarMoveEvent(Rustified rustified) {
        this.plugin = rustified;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){

    }
}
