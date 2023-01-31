package fr.pulsion.utilities;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class UtilitiesCommande implements CommandExecutor {

    private Utilities ctrl;
    private Player[] tabJoueurVanished;

    public UtilitiesCommande(Utilities ctrl) {
        this.ctrl = ctrl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if (sender instanceof Player) {
            Player joueur = (Player) sender;

            //GMC
            if (s.toLowerCase().equals("gmc")) {
                if (joueur.hasPermission("utilities.gmc")) {
                    joueur.setGameMode(GameMode.CREATIVE);
                    joueur.sendMessage("§8→ §6Mode créatif activé !");
                    return true;
                } else
                    joueur.sendMessage("§cTu n'as pas la permission !");
                return false;
            }


            //GMS
            if (s.toLowerCase().equals("gms")) {
                if (joueur.hasPermission("utilities.gms")) {
                    joueur.setGameMode(GameMode.SURVIVAL);
                    joueur.sendMessage("§8→ §6Mode survivaliste activé !");
                    return true;
                } else
                    joueur.sendMessage("§cTu n'as pas la permission !");
                return false;
            }


            //GOD
            if (s.toLowerCase().equals("god")) {
                if (joueur.hasPermission("utilities.god")) {
                    if (!joueur.isInvulnerable()) {
                        joueur.setInvulnerable(true);
                        joueur.sendMessage("§6Mode dieu activé !");
                    } else {
                        joueur.setInvulnerable(false);
                        joueur.sendMessage("§6Mode dieu désactivé !");
                    }
                    return true;
                } else
                    joueur.sendMessage("§cTu n'as pas la permission !");
                return false;
            }


            //VANISH
            if (s.equalsIgnoreCase("vanish")) {
                if (joueur.hasPermission("utilities.vanish")) {
                    tabJoueurVanished = this.ctrl.getServer().getOnlinePlayers().toArray(new Player[0]);
                    if (args.length > 0 && args[0].equalsIgnoreCase("off")) {
                        for(Player p : tabJoueurVanished)
                            p.canSee(joueur);
                        joueur.sendMessage("§dMode incognito désactivé !");
                    } else {
                        for(Player p : tabJoueurVanished)
                            p.hidePlayer(joueur);
                        joueur.sendMessage("§dMode incognito activé !");
                    }
                    return true;
                } else
                    joueur.sendMessage("§cTu n'as pas la permission !");
                return false;
            }


            //FLY
            if (s.equalsIgnoreCase("fly")) {
                if (joueur.hasPermission("utilities.fly")) {

                    if (!joueur.getAllowFlight()) {
                        joueur.setAllowFlight(true);
                        joueur.sendMessage("§8§l[§b§lServeur§8§l] §7Fly activé !");
                    } else {
                        joueur.setAllowFlight(false);
                        joueur.sendMessage("§8§l[§b§lServeur§8§l] §7Fly désactivé !");
                    }
                    return true;
                } else
                    joueur.sendMessage("§cTu n'as pas la permission !");
                return false;
            }


            //SUN
            if (s.equalsIgnoreCase("sun")) {
                if (joueur.hasPermission("utilities.sun")) {
                    joueur.getWorld().setStorm(false);
                    joueur.getWorld().setThundering(false);

                    joueur.sendMessage("");
                    return true;
                }
                else
                    joueur.sendMessage("§cTu n'as pas la permission !");
                return false;
            }


            //INVSEE
            if (s.equalsIgnoreCase("invsee")) {
                if (joueur.hasPermission("utilities.invsee")) {
                    if(args.length > 0) {
                        Player joueurDest = Bukkit.getServer().getPlayer(args[0]);

                        if(joueurDest.isOnline()) {
                            joueur.openInventory(joueurDest.getInventory());
                            return true;
                        }
                    }
                    else {
                        joueur.sendMessage("§8§l[§b§lServeur§8§l] §cUsage : /invsee <NomDuJoueur>");
                        return false;
                    }
                }
                else
                    joueur.sendMessage("§cTu n'as pas la permission !");
                return false;
            }


            //RENAME
            if (s.equalsIgnoreCase("rename")) {
                if (joueur.hasPermission("utilities.item.rename")) {
                    if (args.length > 0 && args[0] != null) {

                        StringBuilder nomItem = new StringBuilder();
                        for(int i=0; i < args.length; i++)
                        {
                            String partie = args[i];
                            nomItem.append(partie + " ");
                        }
                        ItemStack itemARename = joueur.getInventory().getItemInMainHand();
                        String nouveauNomItem = nomItem.toString().replace('&', '§');

                        if(itemARename != null && itemARename.getType() != Material.AIR) {
                            ItemMeta itemARenameMeta = itemARename.getItemMeta();
                            itemARenameMeta.setDisplayName(nouveauNomItem);
                            itemARename.setItemMeta(itemARenameMeta);
                            joueur.updateInventory();
                        }
                        return true;
                    }
                    else {
                        joueur.sendMessage("§8§l[§b§lServeur§8§l] §cUsage : \"/rename <NouveauNomDeL'item>\"");
                        return false;
                    }
                }
                else {
                    joueur.sendMessage("§cTu n'as pas la permission !");
                    return false;
                }
            }


            //NICK
            if (s.equalsIgnoreCase("nick")) {
                if (joueur.hasPermission("utilities.nick")) {
                    if (args.length > 0) {
                        String joueurRename = args[0];
                        String nouveauNom = args[1].replace('&', '§');

                        for(Player p : Bukkit.getOnlinePlayers())
                            if(p.getName().equals(joueurRename))
                                p.setDisplayName(nouveauNom);

                        return true;
                    }
                    else {
                        joueur.sendMessage("§8§l[§b§lServeur§8§l] §cUsage : \"/nick <joueur> <nouveauPseudonyme>\"");
                        return false;
                    }
                }
                else {
                    joueur.sendMessage("§cTu n'as pas la permission !");
                    return false;
                }
            }


            //CLEARMOB
            if (s.equalsIgnoreCase("clearmob")) {
                if (joueur.hasPermission("utilities.clearmob")) {

                    for (World world : Bukkit.getServer().getWorlds())
                        for(Entity entity : world.getEntities())
                            if( ! (entity instanceof Player) && ! (entity instanceof Item) )
                                entity.remove();

                    return true;
                }
                else
                    joueur.sendMessage("§cTu n'as pas la permission !");
                return false;
            }


            //CLEARLAG
            if (s.equalsIgnoreCase("clearlag")) {
                if (joueur.hasPermission("utilities.clearlag")) {
                    int cptEntity = 0;
                    for (World world : Bukkit.getServer().getWorlds())
                        for(Entity entity : world.getEntities())
                            if (entity instanceof Item) {
                                entity.remove();
                                cptEntity++;
                        }
                    Bukkit.broadcastMessage("§8§l[§b§lServeur§8§l] §a" + cptEntity + "§7 entités ont été supprimées");
                    return true;
                }
                else
                    joueur.sendMessage("§cTu n'as pas la permission !");
                return false;
            }


            //FIX & REPAIR
            if (s.equalsIgnoreCase("fix") || s.toLowerCase().equals("repair")) {
                if (joueur.hasPermission("utilities.repair") || joueur.hasPermission("utilities.fix")) {

                    //REPAIR ALL - FIX ALL
                    if (args.length > 0) {

                        if (args[0].equalsIgnoreCase("all") && joueur.hasPermission("utilities.repairall") || joueur.hasPermission("fixall")) {
                            for (ItemStack is : joueur.getInventory().getContents())
                                if (is != null
                                        && is.getType() != Material.AIR
                                        && !is.getType().isBlock()
                                        && !is.getType().isEdible()
                                        && is.getType().getMaxDurability() > 0
                                        && is.getDurability() != 0) {

                                    is.setDurability((short) 0);
                                    joueur.updateInventory();
                                }

                            for (ItemStack is : joueur.getEquipment().getArmorContents()) {
                                if (is != null
                                        && is.getType() != Material.AIR
                                        && !is.getType().isBlock()
                                        && !is.getType().isEdible()
                                        && is.getType().getMaxDurability() > 0
                                        && is.getDurability() != 0) {

                                    is.setDurability((short) 0);
                                    joueur.updateInventory();
                                }
                                joueur.sendMessage("§8§l[§b§lServeur§8§l] §6Les items de votre inventaire ont bien été réparé");
                                return true;
                            }
                        }
                        else{
                            joueur.sendMessage("§8§l[§b§lServeur§8§l] §cUsage : \"/repair\" ou \"/repair all\"");
                        }
                    }
                    else{
                        PlayerInventory invJoueur = joueur.getInventory();
                        joueur.sendMessage("1");
                        ItemStack is = invJoueur.getItemInMainHand();

                        if (is != null
                                && is.getType() != Material.AIR
                                && !is.getType().isBlock()
                                && !is.getType().isEdible()
                                && is.getType().getMaxDurability() > 0
                                && is.getDurability() != 0) {

                            joueur.sendMessage("3");
                            is.setDurability((short) 0);
                            joueur.updateInventory();

                            joueur.sendMessage("§8§l[§b§lServeur§8§l] §6Votre item a été réparé");
                            return true;
                        }
                    }
                }
                else
                    joueur.sendMessage("§cTu n'as pas la permission !");
                return false;
            }
        }
        return false;
    }
}