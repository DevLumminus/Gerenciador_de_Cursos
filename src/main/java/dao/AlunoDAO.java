package dao;

import model.Aluno;
import model.Email;
import util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {
    private IEmailDAO emailDAO;

    // Construtor com injeção de dependência
    public AlunoDAO(IEmailDAO emailDAO) {
        this.emailDAO = emailDAO;
    }

    public static AlunoDAO criar() {
        return new AlunoDAO(new EmailDAO());
    }

    public static AlunoDAO criarComMock(IEmailDAO emailDAO) {
        return new AlunoDAO(emailDAO);
    }

    public Aluno inserir(Aluno aluno) {
        // Valida se o email_id existe antes de inserir
        if (!emailExiste(aluno.getEmail())) {
            System.err.println("Erro: Email ID " + aluno.getEmail() + " não existe na tabela email");
            return aluno;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            String sql = "INSERT INTO alunos (Nome, Telefone, Email, cpf) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getTelefone());
            stmt.setInt(3, aluno.getEmail());
            stmt.setString(4, aluno.getCpf());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    aluno.setIdAlunos(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir aluno: " + e.getMessage());
        } finally {
            Conexao.fechar(conn, stmt, rs);
        }
        return aluno;
    }

    public Aluno inserirAlunoComEmail(Aluno aluno, String enderecoEmail) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            conn.setAutoCommit(false); // Inicia transação

            Email email = emailDAO.buscarPorEndereco(enderecoEmail);
            if (email == null) {
                // Email não existe, cria um novo
                email = new Email(0, enderecoEmail);
                email = emailDAO.inserirComTransacao(conn, email);

                if (email == null || email.getIdEmail() == 0) {
                    throw new SQLException("Falha ao inserir email: " + enderecoEmail);
                }
            }

            // 2. Insere o aluno com o email_id correto
            aluno.setEmail(email.getIdEmail());
            String sql = "INSERT INTO alunos (Nome, Telefone, Email, cpf) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getTelefone());
            stmt.setInt(3, aluno.getEmail());
            stmt.setString(4, aluno.getCpf());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    aluno.setIdAlunos(rs.getInt(1));
                }
            }

            conn.commit(); // Confirma transação
            return aluno;

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(); // Rollback em caso de erro
            }
            System.err.println("Erro ao inserir aluno com email: " + e.getMessage());
            throw e;
        } finally {
            Conexao.fechar(conn, stmt, rs);
        }
    }

    public Aluno buscarPorId(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Aluno aluno = null;

        try {
            conn = Conexao.conectar();
            String sql = "SELECT * FROM alunos WHERE idAlunos = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                aluno = new Aluno();
                aluno.setIdAlunos(rs.getInt("idAlunos"));
                aluno.setNome(rs.getString("Nome"));
                aluno.setTelefone(rs.getString("Telefone"));
                aluno.setEmail(rs.getInt("Email"));
                aluno.setCpf(rs.getString("cpf"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar aluno: " + e.getMessage());
        } finally {
            Conexao.fechar(conn, stmt, rs);
        }
        return aluno;
    }

    public Aluno buscarPorIdComEmail(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Aluno aluno = null;

        try {
            conn = Conexao.conectar();
            String sql = "SELECT a.*, e.Email as endereco_email " +
                    "FROM alunos a " +
                    "INNER JOIN email e ON a.Email = e.idEmail " +
                    "WHERE a.idAlunos = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                aluno = new Aluno();
                aluno.setIdAlunos(rs.getInt("idAlunos"));
                aluno.setNome(rs.getString("Nome"));
                aluno.setTelefone(rs.getString("Telefone"));
                aluno.setEmail(rs.getInt("Email")); // mantém o ID
                aluno.setCpf(rs.getString("cpf"));

                // Informação adicional disponível
                String emailReal = rs.getString("endereco_email");
                System.out.println("Email do aluno: " + emailReal);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar aluno com email: " + e.getMessage());
        } finally {
            Conexao.fechar(conn, stmt, rs);
        }
        return aluno;
    }

    public List<Aluno> listarTodos() {
        List<Aluno> alunos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            String sql = "SELECT * FROM alunos ORDER BY Nome";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setIdAlunos(rs.getInt("idAlunos"));
                aluno.setNome(rs.getString("Nome"));
                aluno.setTelefone(rs.getString("Telefone"));
                aluno.setEmail(rs.getInt("Email"));
                aluno.setCpf(rs.getString("cpf"));

                alunos.add(aluno);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar alunos: " + e.getMessage());
        } finally {
            Conexao.fechar(conn, stmt, rs);
        }
        return alunos;
    }

    public List<Aluno> listarTodosComEmail() {
        List<Aluno> alunos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            String sql = "SELECT a.*, e.Email as endereco_email " +
                    "FROM alunos a " +
                    "INNER JOIN email e ON a.Email = e.idEmail " +
                    "ORDER BY a.Nome";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setIdAlunos(rs.getInt("idAlunos"));
                aluno.setNome(rs.getString("Nome"));
                aluno.setTelefone(rs.getString("Telefone"));
                aluno.setEmail(rs.getInt("Email"));
                aluno.setCpf(rs.getString("cpf"));

                // Email real disponível para uso
                String emailReal = rs.getString("endereco_email");
                System.out.println("Aluno: " + aluno.getNome() + " - Email: " + emailReal);

                alunos.add(aluno);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar alunos com email: " + e.getMessage());
        } finally {
            Conexao.fechar(conn, stmt, rs);
        }
        return alunos;
    }

    public boolean atualizar(Aluno aluno) {
        // Valida se o novo email_id existe
        if (!emailExiste(aluno.getEmail())) {
            System.err.println("Erro: Email ID " + aluno.getEmail() + " não existe na tabela email");
            return false;
        }

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexao.conectar();
            String sql = "UPDATE alunos SET Nome = ?, Telefone = ?, Email = ?, cpf = ? WHERE idAlunos = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getTelefone());
            stmt.setInt(3, aluno.getEmail());
            stmt.setString(4, aluno.getCpf());
            stmt.setInt(5, aluno.getIdAlunos());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar aluno: " + e.getMessage());
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
            String sql = "DELETE FROM alunos WHERE idAlunos = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir aluno: " + e.getMessage());
            return false;
        } finally {
            Conexao.fechar(conn, stmt, null);
        }
    }

    public Aluno buscarPorCpf(String cpf) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Aluno aluno = null;

        try {
            conn = Conexao.conectar();
            String sql = "SELECT * FROM alunos WHERE cpf = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cpf);
            rs = stmt.executeQuery();

            if (rs.next()) {
                aluno = new Aluno();
                aluno.setIdAlunos(rs.getInt("idAlunos"));
                aluno.setNome(rs.getString("Nome"));
                aluno.setTelefone(rs.getString("Telefone"));
                aluno.setEmail(rs.getInt("Email"));
                aluno.setCpf(rs.getString("cpf"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar aluno por CPF: " + e.getMessage());
        } finally {
            Conexao.fechar(conn, stmt, rs);
        }
        return aluno;
    }

    public Aluno buscarPorCpfComEmail(String cpf) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Aluno aluno = null;

        try {
            conn = Conexao.conectar();
            String sql = "SELECT a.*, e.Email as endereco_email " +
                    "FROM alunos a " +
                    "INNER JOIN email e ON a.Email = e.idEmail " +
                    "WHERE a.cpf = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cpf);
            rs = stmt.executeQuery();

            if (rs.next()) {
                aluno = new Aluno();
                aluno.setIdAlunos(rs.getInt("idAlunos"));
                aluno.setNome(rs.getString("Nome"));
                aluno.setTelefone(rs.getString("Telefone"));
                aluno.setEmail(rs.getInt("Email"));
                aluno.setCpf(rs.getString("cpf"));

                String emailReal = rs.getString("endereco_email");
                System.out.println("Email do aluno: " + emailReal);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar aluno por CPF com email: " + e.getMessage());
        } finally {
            Conexao.fechar(conn, stmt, rs);
        }
        return aluno;
    }

    public List<Aluno> buscarPorNome(String nome) {
        List<Aluno> alunos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            String sql = "SELECT * FROM alunos WHERE Nome LIKE ? ORDER BY Nome";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + nome + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setIdAlunos(rs.getInt("idAlunos"));
                aluno.setNome(rs.getString("Nome"));
                aluno.setTelefone(rs.getString("Telefone"));
                aluno.setEmail(rs.getInt("Email"));
                aluno.setCpf(rs.getString("cpf"));

                alunos.add(aluno);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar alunos por nome: " + e.getMessage());
        } finally {
            Conexao.fechar(conn, stmt, rs);
        }
        return alunos;
    }

    private boolean emailExiste(int idEmail) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            String sql = "SELECT COUNT(*) FROM email WHERE idEmail = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idEmail);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar existência de email: " + e.getMessage());
        } finally {
            Conexao.fechar(conn, stmt, rs);
        }
        return false;
    }

    public String buscarEnderecoEmailPorAlunoId(int idAluno) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            String sql = "SELECT e.Email FROM email e " +
                    "INNER JOIN alunos a ON e.idEmail = a.Email " +
                    "WHERE a.idAlunos = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idAluno);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("Email");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar endereço de email: " + e.getMessage());
        } finally {
            Conexao.fechar(conn, stmt, rs);
        }
        return null;
    }

    public boolean cpfExiste(String cpf) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.conectar();
            String sql = "SELECT COUNT(*) FROM alunos WHERE cpf = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cpf);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar existência de CPF: " + e.getMessage());
        } finally {
            Conexao.fechar(conn, stmt, rs);
        }
        return false;
    }
}