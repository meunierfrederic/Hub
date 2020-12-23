package fr.twah2em.hub.commands;

import fr.twah2em.hub.Hub;
import fr.twah2em.hub.gestion.Account;
import fr.twah2em.hub.gestion.ranks.RankUnit;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class AddCoins implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        RankUnit rank = RankUnit.JOUEUR;
        Optional<Account> account = Hub.getInstance().getAccount(Bukkit.getPlayer(sender.getName()));

        if(account.isPresent()) {

            rank = account.get().getDataRank().getRank();

        }

        if (rank.getPower() > RankUnit.MODERATEUR.getPower()) {

            sender.sendMessage("§cErreur: Vous n'avez pas la permission pour faire ceci.");

            return false;

        }

        if (args.length == 0 || args.length == 1) {

            sender.sendMessage("§cErreur: Vous devez préciser un joueur et me le montant d'argent que vous voulez lui ajouter.");
            return false;

        }

        if (args.length == 2) {

            if (isNumeric(args[1])) {

                Player targetPlayer = Bukkit.getPlayer(args[0]);

                Hub.getInstance().getAccount(targetPlayer).get().getDataCoins().addCoins(Long.parseLong(args[1]));
                sender.sendMessage("§aVous avez bien ajouté §6" + args[1] + "§2$ §aà §b" + targetPlayer.getName() + "§a.");
                targetPlayer.sendMessage("§b" + sender.getName() + " §avous a donné §6" + args[1] + "§2$.");

            } else {

                sender.sendMessage("§cErreur: Le montant donné n'est pas un nombre valide.");

            }

        }

        return false;

    }

    public static boolean isNumeric(String strNum) {

        if (strNum == null) {

            return false;

        }

        try {

            Double d = Double.parseDouble(strNum);

        } catch (NumberFormatException nfe) {

            return false;

        }

        return true;

    }

}
