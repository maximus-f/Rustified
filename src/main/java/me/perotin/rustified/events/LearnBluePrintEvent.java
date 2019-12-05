package me.perotin.rustified.events;

import me.perotin.rustified.Rustified;
import me.perotin.rustified.objects.BluePrint;
import me.perotin.rustified.objects.RustifiedPlayer;
import me.perotin.rustified.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

/* Created by Perotin on 4/6/19 */
public class LearnBluePrintEvent implements Listener {
    private Rustified plugin;

    public LearnBluePrintEvent(Rustified rustified) {
        this.plugin = rustified;
    }

    @EventHandler
    public void on(PlayerInteractEvent event){
        Player clicker = event.getPlayer();
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
            ItemStack holding = clicker.getInventory().getItemInMainHand();
            if(holding != null && holding.getType() != Material.AIR) {
                if(holding.getType() == Material.PAPER && holding.hasItemMeta()){

                    ItemMeta meta = holding.getItemMeta();
                    if(meta.getLore() != null && meta.getLore().get(1) != null) {
                        if(meta.getLore().get(0).equals(Messages.getMessage("blueprint-lore"))) {

                            UUID uuid = UUID.fromString(ChatColor.stripColor(meta.getLore().get(1)));
                            BluePrint print = plugin.getBluePrint(uuid);
                            if(print != null){
                                event.setCancelled(true);

                                RustifiedPlayer player = Rustified.getPlayerObjectFor(clicker);
                                if(!player.isAbleToCraft(print.getMaterial())) {
                                    ResearchClickEvent.consumeItem(clicker, 1, Material.PAPER);
                                    player.addBlueprint(print);
                                    clicker.sendMessage(Messages.getMessage("learned-blueprint")
                                            .replace("$name$", print.getMaterial().toString()));
                                    plugin.activeBluePrints.remove(print);
                                } else {
                                    // already learnt
                                    Messages.sendMessage("already-learned", clicker);
                                }

                            }
                        }

                    }

                }
            }
        }
    }
}
