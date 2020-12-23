package fr.twah2em.hub.managers;

import fr.twah2em.hub.Hub;
import fr.twah2em.hub.commands.*;
import fr.twah2em.hub.listeners.PlayerChat;
import fr.twah2em.hub.listeners.PlayerJoin;
import fr.twah2em.hub.listeners.PlayerQuit;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class RegisterManager implements Listener {

    public void register() {

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoin(), Hub.getInstance());
        pluginManager.registerEvents(new PlayerQuit(), Hub.getInstance());
        pluginManager.registerEvents(new PlayerChat(), Hub.getInstance());

        Hub.getInstance().getCommand("setrank").setExecutor(new SetRank());
        Hub.getInstance().getCommand("coins").setExecutor(new Coins());
        Hub.getInstance().getCommand("addcoins").setExecutor(new AddCoins());
        Hub.getInstance().getCommand("removecoins").setExecutor(new RemoveCoins());
        Hub.getInstance().getCommand("setcoins").setExecutor(new SetCoins());

    }

}
