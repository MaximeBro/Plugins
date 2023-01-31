package fr.pulsion.alertes;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AlerteCommande implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args)
    {
        if(sender instanceof Player)
        {
            Player joueur = (Player) sender;
            if(args.length == 0)
                envoyerPageAide(joueur);

            if(args.length > 0)
            {
                if (args[0].equalsIgnoreCase("help"))
                {
                    envoyerPageAide(joueur);
                    return true;
                }
                else
                {
                    StringBuilder msg = new StringBuilder();
                    for(int i=1; i < args.length; i++)
                    {
                        String partie = args[i];
                        msg.append(partie + " ");
                    }

                    switch (args[0].toLowerCase())
                    {
                        case "global" -> {
                            Bukkit.broadcastMessage("§8§l[§b§lServeur§8§l] §7" + msg.toString().replace("&", "§"));
                            return true;
                        }

                        case "event" -> {
                            Bukkit.broadcastMessage("§a§l[§c§lEVENT§a§l] §c" + msg.toString().replace("&", "§"));
                            return true;
                        }

                        case "version" -> {
                            joueur.sendMessage("§bversion 1.0");
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    public void envoyerPageAide(Player joueur)
    {
        String aide = "\n";
        aide+= "\n"  + "§8§n                                                         ";
        aide+= "\n " + "\n" + "§8[§b§nAlertes§8] §7- §3Utilisation du plugin";
        aide+= "\n " + "\n" + "§3/alertes";
        aide+= "\n"         + "§3/alertes help";
        aide+= "\n"         + "§3/alertes global  §7<§7§omessage§7>";
        aide+= "\n"         + "§3/alertes event  §7<§7§omessage§7>";
        aide+= "\n"         + "§3/alertes version";
        aide+= "\n " + "\n" + "§b✔ §7Plugin exclusivement réservé au staff";
        aide+= "\n"  + "§8§n                                                         " + "\n§r ";

        joueur.sendMessage(aide);
    }
}
