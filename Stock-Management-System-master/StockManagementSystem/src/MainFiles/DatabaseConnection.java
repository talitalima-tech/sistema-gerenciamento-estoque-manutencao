
package MainFiles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/fs";

    private DatabaseConnection() {
        // Impede a criação de instâncias
    }

    public static Connection getConnection() throws SQLException {

        String usuario = System.getenv("DB_USER");
        String senha = System.getenv("DB_PASSWORD");

        if (usuario == null || usuario.isEmpty()
                || senha == null || senha.isEmpty()) {
            throw new SQLException(
                "Credenciais do banco não configuradas."
            );
        }

        return DriverManager.getConnection(URL, usuario, senha);
    }
}
