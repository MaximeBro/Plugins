package fr.pulsion.utilities;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Utilities extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(this, this);
        getCommand("gmc")       .setExecutor(new UtilitiesCommande(this));
        getCommand("gms")       .setExecutor(new UtilitiesCommande(this));
        getCommand("fly")       .setExecutor(new UtilitiesCommande(this));
        getCommand("god")       .setExecutor(new UtilitiesCommande(this));
        getCommand("vanish")    .setExecutor(new UtilitiesCommande(this));
        getCommand("clearmob")  .setExecutor(new UtilitiesCommande(this));
        getCommand("clearlag")  .setExecutor(new UtilitiesCommande(this));
        getCommand("repair")    .setExecutor(new UtilitiesCommande(this));
        getCommand("fix")       .setExecutor(new UtilitiesCommande(this));
        getCommand("repair all").setExecutor(new UtilitiesCommande(this));
        getCommand("fix all")   .setExecutor(new UtilitiesCommande(this));
        getCommand("sun")       .setExecutor(new UtilitiesCommande(this));
        getCommand("invsee")    .setExecutor(new UtilitiesCommande(this));
        getCommand("nick")      .setExecutor(new UtilitiesCommande(this));
        getCommand("rename")    .setExecutor(new UtilitiesCommande(this));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage("§7[§a+§7] §b" + event.getPlayer().getName());

        Player joueur = event.getPlayer();
        if(joueur.getFirstPlayed() != 0)
            joueur.sendMessage("§aBon retour parmi nous :)");
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage("§7[§4-§7] §b" + event.getPlayer().getName());

        Player joueur = event.getPlayer();
    }

    @EventHandler
    public void onSpeak(AsyncPlayerChatEvent event) {

        Player joueur = event.getPlayer();
        String message = event.getMessage().replace('&', '§');

        if(!joueur.hasPermission("admin.*"))
            event.setFormat("§8" + joueur.getName() + " §9>> §f" + message);
        else
            event.setFormat("§4" + joueur.getName() + " §9>> §b" + message);
    }
}