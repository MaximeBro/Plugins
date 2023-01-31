package fr.pulsion.lobby;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LobbyCommande implements CommandExecutor {

    private Lobby ctrl;

    public LobbyCommande(Lobby ctrl) {
        this.ctrl = ctrl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        Player joueur = (Player) sender;

        if(sender instanceof Player) {
            if(args.length > 0) {
                if(args[0].toLowerCase().equals("setspawn")) {
                    Location locJoueur = joueur.getLocation();
                    this.ctrl.setSpawnLobby(locJoueur);
                    return true;
                }
            }

            if(s.toLowerCase().equals("lobby") || s.toLowerCase().equals("hub") || s.toLowerCase().equals("spawn")) {
                joueur.teleport(this.ctrl.getSpawnLobby());
                return true;
            }
        }
        return false;
    }
}
