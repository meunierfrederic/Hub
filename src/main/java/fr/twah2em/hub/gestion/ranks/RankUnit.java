package fr.twah2em.hub.gestion.ranks;

import org.bukkit.ChatColor;

import java.util.Arrays;

public enum RankUnit {

    ADMINISTRATEUR("Admin", 0, "§c[Admin] ", ChatColor.RED),
    DEVELOPPEUR("Développeur", 1, "§9[Développeur] ", ChatColor.BLUE),
    MODERATEUR("Modérateur", 2, "§6[Modérateur] ", ChatColor.GOLD),
    VIP("VIP", 3, "§e[VIP] ", ChatColor.YELLOW),
    JOUEUR("Joueur", 4, "§7", ChatColor.GRAY);

    private final String name;
    private final int power;
    private final String prefix;
    private ChatColor color;

    RankUnit(String name, int power, String prefix, ChatColor color) {
        this.name = name;
        this.power = power;
        this.prefix = prefix;
        this.color = color;
    }

    public static RankUnit getByName(String name) {

        return Arrays.stream(values()).filter(r -> r.getName().equalsIgnoreCase(name)).findAny().orElse(RankUnit.JOUEUR);

    }

    public static RankUnit getByPower(int power) {

        return Arrays.stream(values()).filter(r -> r.getPower() == power).findAny().orElse(RankUnit.JOUEUR);

    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getColoredName() {
        return color + name;
    }

}
