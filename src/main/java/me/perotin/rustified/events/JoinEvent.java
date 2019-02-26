package me.perotin.rustified.events;

import me.perotin.rustified.Rustified;
import me.perotin.rustified.objects.RustifiedPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/* Created by Perotin on 2/9/19 */
public class JoinEvent implements Listener {


    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player joiner = event.getPlayer();
        RustifiedPlayer rp = Rustified.getPlayerObjectFor(joiner);
        // check for same name
        if(!rp.getName().equals(joiner.getName())){
            rp.setName(joiner.getName());
        }



    }

}
