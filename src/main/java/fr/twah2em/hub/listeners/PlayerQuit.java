package fr.twah2em.hub.listeners;

import fr.twah2em.hub.Hub;
import fr.twah2em.hub.gestion.Account;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        Hub.getInstance().getScoreboardManager().onLogout(player);
        Account account = new Account(player.getUniqueId());
        Hub.getInstance().getAccount(player).ifPresent(Account::onLogout);

        event.setQuitMessage(ChatColor.BLUE + player.getName() + " §ca quitté le hub !");

    }

}
