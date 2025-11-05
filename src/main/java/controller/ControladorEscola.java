package controller;

import dao.*;
import model.*;
import java.util.List;

public class ControladorEscola {

    private final AlunoDAO alunoDAO;
    private final CursoDAO cursoDAO;
    private final EmailDAO emailDAO;
    private final MatriculaDAO matriculaDAO;

    public ControladorEscola() {
        this.alunoDAO = new AlunoDAO();
        this.cursoDAO = new CursoDAO();
        this.emailDAO = new EmailDAO();
        this.matriculaDAO = new MatriculaDAO();
    }

    // ========== OPERAÇÕES DE ALUNO ==========
    public Aluno cadastrarAluno(String nome, String telefone, int idEmail, String cpf) {
        // Verifica se o email existe
        Email email = emailDAO.buscarPorId(idEmail);
        if (email == null) {
            throw new IllegalArgumentException("Email não encontrado com ID: " + idEmail);
        }

        // Verifica se CPF já existe
        if (alunoDAO.buscarPorCpf(cpf) != null) {
            throw new IllegalArgumentException("CPF já cadastrado: " + cpf);
        }

        Aluno aluno = new Aluno();
        aluno.setNome(nome);
        aluno.setTelefone(telefone);
        aluno.setEmail(idEmail);
        aluno.setCpf(cpf);

        return alunoDAO.inserir(aluno);
    }

    public Aluno cadastrarAlunoCompleto(String nome, String telefone, String enderecoEmail, String cpf) {
        // Verifica se email já existe
        Email emailExistente = emailDAO.buscarPorEndereco(enderecoEmail);
        if (emailExistente != null) {
            throw new IllegalArgumentException("Email já cadastrado: " + enderecoEmail);
        }

        // Verifica se CPF já existe
        if (alunoDAO.buscarPorCpf(cpf) != null) {
            throw new IllegalArgumentException("CPF já cadastrado: " + cpf);
        }

        // Cria o email
        Email email = new Email(0, enderecoEmail);
        email = emailDAO.inserir(email);

        // Cria o aluno
        Aluno aluno = new Aluno();
        aluno.setNome(nome);
        aluno.setTelefone(telefone);
        aluno.setEmail(email.getIdEmail());
        aluno.setCpf(cpf);

        return alunoDAO.inserir(aluno);
    }

    public Aluno buscarAlunoPorId(int id) {
        return alunoDAO.buscarPorId(id);
    }

    public Aluno buscarAlunoPorCpf(String cpf) {
        return alunoDAO.buscarPorCpf(cpf);
    }

    public List<Aluno> listarTodosAlunos() {
        return alunoDAO.listarTodos();
    }

    public List<Aluno> buscarAlunosPorNome(String nome) {
        return alunoDAO.buscarPorNome(nome);
    }

    public boolean atualizarAluno(Aluno aluno) {
        return alunoDAO.atualizar(aluno);
    }

    public boolean excluirAluno(int id) {
        // Verifica se o aluno tem matrículas antes de excluir
        List<Matricula> matriculas = matriculaDAO.buscarPorAluno(id);
        if (!matriculas.isEmpty()) {
            throw new IllegalStateException("Não é possível excluir aluno com matrículas ativas");
        }
        return alunoDAO.excluir(id);
    }

    // ========== OPERAÇÕES DE CURSO ==========
    public Cursos cadastrarCurso(String nome, String tipo) {
        // Verifica se curso com mesmo nome já existe
        if (cursoDAO.buscarPorNome(nome) != null) {
            throw new IllegalArgumentException("Já existe um curso com o nome: " + nome);
        }

        Cursos curso = new Cursos();
        curso.setNome(nome);
        curso.setTipo(tipo);
        curso.setAtivação(true); // Ativo por padrão

        return cursoDAO.inserir(curso);
    }

    public Cursos buscarCursoPorId(int id) {
        return cursoDAO.buscarPorId(id);
    }

    public List<Cursos> listarTodosCursos() {
        return cursoDAO.listarTodos();
    }

    public List<Cursos> listarCursosAtivos() {
        return cursoDAO.listarAtivos();
    }

    public List<Cursos> listarCursosInativos() {
        return cursoDAO.listarInativos();
    }

    public List<Cursos> buscarCursosPorTipo(String tipo) {
        return cursoDAO.buscarPorTipo(tipo);
    }

    public boolean atualizarCurso(Cursos curso) {
        return cursoDAO.atualizar(curso);
    }

    public boolean ativarCurso(int idCurso) {
        return cursoDAO.ativarCurso(idCurso);
    }

