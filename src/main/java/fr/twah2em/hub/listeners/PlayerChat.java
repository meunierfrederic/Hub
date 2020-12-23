package fr.twah2em.hub.listeners;

import fr.twah2em.hub.Hub;
import fr.twah2em.hub.gestion.Account;
import fr.twah2em.hub.gestion.ranks.RankUnit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerChat implements Listener {

    private final List<Player> cooldown = new ArrayList<>();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        RankUnit rank = RankUnit.JOUEUR;
        Optional<Account> account = Hub.getInstance().getAccount(player);
        if(account.isPresent()) {

            rank = account.get().getDataRank().getRank();

        }

        event.setFormat(rank.getPrefix() + "%1$s §7» " + (rank == RankUnit.JOUEUR ? "§7" : "§f") + "%2$s");

        if (cooldown.contains(player)) {

            event.setCancelled(true);
            player.sendMessage("§cN'écrivez pas trop vite !");
            return;

        }

        if (rank.getPower() > RankUnit.MODERATEUR.getPower()) {

            cooldown.add(player);
            Bukkit.getScheduler().runTaskLater(Hub.getInstance(), () -> cooldown.remove(player), 20L);

        }

    }

}
