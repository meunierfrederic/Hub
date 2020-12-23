package fr.twah2em.hub.commands;

import fr.twah2em.hub.Hub;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Coins implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        sender.sendMessage(String.format("§aVous avez §6%s§2$", Hub.getInstance().getAccount(Bukkit.getPlayer(sender.getName())).get().getDataCoins().getCoins()));

        return false;
    }
}