    public boolean desativarCurso(int idCurso) {
        // Verifica se o curso tem matrículas antes de desativar
        List<Matricula> matriculas = matriculaDAO.buscarPorCurso(idCurso);
        if (!matriculas.isEmpty()) {
            throw new IllegalStateException("Não é possível desativar curso com matrículas ativas");
        }
        return cursoDAO.desativarCurso(idCurso);
    }

    public boolean excluirCurso(int id) {
        // Verifica se o curso tem matrículas antes de excluir
        List<Matricula> matriculas = matriculaDAO.buscarPorCurso(id);
        if (!matriculas.isEmpty()) {
            throw new IllegalStateException("Não é possível excluir curso com matrículas ativas");
        }
        return cursoDAO.excluir(id);
    }

    // ========== OPERAÇÕES DE MATRÍCULA ==========
    public Matricula matricularAluno(int idAluno, int idCurso) {
        // Verifica se aluno existe
        Aluno aluno = alunoDAO.buscarPorId(idAluno);
        if (aluno == null) {
            throw new IllegalArgumentException("Aluno não encontrado com ID: " + idAluno);
        }

        // Verifica se curso existe e está ativo
        Cursos curso = cursoDAO.buscarPorId(idCurso);
        if (curso == null) {
            throw new IllegalArgumentException("Curso não encontrado com ID: " + idCurso);
        }
        if (!curso.isAtivação()) {
            throw new IllegalStateException("Curso não está ativo para matrícula");
        }

        // Verifica se já existe matrícula
        if (matriculaDAO.existeMatricula(idAluno, idCurso)) {
            throw new IllegalStateException("Aluno já está matriculado neste curso");
        }

        Matricula matricula = new Matricula(0, idAluno, idCurso);
        return matriculaDAO.inserir(matricula);
    }

    public Matricula buscarMatriculaPorId(int id) {
        return matriculaDAO.buscarPorId(id);
    }

    public List<Matricula> listarTodasMatriculas() {
        return matriculaDAO.listarTodos();
    }

    public List<Matricula> listarMatriculasPorAluno(int idAluno) {
        return matriculaDAO.buscarPorAluno(idAluno);
    }

    public List<Matricula> listarMatriculasPorCurso(int idCurso) {
        return matriculaDAO.buscarPorCurso(idCurso);
    }

    public boolean cancelarMatricula(int idMatricula) {
        return matriculaDAO.excluir(idMatricula);
    }

    public int contarMatriculasPorCurso(int idCurso) {
        return matriculaDAO.contarMatriculasPorCurso(idCurso);
    }

    public int contarMatriculasPorAluno(int idAluno) {
        return matriculaDAO.contarMatriculasPorAluno(idAluno);
    }

    // ========== OPERAÇÕES DE EMAIL ==========
    public Email cadastrarEmail(String enderecoEmail) {
        // Verifica se email já existe
        if (emailDAO.buscarPorEndereco(enderecoEmail) != null) {
            throw new IllegalArgumentException("Email já cadastrado: " + enderecoEmail);
        }

        Email email = new Email(0, enderecoEmail);
        return emailDAO.inserir(email);
    }

    public Email buscarEmailPorId(int id) {
        return emailDAO.buscarPorId(id);
    }

    public Email buscarEmailPorEndereco(String endereco) {
        return emailDAO.buscarPorEndereco(endereco);
    }

    public List<Email> listarTodosEmails() {
        return emailDAO.listarTodos();
    }

    public boolean atualizarEmail(Email email) {
        return emailDAO.atualizar(email);
    }

    public boolean excluirEmail(int id) {
        // Verifica se o email está sendo usado por algum aluno
        List<Aluno> alunos = alunoDAO.listarTodos();
        for (Aluno aluno : alunos) {
            if (aluno.getEmail() == id) {
                throw new IllegalStateException("Não é possível excluir email vinculado a um aluno");
            }
        }
        return emailDAO.excluir(id);
    }

    // ========== RELATÓRIOS E ESTATÍSTICAS ==========
    public void gerarRelatorioGeral() {
        System.out.println("=== RELATÓRIO GERAL DA PLATAFORMA ===");
        System.out.println("Total de Alunos: " + alunoDAO.listarTodos().size());
        System.out.println("Total de Cursos: " + cursoDAO.listarTodos().size());
        System.out.println("Total de Cursos Ativos: " + cursoDAO.listarAtivos().size());
        System.out.println("Total de Matrículas: " + matriculaDAO.listarTodos().size());
        System.out.println("Total de Emails: " + emailDAO.listarTodos().size());
        System.out.println("=====================================");
    }
}