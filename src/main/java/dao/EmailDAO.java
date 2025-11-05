package dao;

import model.Email;
import util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmailDAO {

    public Email inserir(Email email) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            String sql = "INSERT INTO email (Email) VALUES (?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, email.getEmail());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    email.setIdEmail(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir email: " + e.getMessage());
        } finally {
            Conexao.fechar(conn, stmt, rs);
        }
        return email;
    }

    public Email buscarPorId(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Email email = null;

        try {
            conn = Conexao.conectar();
            String sql = "SELECT * FROM email WHERE idEmail = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                email = new Email();
                email.setIdEmail(rs.getInt("idEmail"));
                email.setEmail(rs.getString("Email"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar email: " + e.getMessage());
        } finally {
            Conexao.fechar(conn, stmt, rs);
        }
        return email;
    }

    public List<Email> listarTodos() {
        List<Email> emails = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            String sql = "SELECT * FROM email ORDER BY idEmail";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Email email = new Email();
                email.setIdEmail(rs.getInt("idEmail"));
                email.setEmail(rs.getString("Email"));
                emails.add(email);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar emails: " + e.getMessage());
        } finally {
            Conexao.fechar(conn, stmt, rs);
        }
        return emails;
    }

    public boolean atualizar(Email email) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexao.conectar();
            String sql = "UPDATE email SET Email = ? WHERE idEmail = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email.getEmail());
            stmt.setInt(2, email.getIdEmail());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar email: " + e.getMessage());
            return false;
        } finally {
            Conexao.fechar(conn, stmt, null);
        }
    }

    public boolean excluir(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexao.conectar();
            String sql = "DELETE FROM email WHERE idEmail = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir email: " + e.getMessage());
            return false;
        } finally {
            Conexao.fechar(conn, stmt, null);
        }
    }

    public Email buscarPorEndereco(String enderecoEmail) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Email email = null;

        try {
            conn = Conexao.conectar();
            String sql = "SELECT * FROM email WHERE Email = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, enderecoEmail);
            rs = stmt.executeQuery();

            if (rs.next()) {
                email = new Email();
                email.setIdEmail(rs.getInt("idEmail"));
                email.setEmail(rs.getString("Email"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar email por endereço: " + e.getMessage());
        } finally {
            Conexao.fechar(conn, stmt, rs);
        }
        return email;
    }

    // MÉTODO ADICIONADO: Para uso em transações com AlunoDAO
    public Email inserirComTransacao(Connection conn, Email email) throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = "INSERT INTO email (Email) VALUES (?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, email.getEmail());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    email.setIdEmail(rs.getInt(1));
                }
            }
            return email;
        } finally {
            // Não fecha a conexão aqui - será fechada no AlunoDAO
            if (stmt != null) stmt.close();
            if (rs != null) rs.close();
        }
    }
}