package cg.creamgod45;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigReader {
    // Base Config
    public static String version = "100";
    public static Boolean update_config = false;
    public static String Prefix = "&5&l伺服器集成功能》 &r";
    public static Boolean using_custom_message = false;
    // More Config
    // Messages
    public static String on_load                          = format(Prefix + "&2已複製 config.yml 至插件資料夾。");
    public static String on_enable                        = format(Prefix + "&2CGInvisibleItemFrame 啟動成功");
    public static String on_disable                       = format(Prefix + "&4CGInvisibleItemFrame 關閉成功");
    public static String on_dectect_conflict              = format(Prefix + "&4警告此插件相互衝突!!&c建議移除");
    public static String no_permission                    = format(Prefix + "&c沒有權限使用此操作!!");
    public static String file_configbak_copyed            = format(Prefix + "&e檔案 config.yml 已經備份到 /CGInvisibleItemFrame/backups/");
    public static String file_config_deleted              = format(Prefix + "&c檔案 config.yml 已刪除");
    public static String file_io_error                    = format(Prefix + "&4檔案 IO 錯誤");
    public static String file_config_lock                 = format(Prefix + "&f[&4鎖定&f]&e設定檔案 config.yml");
    public static String file_config_locked               = format(Prefix + "&c設定檔案 config.yml 已經被鎖定。");
    public static String updatachecker_title              = format("&b更新檢查");
    public static String updatachecker_endtitle           = format("&b更新檢查完成");
    public static String updatachecker_nowversion         = format(Prefix + "&f[&c舊&f] &c現在版本");
    public static String updatachecker_newversion         = format(Prefix + "&f[&a新&f] &a最新版本");
    public static String updatachecker_update_suggestion  = format(Prefix + "&f[&a新&f] &e更新建議");
    public static String updatachecker_done               = format(Prefix + "&a你已經是最新版本");

    public static String getstr(String path, Boolean placeholder){
        FileConfiguration _instance = CGUtils.fileconfig;
        if(placeholder) return format(_instance.getString(path));
        if(_instance.getString(path) == "" || _instance.getString(path) == null) {
            if(getdefault(path).equals(path)){
                return path;
            }else{
                return format(getdefault(path));
            }
        }
        return format(_instance.getString(path));
    }

    public static String getdefault(String path){
        switch (path){
            case "Setting.prefix":
                return Prefix;
            case "Messages.on_load":
                return on_load;
            case "Messages.on_enable":
                return on_enable;
            case "Messages.on_disable":
                return on_disable;
            case "Messages.on_dectect_conflict":
                return on_dectect_conflict;
            case "Messages.no_permission":
                return no_permission;
            case "Messages.file_configbak_copyed":
                return file_configbak_copyed;
            case "Messages.file_config_deleted":
                return file_config_deleted;
            case "Messages.file_io_error":
                return file_io_error;
            case "Messages.updatachecker_title":
                return updatachecker_title;
            case "Messages.updatachecker_endtitle":
                return updatachecker_endtitle;
            case "Messages.updatachecker_nowversion":
                return updatachecker_nowversion;
            case "Messages.updatachecker_newversion":
                return updatachecker_newversion;
            case "Messages.updatachecker_update_suggestion":
                return updatachecker_update_suggestion;
            case "Messages.updatachecker_done":
                return updatachecker_done;
            case "Messages.file_config_lock":
                return file_config_lock;
            case "Messages.file_config_locked":
                return file_config_locked;
            default:
                return path;
        }
    }

    public static void load(){
        FileConfiguration _instance = CGUtils.fileconfig;
        Prefix = getstr("Setting.prefix", false);
        update_config = _instance.getBoolean("Setting.update_config");
        using_custom_message = _instance.getBoolean("Setting.using_custom_message");

        if(using_custom_message){
            on_load                          = Prefix + getstr("Messages.on_load", false);
            on_enable                        = Prefix + getstr("Messages.on_enable", false);
            on_disable                       = Prefix + getstr("Messages.on_disable", false);
            on_dectect_conflict              = Prefix + getstr("Messages.on_dectect_conflict", false);
            no_permission                    = Prefix + getstr("Messages.no_permission", false);
            file_configbak_copyed            = Prefix + getstr("Messages.file_configbak_copyed", false);
            file_config_deleted              = Prefix + getstr("Messages.file_config_deleted", false);
            file_io_error                    = Prefix + getstr("Messages.file_io_error", false);
            file_config_lock                 = Prefix + getstr("Messages.file_config_lock", false);
            file_config_locked               = Prefix + getstr("Messages.file_config_locked", false);
            updatachecker_title              = getstr("Messages.updatachecker_title", false);
            updatachecker_endtitle           = getstr("Messages.updatachecker_endtitle", false);
            updatachecker_nowversion         = Prefix + getstr("Messages.updatachecker_nowversion", false);
            updatachecker_newversion         = Prefix + getstr("Messages.updatachecker_newversion", false);
            updatachecker_update_suggestion  = Prefix + getstr("Messages.updatachecker_update_suggestion", false);
            updatachecker_done               = Prefix + getstr("Messages.updatachecker_done",false);
        }

    }

    private static String format(String str){
        return Utils.format(str);
    }
}