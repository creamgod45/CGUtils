package cg.creamgod45;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;

public final class CGUtils extends JavaPlugin {
    public static JavaPlugin plugin;
    public static FileConfiguration fileconfig;
    public static ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

    @Override
    public void onEnable() {
        plugin = this;

        Utils.updatecheck();
        fileconfig = this.getConfig();
        ConfigReader.load();

        console.sendMessage(ConfigReader.on_enable);

    }

    @Override
    public void onLoad() {
        File config = new File("plugins/CGInvisibleItemFrame/config.yml");
        fileconfig = this.getConfig();

        if (!(config.exists())){
            Bukkit.getServer().getConsoleSender().sendMessage(ConfigReader.on_load);
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }else ConfigReader.load();

        Utils.update_config();
    }


    @Override
    public void onDisable() {
        this.getServer().getConsoleSender().sendMessage(ConfigReader.on_disable);
        if(!ConfigReader.using_custom_message){
            this.getServer().getConsoleSender().sendMessage(Utils.format(ConfigReader.Prefix + "&b&l謝謝你使用我的插件 :)"));
        }else{
            this.getServer().getConsoleSender().sendMessage(Utils.format(ConfigReader.Prefix + "&b&lThank you for using my plugin :)"));
        }
    }


}
