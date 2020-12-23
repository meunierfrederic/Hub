package fr.twah2em.hub.utils;

import net.minecraft.server.v1_12_R1.ChatMessageType;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionBar {
    private final String message;

    public ActionBar(String message) {
        this.message = message;
    }

    /**
     * Jouer l'ActionBar à un joueur
     * @param player le joueur
     */
    public void playMessage(Player player){
        CraftPlayer p = (CraftPlayer) player;
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, ChatMessageType.GAME_INFO);
        p.getHandle().playerConnection.sendPacket(ppoc);
    }
}
