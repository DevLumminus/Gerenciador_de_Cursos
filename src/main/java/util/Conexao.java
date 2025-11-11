package util;

import java.sql.*;
import java.io.InputStream;
import java.io.File;
import java.util.Properties;

public class Conexao {
    private static String url;
    private static String user;
    private static String password;
    private static String driver;

    static {
        carregarConfiguracao();
    }

    private static void carregarConfiguracao() {
        try {
            Properties props = new Properties();
            InputStream input = null;

            // Tentativa 1: Carregar do classpath (resources)
            input = Conexao.class.getClassLoader().getResourceAsStream("config.properties");

            // Tentativa 2: Se não encontrou no classpath, tenta caminho relativo
            if (input == null) {
                System.out.println("Tentando carregar config.properties do diretório do projeto...");
                File configFile = new File("src/resources/config.properties");
                if (configFile.exists()) {
                    input = new java.io.FileInputStream(configFile);
                }
            }

            // Tentativa 3: Tenta caminho absoluto
            if (input == null) {
                System.out.println("Tentando caminho absoluto...");
                File configFile = new File("C:/Users/Andre/Downloads/Gerenciador de Curso/Gerenciador_de_Cursos/src/resources/config.properties");
                if (configFile.exists()) {
                    input = new java.io.FileInputStream(configFile);
                }
            }

            if (input == null) {
                System.err.println("ERRO: Arquivo config.properties não encontrado em nenhum local!");
                System.err.println("Diretório atual: " + new File(".").getAbsolutePath());
                return;
            }

            props.load(input);
            input.close();

            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");
            driver = props.getProperty("db.driver");

            if (url == null || user == null || password == null || driver == null) {
                System.err.println("ERRO: Propriedades não encontradas no arquivo config.properties");
                return;
            }

            // Registra o driver JDBC
            Class.forName(driver);
            System.out.println("Driver JDBC carregado com sucesso: " + driver);

        } catch (Exception e) {
            System.err.println("Erro ao carregar configuração do banco: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection conectar() throws SQLException {
        if (url == null || user == null || password == null) {
            throw new SQLException("Configuração do banco de dados não carregada corretamente");
        }


        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão estabelecida com sucesso!");
            return conn;
        } catch (SQLException e) {
            System.err.println("Erro na conexão: " + e.getMessage());
            System.err.println("Verifique se:");
            System.err.println("   - MySQL está rodando");
            System.err.println("   - Banco 'plataformac' existe");
            System.err.println("   - Usuário e senha estão corretos");
            throw e;
        }
    }

    public static void fechar(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (Exception e) {
            System.err.println("Erro ao fechar ResultSet: " + e.getMessage());
        }
        try {
            if (stmt != null) stmt.close();
        } catch (Exception e) {
            System.err.println("Erro ao fechar Statement: " + e.getMessage());
        }
        try {
            if (conn != null) conn.close();
        } catch (Exception e) {
            System.err.println("Erro ao fechar Connection: " + e.getMessage());
        }
    }
}