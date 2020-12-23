package fr.twah2em.hub.gestion;

import fr.twah2em.hub.Hub;
import fr.twah2em.hub.gestion.ranks.RankUnit;

import java.sql.SQLException;
import java.util.UUID;

public class Account extends AbstractData {

    private boolean newPlayer;
    private final DRank dataRank;
    private final DCoins dataCoins;

    private static final String TABLE = "accounts";


    public Account(UUID uuid) {
        this.uuid = uuid;
        this.newPlayer = false;
        this.dataRank = new DRank(uuid);
        this.dataCoins = new DCoins(uuid);
    }

    private String[] getDataFromMySQL() {

        String[] data = new String[3];

        Hub.getInstance().getMySQL().query(String.format("SELECT * FROM accounts WHERE uuid='%s'", getUUID()), rs -> {

            try {

                if (rs.next()) {

                    data[0] = rs.getString("grade");
                    data[1] = rs.getString("grade_end");
                    data[2] = rs.getString("coins");

                } else {

                    newPlayer = true;

                }

            } catch (SQLException throwables) {

                throwables.printStackTrace();

            }

        });

        return data;

    }

    private void sendDataToMySQL() {

        if (newPlayer) {

            Hub.getInstance().getMySQL().update(String.format("INSERT INTO accounts (uuid, grade, grade_end, coins) VALUES ('%s', '%s', '%s', '%s')",
                    getUUID(), dataRank.getRank().getName(), dataRank.getEnd(), dataCoins.getCoins()));

        } else {

            Hub.getInstance().getMySQL().update(String.format("UPDATE accounts SET grade='%s', grade_end='%s', coins='%s' WHERE uuid='%s'",
                    dataRank.getRank().getName(), dataRank.getEnd(), dataCoins.getCoins(), getUUID()));

        }

    }

    public void onLogin() {

        Hub.getInstance().getAccounts().add(this);
        String[] data = getDataFromMySQL();

        if(newPlayer) {

            dataRank.setRank(RankUnit.JOUEUR);
            dataCoins.setCoins(0);

        } else {

            dataRank.setRank(RankUnit.getByName(data[0]), Long.parseLong(data[1]));
            dataCoins.setCoins(Long.parseLong(data[2]));

        }

    }

    public void onLogout() {

        sendDataToMySQL();
        Hub.getInstance().getAccounts().remove(this);

    }

    public DCoins getDataCoins() {
        return dataCoins;
    }

    public DRank getDataRank() {
        return dataRank;
    }

}
