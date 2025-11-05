package dao;

import model.Matricula;
import util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatriculaDAO {

    public Matricula inserir(Matricula matricula) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            String sql = "INSERT INTO matricula (Aluno, Curso) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, matricula.getAluno());
            stmt.setInt(2, matricula.getCurso());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    matricula.setIdMatricula(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir matrícula: " + e.getMessage());
        } finally {
            Conexao.fechar(conn, stmt, rs);
        }
        return matricula;
    }

    public Matricula buscarPorId(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Matricula matricula = null;

        try {
            conn = Conexao.conectar();
            String sql = "SELECT * FROM matricula WHERE idMatricula = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                matricula = new Matricula();
                matricula.setIdMatricula(rs.getInt("idMatricula"));
                matricula.setAluno(rs.getInt("Aluno"));
                matricula.setCurso(rs.getInt("Curso"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar matrícula: " + e.getMessage());
        } finally {
            Conexao.fechar(conn, stmt, rs);
        }
        return matricula;
    }

    public List<Matricula> listarTodos() {
        List<Matricula> matriculas = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            String sql = "SELECT * FROM matricula ORDER BY idMatricula";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Matricula matricula = new Matricula();
                matricula.setIdMatricula(rs.getInt("idMatricula"));
                matricula.setAluno(rs.getInt("Aluno"));
                matricula.setCurso(rs.getInt("Curso"));
                matriculas.add(matricula);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar matrículas: " + e.getMessage());
        } finally {
            Conexao.fechar(conn, stmt, rs);
        }
        return matriculas;
    }

    public boolean atualizar(Matricula matricula) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexao.conectar();
            String sql = "UPDATE matricula SET Aluno = ?, Curso = ? WHERE idMatricula = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, matricula.getAluno());
            stmt.setInt(2, matricula.getCurso());
            stmt.setInt(3, matricula.getIdMatricula());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar matrícula: " + e.getMessage());
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
            String sql = "DELETE FROM matricula WHERE idMatricula = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir matrícula: " + e.getMessage());
            return false;
        } finally {
            Conexao.fechar(conn, stmt, null);
        }
    }

    public List<Matricula> buscarPorAluno(int idAluno) {
        List<Matricula> matriculas = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            String sql = "SELECT * FROM matricula WHERE Aluno = ? ORDER BY idMatricula";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idAluno);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Matricula matricula = new Matricula();
                matricula.setIdMatricula(rs.getInt("idMatricula"));
                matricula.setAluno(rs.getInt("Aluno"));
                matricula.setCurso(rs.getInt("Curso"));
                matriculas.add(matricula);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar matrículas por aluno: " + e.getMessage());
        } finally {
            Conexao.fechar(conn, stmt, rs);
        }
        return matriculas;
    }

    public List<Matricula> buscarPorCurso(int idCurso) {
        List<Matricula> matriculas = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            String sql = "SELECT * FROM matricula WHERE Curso = ? ORDER BY idMatricula";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idCurso);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Matricula matricula = new Matricula();
                matricula.setIdMatricula(rs.getInt("idMatricula"));
                matricula.setAluno(rs.getInt("Aluno"));
                matricula.setCurso(rs.getInt("Curso"));
                matriculas.add(matricula);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar matrículas por curso: " + e.getMessage());
        } finally {
            Conexao.fechar(conn, stmt, rs);
        }
        return matriculas;
    }

    public boolean existeMatricula(int idAluno, int idCurso) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            String sql = "SELECT COUNT(*) FROM matricula WHERE Aluno = ? AND Curso = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idAluno);
            stmt.setInt(2, idCurso);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar existência de matrícula: " + e.getMessage());
        } finally {
            Conexao.fechar(conn, stmt, rs);
        }
        return false;
    }

    public int contarMatriculasPorCurso(int idCurso) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            String sql = "SELECT COUNT(*) FROM matricula WHERE Curso = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idCurso);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao contar matrículas por curso: " + e.getMessage());
        } finally {
            Conexao.fechar(conn, stmt, rs);
        }
        return 0;
    }

    public int contarMatriculasPorAluno(int idAluno) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            String sql = "SELECT COUNT(*) FROM matricula WHERE Aluno = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idAluno);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao contar matrículas por aluno: " + e.getMessage());
        } finally {
            Conexao.fechar(conn, stmt, rs);
        }
        return 0;
    }
}