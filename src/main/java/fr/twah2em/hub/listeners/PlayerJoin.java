package fr.twah2em.hub.listeners;

import fr.twah2em.hub.Hub;
import fr.twah2em.hub.gestion.Account;
import fr.twah2em.hub.gestion.ranks.RankUnit;
import fr.twah2em.hub.utils.ActionBar;
import fr.twah2em.hub.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class PlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        Account account = new Account(player.getUniqueId());
        account.onLogin();

        event.setJoinMessage(ChatColor.AQUA + player.getName() + " §aa rejoint le hub !");

        player.getInventory().clear();
        player.getEquipment().clear();
        player.setHealth(20);
        player.setFoodLevel(20);
        player.setLevel(0);
        player.setExp(0);
        player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_CHIME, 1f, 1f);

        if(!account.getDataRank().getRank().equals(RankUnit.JOUEUR)) {

            if (account.getDataRank().getRank().equals(RankUnit.ADMINISTRATEUR)) {

                player.setGameMode(GameMode.CREATIVE);

            } else {

                player.setAllowFlight(true);

            }

        }

        Title title = new Title("§6§lBienvenue", "§6§lSur le serveur !");
        title.send(player, 1, 5, 1);

        ActionBar actionBar = new ActionBar("§aBienvenue !");
        actionBar.playMessage(player);

        Hub.getInstance().getScoreboardManager().onLogin(player);

    }

}
