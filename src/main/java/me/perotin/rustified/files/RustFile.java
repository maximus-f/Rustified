package me.perotin.rustified.files;

import me.perotin.rustified.Rustified;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;


import static me.perotin.rustified.files.RustFile.RustFileType.MESSAGES;
import static me.perotin.rustified.files.RustFile.RustFileType.PLAYERS;
import static me.perotin.rustified.files.RustFile.RustFileType.WORKBENCHES;


public class RustFile {

    private File file;
    private FileConfiguration configuration;
    private RustFileType type;

    public RustFile(RustFileType type) {
        // hard-coding some stuff to make our lives easier, since we'll be using
        // files A LOT so it is important we have a good system set in place to easily retreive these things

        if (type == MESSAGES) {
            file = new File(Rustified.getInstance().getDataFolder(), "messages.yml");
            configuration = YamlConfiguration.loadConfiguration(file);
            this.type = MESSAGES;
        }
        if (type == PLAYERS) {
            file = new File(Rustified.getInstance().getDataFolder(), "players.yml");
            configuration = YamlConfiguration.loadConfiguration(file);
            this.type = PLAYERS;
        }
        if (type == WORKBENCHES) {
            file = new File(Rustified.getInstance().getDataFolder(), "workbenches.yml");
            configuration = YamlConfiguration.loadConfiguration(file);
            this.type = WORKBENCHES;
        }




    }

    public void save() {
        try {
            configuration.save(file);
        } catch (IOException ex) {
            ex.printStackTrace();
            ;
        }
    }

    // some generic methods to speed up the process

    public FileConfiguration getConfiguration() {
        return configuration;
    }

    public Object get(String path) {
        return configuration.get(path);
    }

    public void set(String path, Object value) {
        configuration.set(path, value);
    }

    public String getString(String path) {
        return ChatColor.translateAlternateColorCodes('&', configuration.getString(path));
    }

    public void load() {

        File lang = null;
        InputStream defLangStream = null;

        switch (type) {
            case MESSAGES:
                lang = new File(Rustified.getInstance().getDataFolder(), "messages.yml");
                defLangStream = Rustified.getInstance().getResource("messages.yml");
                break;
            case PLAYERS:
                lang = new File(Rustified.getInstance().getDataFolder(), "players.yml");
                defLangStream = Rustified.getInstance().getResource("players.yml");
                break;
            case WORKBENCHES:
                lang = new File(Rustified.getInstance().getDataFolder(), "workbenches.yml");
                defLangStream = Rustified.getInstance().getResource("workbenches.yml");
                break;

        }
        OutputStream out = null;
        if (!lang.exists()) {
            try {
                Rustified.getInstance().getDataFolder().mkdir();
                lang.createNewFile();
                if (defLangStream != null) {
                    out = new FileOutputStream(lang);
                    int read;
                    byte[] bytes = new byte[1024];

                    while ((read = defLangStream.read(bytes)) != -1) {
                        out.write(bytes, 0, read);
                    }
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace(); // So they notice
                Bukkit.getLogger().severe("[Rustified] Couldn't create " + type.toString().toLowerCase() + " file.");
                Bukkit.getLogger().severe("[Rustified] This is a fatal error. Now disabling");
                Rustified.getInstance().getPluginLoader().disablePlugin(Rustified.getInstance()); // Without
                // it
                // loaded,
                // we
                // can't
                // send
                // them
                // messages
            } finally {
                if (defLangStream != null) {
                    try {
                        defLangStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    public static void loadFiles(){
        Rustified.getInstance().saveResource("players.yml", false);
        Rustified.getInstance().saveResource("messages.yml", false);
        Rustified.getInstance().saveResource("workbenches.yml", false);

        for(RustFileType type : RustFileType.values()){
            new RustFile(type).load();
        }
    }


    public enum RustFileType {

        PLAYERS, MESSAGES, WORKBENCHES

    }








}