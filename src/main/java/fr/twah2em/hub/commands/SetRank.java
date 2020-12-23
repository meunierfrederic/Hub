package fr.twah2em.hub.commands;

import fr.twah2em.hub.Hub;
import fr.twah2em.hub.gestion.ranks.RankUnit;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetRank implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;
            RankUnit rank = Hub.getInstance().getAccount(player).get().getDataRank().getRank();

            if (rank != RankUnit.ADMINISTRATEUR) {

                player.sendMessage("§cErreur: Vous n'avez pas la permission pour faire ceci.");

                return false;

            }

            if (args.length == 0 || args.length == 1) {

                player.sendMessage("§cErreur: Vous devez préciser un joueur et un grade");

                return false;

            }

            Player targetPlayer = Bukkit.getPlayer(args[0]);

            if (args[0].equalsIgnoreCase(targetPlayer.getName())) {

                switch (args[1]) {

                    case "joueur":

                        Hub.getInstance().getAccount(targetPlayer).get().getDataRank().setRank(RankUnit.JOUEUR);
                        player.sendMessage("§aVous avez bien mis §b" + targetPlayer.getName() + " §7Joueur§a.");
                        break;

                    case "vip":

                        Hub.getInstance().getAccount(targetPlayer).get().getDataRank().setRank(RankUnit.VIP);
                        player.sendMessage("§aVous avez bien mis §b" + targetPlayer.getName() + " §eVIP§a.");
                        break;

                    case "modo":
                    case "modérateur":
                    case "moderateur":

                        Hub.getInstance().getAccount(targetPlayer).get().getDataRank().setRank(RankUnit.MODERATEUR);
                        Bukkit.broadcastMessage("§6§l" + targetPlayer.getName() + " est passé §6§lModérateur §a§l!");
                        break;

                    case "dev":
                    case "developpeur":
                    case "développeur":

                        Hub.getInstance().getAccount(targetPlayer).get().getDataRank().setRank(RankUnit.DEVELOPPEUR);
                        Bukkit.broadcastMessage("§6§l" + targetPlayer.getName() + " est passé §9§lDéveloppeur §a§l!");
                        break;

                    case "administrateur":
                    case "admin":

                        Hub.getInstance().getAccount(targetPlayer).get().getDataRank().setRank(RankUnit.ADMINISTRATEUR);
                        Bukkit.broadcastMessage("§6§l" + targetPlayer.getName() + " est passé §c§lAdministrateur §a§l!");
                        break;

                }

                targetPlayer.kickPlayer("§cVous avez changé de grade. Vous avez été kick pour actualiser la base de donnée.");

            }

        }

        return false;

    }
}
