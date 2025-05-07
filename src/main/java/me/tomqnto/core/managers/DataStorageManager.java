package me.tomqnto.core.managers;

import com.google.errorprone.annotations.Var;
import me.tomqnto.core.Core;
import org.bukkit.entity.Player;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class DataStorageManager {

    final Core plugin = Core.getInstance();
    final String folderPath  = plugin.getDataFolder().getPath() + "/data/";

    public void savePlayerData(Player player, Var data){
        File file = new File(folderPath + player.getUniqueId() + ".bat");
        file.getParentFile().mkdirs();

        try(DataOutputStream out = new DataOutputStream(new FileOutputStream(file))){
            out.writeUTF(String.valueOf(player.getUniqueId()));
        } catch (Exception e){

        }
    }

}
