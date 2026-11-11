package MainFiles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

    public static Connection ConnectDb() throws SQLException {
        return getConnection("stock");
    }

    public static Connection getConnection(String database)
            throws SQLException {

        if (!"stock".equals(database) && !"fs".equals(database)) {
            throw new SQLException("Banco de dados não permitido.");
        }

        String url = "jdbc:mysql://localhost:3306/" + database;
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        if (user == null || user.isEmpty()
                || password == null || password.isEmpty()) {
            throw new SQLException(
                    "Credenciais do banco não configuradas.");
        }

        return DriverManager.getConnection(url, user, password);
    }

}