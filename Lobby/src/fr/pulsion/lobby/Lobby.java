package fr.pulsion.lobby;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class Lobby extends JavaPlugin implements Listener {

    private Location spawnLobby;
    private ItemStack menuTp, profilServeur, menuInfoJoueur;
    private Inventory menuServeurs;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(this, this);

        this.spawnLobby = new Location(Bukkit.getWorld(getConfig().getString("SpawnLobby.World.name")),
                                        getConfig().getDouble("SpawnLobby.World.x"),
                                        getConfig().getDouble("SpawnLobby.World.y"),
                                        getConfig().getDouble("SpawnLobby.World.z"), 0.0f, 0.0f);

        getCommand("lobby").setExecutor(new LobbyCommande(this));
        getCommand("hub").setExecutor(new LobbyCommande(this));
        getCommand("spawn").setExecutor(new LobbyCommande(this));

        System.out.println("§aLobby Enabled");

        List<Player> alJoueur = Bukkit.getWorld("lobby").getPlayers();
        for(Player joueur : alJoueur)
            giveInventaireLobby(joueur);
    }

    public void setSpawnLobby(Location loc) {

        getConfig().set("SpawnLobby.World.name", (String) loc.getWorld().getName());
        getConfig().set("SpawnLobby.World.x", (Double) loc.getX());
        getConfig().set("SpawnLobby.World.y", (Double) loc.getY());
        getConfig().set("SpawnLobby.World.z", (Double) loc.getZ());
        this.spawnLobby = loc;
    }

    public Location getSpawnLobby() {
        return this.spawnLobby;
    }


    @Override
    public void onDisable() {
        System.out.println("§aLobby Disabled");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        //Lorsque le joueur rejoint le serveur, le spawn est redéfini et le joueur y est téléporté
        Player joueur = event.getPlayer();
        joueur.teleport(this.spawnLobby);
        giveInventaireLobby(joueur);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        //Ici, on gère les intéractions avec la boussole de téléportation
        Player joueur = event.getPlayer();
        Action action = event.getAction();
        ItemStack objetInteractif = event.getItem();

        if(objetInteractif != null && objetInteractif.isSimilar(this.menuTp)) {
            if( action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK ||
                action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK)
                ouvrirMenu(joueur);
        }
    }


    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {

        ItemStack objetDrop = event.getItemDrop().getItemStack();
        Player joueur = event.getPlayer();

        if(objetDrop.isSimilar(this.menuTp) || objetDrop.isSimilar((this.menuInfoJoueur))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMoveInInventory(InventoryClickEvent event) {
        ItemStack objetMove = event.getCurrentItem();

        if( objetMove != null && objetMove.isSimilar(this.menuTp) ||
            objetMove != null && objetMove.isSimilar(this.menuInfoJoueur) ||
            objetMove != null && event.getClickedInventory().equals(this.menuServeurs))
            event.setCancelled(true);
    }


    public void giveInventaireLobby(Player joueur) {

        //Ici, on gère l'inventaire du joueur lorsqu'il rejoint le serveur de manière à ce qu'il n'ait
        //que la boussole de téléportation entre les différents serveurs
        joueur.getInventory().clear();
        this.menuTp = new ItemStack(Material.COMPASS, 1);
        this.menuInfoJoueur = new ItemStack(Material.BOOK);

        ItemMeta menuTpMeta = this.menuTp.getItemMeta();
        menuTpMeta.setDisplayName("§aServeurs de jeu");
        menuTpMeta.setLore(Arrays.asList("", "§7→ clic droit pour ouvrir le menu"));
        this.menuTp.setItemMeta(menuTpMeta);

        ItemMeta menuInfoMeta = this.menuInfoJoueur.getItemMeta();
        menuInfoMeta.setDisplayName("§9→ Infos");
        menuInfoMeta.setLore(Arrays.asList("Temps de jeu : 0h", ""));
        this.menuInfoJoueur.setItemMeta(menuInfoMeta);

        joueur.getInventory().setItem(4, this.menuTp);
        joueur.getInventory().setItem(8, this.menuInfoJoueur);
        joueur.updateInventory();
    }


    //Menu de la boussole de téléportation
    public void ouvrirMenu(Player joueur) {
        this.menuServeurs = Bukkit.createInventory(null, 54, "§4Menu des serveurs");

        //Création du menu des serveurs de jeu
        for(int i=0; i < 9; i++) {
            ItemStack itemDeco = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta itemDecoMeta = itemDeco.getItemMeta();
            itemDecoMeta.setDisplayName(" ");
            itemDeco.setItemMeta(itemDecoMeta);
            this.menuServeurs.setItem(i, itemDeco);
        }

        for(int i=9; i < 37; i+=9) {
            ItemStack itemDeco = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta itemDecoMeta = itemDeco.getItemMeta();
            itemDecoMeta.setDisplayName(" ");
            itemDeco.setItemMeta(itemDecoMeta);
            this.menuServeurs.setItem(i, itemDeco);
        }

        for(int i=17; i < 46; i+=9) {
            ItemStack itemDeco = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta itemDecoMeta = itemDeco.getItemMeta();
            itemDecoMeta.setDisplayName(" ");
            itemDeco.setItemMeta(itemDecoMeta);
            this.menuServeurs.setItem(i, itemDeco);
        }

        for(int i=45; i < 54; i++) {
            ItemStack itemDeco = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta itemDecoMeta = itemDeco.getItemMeta();
            itemDecoMeta.setDisplayName(" ");
            itemDeco.setItemMeta(itemDecoMeta);
            this.menuServeurs.setItem(i, itemDeco);
        }

        for(int i=10; i < 17; i++)
        {
            ItemStack itemDeco = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
            ItemMeta itemDecoMeta = itemDeco.getItemMeta();
            itemDecoMeta.setDisplayName(" ");
            itemDeco.setItemMeta(itemDecoMeta);
            this.menuServeurs.setItem(i, itemDeco);
        }

        for(int i=28; i < 35; i++)
        {
            ItemStack itemDeco = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
            ItemMeta itemDecoMeta = itemDeco.getItemMeta();
            itemDecoMeta.setDisplayName(" ");
            itemDeco.setItemMeta(itemDecoMeta);
            this.menuServeurs.setItem(i, itemDeco);
        }

        for(int i=37; i < 44; i++)
        {
            if(i == 40) {
                this.profilServeur = new ItemStack(Material.PAINTING);
                ItemMeta profilServeurMeta = this.profilServeur.getItemMeta();

                profilServeurMeta.setDisplayName("§9-=-=-§5§nUnivers§6§nCraft§9-=-=-");
                profilServeurMeta.setLore(Arrays.asList("§9|                              |",
                                                        "§7→ §6Site: §fwww.serveur.fr", "§7→ §9Discord: §fdiscord.gg",
                                                        "§9|                              |",
                                                        "§9-=-=-=--=-=-=--=-=-=-"));
                this.profilServeur.setItemMeta(profilServeurMeta);
                this.menuServeurs.setItem(i, this.profilServeur);
            }
            else {
                ItemStack itemDeco = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
                ItemMeta itemDecoMeta = itemDeco.getItemMeta();
                itemDecoMeta.setDisplayName(" ");
                itemDeco.setItemMeta(itemDecoMeta);
                this.menuServeurs.setItem(i, itemDeco);
            }
        }


        ItemStack itemDeco = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta itemDecoMeta = itemDeco.getItemMeta();
        itemDecoMeta.setDisplayName(" ");
        itemDeco.setItemMeta(itemDecoMeta);
        this.menuServeurs.setItem(19, itemDeco);
        this.menuServeurs.setItem(21, itemDeco);
        this.menuServeurs.setItem(22, itemDeco);
        this.menuServeurs.setItem(23, itemDeco);
        this.menuServeurs.setItem(25, itemDeco);


        ItemStack serveurSkyblock = new ItemStack(Material.GRASS_BLOCK);
        ItemStack serveurGTA = new ItemStack(Material.MINECART);

        ItemMeta skyblockMeta = serveurSkyblock.getItemMeta();
        ItemMeta gtaMeta = serveurGTA.getItemMeta();

        skyblockMeta.setDisplayName("§aSkyblock");
        gtaMeta.setDisplayName("§cGTA");

        serveurSkyblock.setItemMeta(skyblockMeta);
        serveurGTA.setItemMeta(gtaMeta);


        this.menuServeurs.setItem(20, serveurSkyblock);
        this.menuServeurs.setItem(24, serveurGTA);

        //Ouverture du menu
        joueur.openInventory(this.menuServeurs);
    }
}