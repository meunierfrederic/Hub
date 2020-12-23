package fr.twah2em.hub.scoreboard;

import fr.twah2em.hub.gestion.ranks.RankUnit;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_12_R1.PacketPlayOutScoreboardTeam;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Alexis on 19/02/2017.
 */
public class ScoreboardTeam {

    private final RankUnit rank;

    public ScoreboardTeam(RankUnit rank) {
        this.rank = rank;
    }

    private PacketPlayOutScoreboardTeam createPacket(int mode){
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();

        // a : team name

        // h : mode
        /*
         If 0 then the team is created.
         If 1 then the team is removed.
         If 2 the team team information is updated.
         If 3 then new players are added to the team.
         If 4 then players are removed from the team.
         */

        // b : display name
        // c : prefix
        // d : suffix
        // i : friendly fire (0 off, 1 on)
        // e : name tag visibility
        // f : chat color

        setField(packet, "a", Integer.toString(rank.getPower()));
        setField(packet, "i", mode);
        setField(packet, "b", "");
        setField(packet, "c", rank.getPrefix());
        setField(packet, "d", "");
        setField(packet, "e", "always");
        setField(packet, "f", "never");

        return packet;
    }

    public PacketPlayOutScoreboardTeam createTeam(){
        return createPacket(0);
    }

    public PacketPlayOutScoreboardTeam updateTeam(){
        return createPacket(2);
    }

    public PacketPlayOutScoreboardTeam removeTeam(){
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();
        setField(packet, "a", Integer.toString(rank.getPower()));
        setField(packet, "i", 1);

        return packet;
    }

    public PacketPlayOutScoreboardTeam addOrRemovePlayer(int mode, String playerName){
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();
        setField(packet, "a", Integer.toString(rank.getPower()));
        setField(packet, "i", mode);

        try {
            Field f = packet.getClass().getDeclaredField("h");
            f.setAccessible(true);
            ((List<String>) f.get(packet)).add(playerName);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return packet;
    }

    public static PacketPlayOutPlayerInfo updateDisplayName(EntityPlayer player) {
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_DISPLAY_NAME, player);
        player.playerConnection.sendPacket(packet);
        return packet;
    }

    private void setField(Object edit, String fieldName, Object value) {
        try {
            Field field = edit.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(edit, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public RankUnit getRank() {
        return rank;
    }
}
