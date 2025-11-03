package util;

import java.sql.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Conexao {
    private static String url;
    private static String user;
    private static String password;
    private static String driver;

    static {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("resources/config.properties"));

            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");
            driver = props.getProperty("db.driver");

            Class.forName(driver);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar configuração do banco: " + e.getMessage());
        }
    }

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static void fechar(Connection conn, Statement stmt, ResultSet rs) {
        try { if (rs != null) rs.close(); } catch (Exception e) { }
        try { if (stmt != null) stmt.close(); } catch (Exception e) { }
        try { if (conn != null) conn.close(); } catch (Exception e) { }
    }
}
