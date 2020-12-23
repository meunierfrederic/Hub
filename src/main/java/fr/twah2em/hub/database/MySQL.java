package fr.twah2em.hub.database;

import fr.twah2em.hub.Hub;
import org.apache.commons.dbcp2.BasicDataSource;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

import java.sql.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class MySQL {

    String host = "45.140.165.227";
    String port = "3306";
    String database = "hub";
    String username = "Twah2em";
    String password = "RxRuyGjRhZvybSRK";

    private final BasicDataSource connectionPool;
    static Connection connection;

    public MySQL(BasicDataSource connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }

    static ConsoleCommandSender console = Bukkit.getConsoleSender();

    public void createTableHub() {

        Hub.getInstance().getLogger().info("§e[Hub] §aCréation des tables de la base de données");
        update("CREATE TABLE IF NOT EXISTS accounts (" +
                        "`#` INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                        "uuid VARCHAR(255), " +
                        "grade VARCHAR(255), " +
                        "grade_end BIGINT, " +
                        "coins BIGINT)");

    }

    /*public void createTableBan() {

        Hub.getInstance().getLogger().info("§c[Ban] §aCréation des tables de la base de données");
        update("CREATE TABLE IF NOT EXISTS player_infos (" +
                "`ID` INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "player_uuid VARCHAR(255), " +
                "player_name VARCHAR(255))");

    }*/

    public void update(String query) {

        try (Connection c = getConnection();

             PreparedStatement s = c.prepareStatement(query)) {
            s.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public Object query(String query, Function<ResultSet, Object> consumer) {

        try (Connection c = getConnection();

        PreparedStatement s = c.prepareStatement(query);

        ResultSet rs = s.executeQuery()) {

            return consumer.apply(rs);

        } catch (SQLException e) {

            throw new IllegalStateException(e.getMessage());

        }

    }

    public void query(String query, Consumer<ResultSet> consumer) {

        try (Connection c = getConnection();

             PreparedStatement s = c.prepareStatement(query);

             ResultSet rs = s.executeQuery()) {

            consumer.accept(rs);

        } catch (SQLException e) {

            throw new IllegalStateException(e.getMessage());

        }

    }

}
