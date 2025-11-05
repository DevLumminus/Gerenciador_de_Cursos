package dao;

import model.Cursos;
import util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    public Cursos inserir(Cursos curso) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            String sql = "INSERT INTO cursos (Nome, Tipo, Ativação) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getTipo());
            stmt.setBoolean(3, curso.isAtivação());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    curso.setIdCursos(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir curso: " + e.getMessage());
        } finally {
            Conexao.fechar(conn, stmt, rs);
        }
        return curso;
    }

    public Cursos buscarPorId(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Cursos curso = null;

        try {
            conn = Conexao.conectar();
            String sql = "SELECT * FROM cursos WHERE idCursos = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                curso = new Cursos();
                curso.setIdCursos(rs.getInt("idCursos"));
                curso.setNome(rs.getString("Nome"));
                curso.setTipo(rs.getString("Tipo"));
                curso.setAtivação(rs.getBoolean("Ativação"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar curso: " + e.getMessage());
        } finally {
            Conexao.fechar(conn, stmt, rs);
        }
        return curso;
    }

    public List<Cursos> listarTodos() {
        List<Cursos> cursos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            String sql = "SELECT * FROM cursos ORDER BY Nome";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Cursos curso = new Cursos();
                curso.setIdCursos(rs.getInt("idCursos"));
                curso.setNome(rs.getString("Nome"));
                curso.setTipo(rs.getString("Tipo"));
                curso.setAtivação(rs.getBoolean("Ativação"));
                cursos.add(curso);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar cursos: " + e.getMessage());
        } finally {
            Conexao.fechar(conn, stmt, rs);
        }
        return cursos;
    }

    public List<Cursos> listarAtivos() {
        List<Cursos> cursos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            String sql = "SELECT * FROM cursos WHERE Ativação = true ORDER BY Nome";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Cursos curso = new Cursos();
                curso.setIdCursos(rs.getInt("idCursos"));
                curso.setNome(rs.getString("Nome"));
                curso.setTipo(rs.getString("Tipo"));
                curso.setAtivação(true);
                cursos.add(curso);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar cursos ativos: " + e.getMessage());
        } finally {
            Conexao.fechar(conn, stmt, rs);
        }
        return cursos;
    }

    public List<Cursos> listarInativos() {
        List<Cursos> cursos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            String sql = "SELECT * FROM cursos WHERE Ativação = false ORDER BY Nome";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Cursos curso = new Cursos();
                curso.setIdCursos(rs.getInt("idCursos"));
                curso.setNome(rs.getString("Nome"));
                curso.setTipo(rs.getString("Tipo"));
                curso.setAtivação(false);
                cursos.add(curso);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar cursos inativos: " + e.getMessage());
        } finally {
            Conexao.fechar(conn, stmt, rs);
        }
        return cursos;
    }

    public boolean atualizar(Cursos curso) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexao.conectar();
            String sql = "UPDATE cursos SET Nome = ?, Tipo = ?, Ativação = ? WHERE idCursos = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getTipo());
            stmt.setBoolean(3, curso.isAtivação());
            stmt.setInt(4, curso.getIdCursos());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar curso: " + e.getMessage());
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
            String sql = "DELETE FROM cursos WHERE idCursos = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir curso: " + e.getMessage());
            return false;
        } finally {
            Conexao.fechar(conn, stmt, null);
        }
    }

    public boolean ativarCurso(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexao.conectar();
            String sql = "UPDATE cursos SET Ativação = true WHERE idCursos = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao ativar curso: " + e.getMessage());
            return false;
        } finally {
            Conexao.fechar(conn, stmt, null);
        }
    }

    public boolean desativarCurso(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexao.conectar();
            String sql = "UPDATE cursos SET Ativação = false WHERE idCursos = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao desativar curso: " + e.getMessage());
            return false;
        } finally {
            Conexao.fechar(conn, stmt, null);
        }
    }

    public List<Cursos> buscarPorTipo(String tipo) {
        List<Cursos> cursos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            String sql = "SELECT * FROM cursos WHERE Tipo LIKE ? ORDER BY Nome";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + tipo + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Cursos curso = new Cursos();
                curso.setIdCursos(rs.getInt("idCursos"));
                curso.setNome(rs.getString("Nome"));
                curso.setTipo(rs.getString("Tipo"));
                curso.setAtivação(rs.getBoolean("Ativação"));
                cursos.add(curso);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cursos por tipo: " + e.getMessage());
        } finally {
            Conexao.fechar(conn, stmt, rs);
        }
        return cursos;
    }

    public Cursos buscarPorNome(String nome) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Cursos curso = null;

        try {
            conn = Conexao.conectar();
            String sql = "SELECT * FROM cursos WHERE Nome = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            rs = stmt.executeQuery();

            if (rs.next()) {
                curso = new Cursos();
                curso.setIdCursos(rs.getInt("idCursos"));
                curso.setNome(rs.getString("Nome"));
                curso.setTipo(rs.getString("Tipo"));
                curso.setAtivação(rs.getBoolean("Ativação"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar curso por nome: " + e.getMessage());
        } finally {
            Conexao.fechar(conn, stmt, rs);
        }
        return curso;
    }
}