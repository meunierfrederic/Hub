package fr.twah2em.hub.gestion;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

abstract class AbstractData {

    UUID uuid;
    public String getUUID() {
        return uuid.toString();
    }
    public Player getPlayer() {
        return Bukkit.getPlayer(uuid).getPlayer();
    }

}
