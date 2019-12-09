package me.perotin.rustified.objects;

import me.perotin.rustified.files.RustFile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/* Created by Perotin on 12/8/19 */
public class WorkbenchMenu {

    private String title;
    private int level;
    private Inventory gui;
    private ItemStack bluePrint;
    private ItemStack trade;

    public WorkbenchMenu(ItemStack bluePrint, ItemStack trade, int level) {
        RustFile messages = new RustFile(RustFile.RustFileType.MESSAGES);
        this.level = level;
        this.title = "Workbench Level: " + level;
        this.gui = Bukkit.createInventory(null, 9, title);
        this.bluePrint = bluePrint;
        this.trade = trade;
        ItemMeta tradeMeta = trade.getItemMeta();
        tradeMeta.setDisplayName(messages.getString("insert-item"));
        tradeMeta.setLore(Arrays.asList(messages.getString("insert-item-lore")
        .replace("$amount$", trade.getAmount()+"").replace("$item$", trade.getType().toString())));
        setGui();
    }

    public Inventory getGui() {
        return gui;
    }

    public String getTitle() {
        return title;
    }

    private void setGui(){
        RustFile messages = new RustFile(RustFile.RustFileType.MESSAGES);
        ItemStack deco = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemStack tradeAccept = new ItemStack(Material.TOTEM_OF_UNDYING);
        ItemMeta tradeMeta = tradeAccept.getItemMeta();
        tradeMeta.setDisplayName(messages.getString("click-to-accept"));
        tradeMeta.setLore(Arrays.asList(messages.getString("click-accept-lore")));
        tradeAccept.setItemMeta(tradeMeta);
        ItemMeta decoMeta = deco.getItemMeta();
        decoMeta.setDisplayName("");
        deco.setItemMeta(decoMeta);
        gui.setItem(0, deco);
        gui.setItem(1, deco);
        gui.setItem(2, trade);
        gui.setItem(7, deco);
        gui.setItem(8, deco);
        gui.setItem(6, deco);
        gui.setItem(5, bluePrint);
        gui.setItem(4, tradeAccept);
    }

    public void show(Player show){
        show.openInventory(gui);
    }


}
