package me.perotin.rustified.events;

import me.perotin.rustified.Rustified;
import me.perotin.rustified.objects.BluePrint;
import me.perotin.rustified.objects.BluePrintData;
import me.perotin.rustified.objects.Workbench;
import me.perotin.rustified.objects.WorkbenchLocations;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;

import java.util.Collections;

/* Created by Perotin on 2/18/19 */
public class WorkbenchUseEvent implements Listener {


    private Rustified plugin;

    public WorkbenchUseEvent(Rustified plugin){
        this.plugin = plugin;
    }


    @EventHandler
    public void onInteractEvent(PlayerInteractEvent event){
        Player clicker = event.getPlayer();
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK){
            Block block = event.getClickedBlock();
            if(block != null){
                if(block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN) {
                    Sign s = (Sign) block.getState();
                    org.bukkit.material.Sign sd = (org.bukkit.material.Sign) s.getData();
                    Block against = block.getRelative(sd.getAttachedFace());
                    BluePrintData data = BluePrintData.getSingleton();
                    if (data.getWorkbenchBlocks().contains(against.getType())){
                        // potential workbench

                        BlockFace[] possibleLocations = {BlockFace.DOWN, BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.UP, BlockFace.SOUTH};
                        for(BlockFace face : possibleLocations){
                            if(against.getRelative(face).getType() == against.getType()){
                                // tis a workbench

                               // Inventory menu = Bukkit.createInventory(null, InventoryType.MERCHANT, "Workbench Level: "+ data.getLevelForWorkbench(against.getType()));

                                Workbench bench = WorkbenchLocations.getWorkBenchLocations().getWorkbenchWith(s.getLocation());
                                Merchant menu = Bukkit.createMerchant("Workbench Level: " + data.getLevelForWorkbench(against.getType()));
                               // menu.setRecipes();
                                BluePrint print = data.getRandomBluePrintFor(Rustified.getPlayerObjectFor(clicker), bench.getLevel());

                                Material material = data.getWorkbenchInputs().get(bench.getLevel()).keySet().iterator().next();
                                int amount  = data.getWorkbenchInputs().get(bench.getLevel()).get(material);
                                MerchantRecipe recipe =  new MerchantRecipe(print.getItemHidden(), 0, 999, false);

                                recipe.addIngredient(new ItemStack(material, amount));
                                menu.setRecipes(Collections.singletonList(recipe));

                                clicker.openMerchant(menu, false);

                            }
                        }

                    }
                }
            }
        }
    }
}
