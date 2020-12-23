package fr.twah2em.hub;

import fr.twah2em.hub.database.MySQL;
import fr.twah2em.hub.gestion.Account;
import fr.twah2em.hub.gestion.ranks.RankUnit;
import fr.twah2em.hub.managers.RegisterManager;
import fr.twah2em.hub.scoreboard.ScoreboardManager;
import fr.twah2em.hub.scoreboard.ScoreboardTeam;
import org.apache.commons.dbcp2.BasicDataSource;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class Hub extends JavaPlugin {

    public static Hub INSTANCE;
    private ScoreboardManager scoreboardManager;
    BasicDataSource connectionPool;

    String host, port, database, username, password;
    static Connection connection;

    private MySQL mySQL;

    private final List<ScoreboardTeam> teams = new ArrayList<>();
    private List<Account> accounts;

    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;

    @Override
    public void onEnable() {

        INSTANCE = this;
        new RegisterManager().register();
        accounts = new ArrayList<>();

        host = "45.140.165.227";
        port = "3306";
        database = "hub";
        username = "Twah2em";
        password = "RxRuyGjRhZvybSRK";

        try {

            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            //console.sendMessage("Connecté à la DataBase");

        } catch (SQLException e) {

            e.printStackTrace();

        }

        //initConnectionHub();
        //initConnectionBan();

        scheduledExecutorService = Executors.newScheduledThreadPool(16);
        executorMonoThread = Executors.newScheduledThreadPool(1);
        scoreboardManager = new ScoreboardManager();

        Arrays.stream(RankUnit.values()).forEach(rank -> teams.add(new ScoreboardTeam(rank)));

    }

    @Override
    public void onDisable() {

        // Plugin shutdown logic
        try {

            connection.close();

            //console.sendMessage("Déconnecté de la DataBase !");

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    /*private void initConnectionHub() {

        getLogger().info("§e[Hub] §aTentative de connexion à la base de données en cours...");
        connectionPool = new BasicDataSource();
        connectionPool.setDriverClassName("com.mysql.jdbc.Driver");
        //connectionPool.setUsername("Twah2em");
        connectionPool.setUsername("root");
        connectionPool.setPassword("");
        //connectionPool.setPassword("RxRuyGjRhZvybSRK");
        //connectionPool.setUrl("http://45.140.165.227:3306/hub");
        connectionPool.setUrl("jdbc:mysql://localhost:3306/hub2");
        connectionPool.setInitialSize(1);
        connectionPool.setMaxTotal(10);

        mySQL = new MySQL(connectionPool);
        mySQL.createTableHub();

        getLogger().info("§e[Hub] §aJe suis connecté à la base de données !");

    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            return;
        }
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database,
                this.username, this.password);
    }

    private void initConnectionBan() {

        getLogger().info("§c[Ban] §aTentative de connexion à la base de données en cours...");
        connectionPool = new BasicDataSource();
        connectionPool.setDriverClassName("com.mysql.jdbc.Driver");
        connectionPool.setUsername("root");
        connectionPool.setPassword("");
        connectionPool.setUrl("jdbc:mysql://localhost:3306/ban?autoReconnect=true");

        mySQL = new MySQL(connectionPool);
        mySQL.createTableBan();

    }*/

    public MySQL getMySQL() {
        return mySQL;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public Optional<Account> getAccount(Player player) {
        return new ArrayList<>(accounts).stream().filter(account -> account.getUUID().equals(player.getUniqueId().toString())).findFirst();
    }

    public static Hub getInstance() {
        return INSTANCE;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public ScheduledExecutorService getExecutorMonoThread() {
        return executorMonoThread;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }

    public List<ScoreboardTeam> getTeams() {
        return teams;
    }

    public Optional<ScoreboardTeam> getSbTeam(RankUnit rank) {

        return teams.stream().filter(team -> team.getRank() == rank).findFirst();

    }

}
