package fr.pulsion.alertes;

import org.bukkit.plugin.java.JavaPlugin;

public class Alertes extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("Alertes Enabled");

        getCommand("alertes").setExecutor(new AlerteCommande());
    }

    @Override
    public void onDisable() {
        System.out.println("Alertes Disabled");
    }
}
