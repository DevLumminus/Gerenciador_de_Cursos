package controller;

import dao.InterfacesDAO.IAlunoDAO;
import dao.InterfacesDAO.IEmailDAO;
import model.Aluno;
import java.util.List;

public class alunocontroller extends controllerbase<Aluno> {

    private final IAlunoDAO alunoDAO;
    private final IEmailDAO emailDAO;

    // ✅ Construtor recebe DAOs diretamente
    public alunocontroller(IAlunoDAO alunoDAO, IEmailDAO emailDAO) {
        if (alunoDAO == null || emailDAO == null) {
            throw new IllegalArgumentException("DAOs não podem ser nulos");
        }
        this.alunoDAO = alunoDAO;
        this.emailDAO = emailDAO;
    }

    // 🎯 MÉTODO PRINCIPAL: Matricular aluno com email
    public Aluno matricularAlunoComEmail(String nome, String telefone, String cpf, String enderecoEmail) {
        validarDadosAluno(nome, telefone, cpf, enderecoEmail);

        try {
            // Lógica de matrícula direto no DAO
            Aluno aluno = new Aluno(0, nome, telefone, 0, cpf);
            return alunoDAO.inserirAlunoComEmail(aluno, enderecoEmail);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao matricular aluno: " + e.getMessage(), e);
        }
    }

    @Override
    public Aluno criar(Aluno aluno) {
        validarAluno(aluno);
        return alunoDAO.inserir(aluno);
    }

    @Override
    public Aluno buscarPorId(int id) {
        validarId(id);
        return alunoDAO.buscarPorId(id);
    }

    public Aluno buscarPorIdComEmail(int id) {
        validarId(id);
        return alunoDAO.buscarPorIdComEmail(id);
    }

    @Override
    public List<Aluno> listarTodos() {
        return alunoDAO.listarTodos();
    }

    public List<Aluno> listarTodosComEmail() {
        return alunoDAO.listarTodosComEmail();
    }

    public List<Aluno> buscarPorNome(String nome) {
        validarNome(nome);
        return alunoDAO.buscarPorNome(nome);
    }

    public Aluno buscarPorCpf(String cpf) {
        validarCpf(cpf);
        return alunoDAO.buscarPorCpf(cpf);
    }

    public Aluno buscarPorCpfComEmail(String cpf) {
        validarCpf(cpf);
        return alunoDAO.buscarPorCpfComEmail(cpf);
    }

    @Override
    public boolean atualizar(Aluno aluno) {
        validarAluno(aluno);
        return alunoDAO.atualizar(aluno);
    }

    @Override
    public boolean excluir(int id) {
        validarId(id);
        return alunoDAO.excluir(id);
    }

    public boolean cpfExiste(String cpf) {
        return alunoDAO.cpfExiste(cpf);
    }

    public String obterEmailAluno(int idAluno) {
        validarId(idAluno);
        return alunoDAO.buscarEnderecoEmailPorAlunoId(idAluno);
    }

    public boolean validarAlunoParaMatricula(int idAluno) {
        validarId(idAluno);
        Aluno aluno = alunoDAO.buscarPorId(idAluno);
        return aluno != null && alunoDAO.buscarEnderecoEmailPorAlunoId(idAluno) != null;
    }

    // ✅ VALIDAÇÕES ESPECÍFICAS
    private void validarDadosAluno(String nome, String telefone, String cpf, String email) {
        validacaoStringNaoVazia(nome, "Nome");
        validacaoStringNaoVazia(telefone, "Telefone");
        validarCpf(cpf);
        validarEmail(email);
    }

    private void validarAluno(Aluno aluno) {
        if (aluno == null) {
            throw new IllegalArgumentException("Aluno não pode ser nulo");
        }
        validarDadosAluno(aluno.getNome(), aluno.getTelefone(), aluno.getCpf(), "");
    }

    private void validarCpf(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF inválido deve conter 11 dígitos");
        }
    }

    private void validarNome(String nome) {
        validacaoStringNaoVazia(nome, "Nome");
    }

    private void validarId(int id) {
        validacaoNumeroPositivo(id, "ID");
    }

    private void validarEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Email inválido: " + email);
        }
    }
}