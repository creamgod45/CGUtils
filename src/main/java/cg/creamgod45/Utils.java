package cg.creamgod45;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class Utils {
    public static ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

    public static Boolean HasPermission(Player player, String Permission){
        if(Permission == null){Permission = "" ;}
        if(player.hasPermission("CGCommandToGui.admin")) return true;
        if(player.isOp()) return true;

        return player.hasPermission(Permission);
    }

    public static String format(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String decolor(String string) {
        return string.replaceAll("&1|&2|&3|&4|&5|&6|&7|&8|&9|&a|&b|&c|&d|&e|&f|&l|&m|&n|&o|&r", "");
    }

    public static Player getPlayer(String player_name) {
        List<Player> player_list = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (Player player : player_list) {
            if (player.getName().equals(player_name)) {
                return player;
            }
        }

        return null;
    }

    public static String arraytostring(String[] array, int start){
        if(array==null) return "null";
        if(start<0) return "null";
        array = Arrays.copyOfRange(array, start, array.length);
        return String.join(" ",array);
    }

    public static void copy(File sourceLocation, File targetLocation) throws IOException {
        if (sourceLocation.isDirectory()) {
            copyDirectory(sourceLocation, targetLocation);
        } else {
            copyFile(sourceLocation, targetLocation);
        }
    }

    private static void copyDirectory(File source, File target) throws IOException {
        if (!target.exists()) {
            target.mkdir();
        }

        for (String f : source.list()) {
            copy(new File(source, f), new File(target, f));
        }
    }

    private static void copyFile(File source, File target) throws IOException {
        try (
                InputStream in = new FileInputStream(source);
                OutputStream out = new FileOutputStream(target)) {
            byte[] buf = new byte[1024];
            int length;
            while ((length = in.read(buf)) > 0) {
                out.write(buf, 0, length);
            }
        }
    }



    public static void update_config () {
        console.sendMessage("");
        console.sendMessage("");
        console.sendMessage("");
        SimpleDateFormat date = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        String timeStamp = date.format(new Date());

        File oldconfig = new File("plugins/CGInvisibleItemFrame/config.yml");
        File backup = new File("plugins/CGInvisibleItemFrame/backups/");
        File newconfig = new File("plugins/CGInvisibleItemFrame/backups/config_bak_" + timeStamp + ".yml");
        File lock = new File("plugins/CGInvisibleItemFrame/config.lock");
        try {
            if(backup.mkdirs() || backup.exists()){
                if(ConfigReader.update_config){
                    copy(oldconfig,newconfig);
                    console.sendMessage(ConfigReader.file_configbak_copyed + "config_bak_" + timeStamp + ".yml");
                    if(oldconfig.delete()){
                        console.sendMessage(ConfigReader.file_config_deleted);
                        CGUtils.plugin.getConfig().options().copyDefaults(true);
                        CGUtils.plugin.saveDefaultConfig();
                        console.sendMessage(ConfigReader.on_load);
                    }
                    if(!lock.exists() && lock.createNewFile()){
                        console.sendMessage(ConfigReader.file_config_lock);

                    }
                    return;
                }else if(lock.exists()){
                    console.sendMessage(ConfigReader.file_config_locked);
                }else{
                    copy(oldconfig,newconfig);
                    console.sendMessage(ConfigReader.file_configbak_copyed + "config_bak_" + timeStamp + ".yml");
                    if(oldconfig.delete()){
                        console.sendMessage(ConfigReader.file_config_deleted);
                        CGUtils.plugin.getConfig().options().copyDefaults(true);
                        CGUtils.plugin.saveDefaultConfig();
                        console.sendMessage(ConfigReader.on_load);
                    }
                    if(lock.createNewFile()){
                        console.sendMessage(ConfigReader.file_config_lock);

                    }
                    return;
                }
            }
        } catch (IOException e) {
            console.sendMessage(ConfigReader.file_io_error);
            e.printStackTrace();
        }
    }

    public static void updatecheck(){
        try {
            String json = "";
            URL url = new URL("https://raw.githubusercontent.com/creamgod45/CGInvisibleItemFrame/main/version.txt");
            Scanner s = new Scanner(url.openStream(),"UTF-8");
            int k =0;
            while (s.hasNext()){
                if(k==0){
                    json = json + s.next();
                }else{
                    json = json + " " + s.next();
                }
                k++;
            }

            JSONObject jsonObject = new JSONObject(json);
            console.sendMessage(Utils.format("&b==============["+ConfigReader.updatachecker_title+"]&b================"));
            if(!ConfigReader.version.equals(jsonObject.get("version").toString())) {
                console.sendMessage(Utils.format("&f⇒ "+ConfigReader.updatachecker_nowversion+" : " + ConfigReader.version));
                console.sendMessage(Utils.format("&f⇒ "+ConfigReader.updatachecker_newversion+" : "+jsonObject.get("version").toString()));

                if(ConfigReader.using_custom_message){
                    Map<String, Object> custom = jsonObject.getJSONObject("update-suggestion").getJSONObject("custom").toMap();
                    for (Map.Entry<String, Object> ver : custom.entrySet()) {
                        int newversion = Integer.parseInt(ver.getKey());
                        int nowversion = Integer.parseInt(ConfigReader.version);
                        if(newversion >= nowversion){
                            String s1 = ver.getValue().toString();
                            if(s1.length()>=30){
                                String s1a = s1.substring(0, (s1.length()/2));
                                String s1b = s1.substring((s1.length()/2));
                                console.sendMessage(Utils.format("&f⇒ &e"+ConfigReader.updatachecker_update_suggestion+" : "+s1a));
                                console.sendMessage(Utils.format("&e" + s1b));
                            }else{
                                console.sendMessage(Utils.format("&f⇒ &e"+ConfigReader.updatachecker_update_suggestion+" : "+ver.getValue().toString()));
                            }
                        }
                    }
                }else{
                    Map<String, Object> custom = jsonObject.getJSONObject("update-suggestion").getJSONObject("default").toMap();
                    for (Map.Entry<String, Object> ver : custom.entrySet()) {
                        int newversion = Integer.parseInt(ver.getKey());
                        int nowversion = Integer.parseInt(ConfigReader.version);
                        if(newversion >= nowversion){
                            String s1 = ver.getValue().toString();
                            if(s1.length()>=50){
                                String s1a = s1.substring(0, (s1.length()/2));
                                String s1b = s1.substring((s1.length()/2));
                                console.sendMessage(Utils.format("&f⇒ &e"+ConfigReader.updatachecker_update_suggestion+" : "+s1a));
                                console.sendMessage(Utils.format("&e"+s1b));
                            }else{
                                console.sendMessage(Utils.format("&f⇒ &e"+ConfigReader.updatachecker_update_suggestion+" : "+ver.getValue().toString()));
                            }
                        }
                    }
                }

                console.sendMessage(Utils.format("&b============["+ConfigReader.updatachecker_endtitle+"]&b=============="));
            }else{
                console.sendMessage(Utils.format("&f⇒ "+ConfigReader.updatachecker_done));
                console.sendMessage(Utils.format("&b============["+ConfigReader.updatachecker_endtitle+"]&b=============="));
            }
            // read from your scanner
        }
        catch(IOException ex) {
            ex.printStackTrace();
        } catch (JSONException err) {
            System.out.println("Exception : "+ err.toString());
        }
    }
}

