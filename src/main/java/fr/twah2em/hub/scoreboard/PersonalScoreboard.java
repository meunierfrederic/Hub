package fr.twah2em.hub.scoreboard;

import fr.twah2em.hub.Hub;
import fr.twah2em.hub.gestion.Account;
import fr.twah2em.hub.gestion.ranks.RankUnit;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

/*
 * This file is part of SamaGamesAPI.
 *
 * SamaGamesAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SamaGamesAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SamaGamesAPI.  If not, see <http://www.gnu.org/licenses/>.
 */
public class PersonalScoreboard {
    private final Player player;
    private final UUID uuid;
    private final ObjectiveSign objectiveSign;
    private final Optional<Account> account;
    private String grade;
    private int playerCount;

    PersonalScoreboard(Player player) {
        this.player = player;
        uuid = player.getUniqueId();
        objectiveSign = new ObjectiveSign("sidebar", "Hub");
        account = Hub.getInstance().getAccount(player);

        reloadData();
        objectiveSign.addReceiver(player);

        for (ScoreboardTeam team : Hub.getInstance().getTeams()) {
            ((CraftPlayer) Bukkit.getPlayer(uuid)).getHandle().playerConnection.sendPacket(team.createTeam());
        }

        for (Player player1 : Bukkit.getOnlinePlayers()) {

            for (Player player2 : Bukkit.getOnlinePlayers()) {

                RankUnit rank = RankUnit.JOUEUR;
                Optional<Account> a = Hub.getInstance().getAccount(player2);

                if (a.isPresent()) {

                    rank = a.get().getDataRank().getRank();

                }

                Optional<ScoreboardTeam> team = Hub.getInstance().getSbTeam(rank);

                team.ifPresent(scoreboardTeam -> ((CraftPlayer) player1).getHandle().playerConnection.sendPacket(scoreboardTeam.addOrRemovePlayer(3, player2.getName())));

            }

        }

    }

    void reloadData() {

        playerCount = Bukkit.getOnlinePlayers().size();

        account.ifPresent(account1 -> {

            grade = account1.getDataRank().getRank().getColoredName();

        });

    }

    void setLines(String ip) {
        objectiveSign.setDisplayName("§eHub");

        objectiveSign.setLine(0, "§1");
        objectiveSign.setLine(1, "§6Joueurs: §a" + playerCount + "/20");
        objectiveSign.setLine(2, "§6Pseudo: §b" + player.getName());
        objectiveSign.setLine(3, "§6Grade: §b" + grade);
        objectiveSign.setLine(4, "§2");
        objectiveSign.setLine(5, ip);

        objectiveSign.updateLines();
    }

    void onLogout() {
        objectiveSign.removeReceiver(Bukkit.getServer().getOfflinePlayer(uuid));
    }
}
