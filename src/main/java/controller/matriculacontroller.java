package controller;

import dao.InterfacesDAO.IMatriculaDAO;
import dao.MatriculaDAO;
import model.Matricula;
import java.util.List;

public class matriculacontroller extends controllerbase<Matricula> {

    private final IMatriculaDAO matriculaDAO;

    // ✅ Construtor recebe DAO diretamente
    public matriculacontroller(IMatriculaDAO matriculaDAO) {
        if (matriculaDAO == null) {
            throw new IllegalArgumentException("DAO de matrícula não pode ser nulo");
        }
        this.matriculaDAO = matriculaDAO;
    }

    // 🎯 MÉTODO PRINCIPAL: Matricular aluno em curso
    public Matricula matricularAluno(int idAluno, int idCurso) {
        validarId(idAluno, "ID Aluno");
        validarId(idCurso, "ID Curso");

        // Verifica se matrícula já existe
        if (matriculaDAO.existeMatricula(idAluno, idCurso)) {
            throw new IllegalArgumentException("Aluno já matriculado neste curso");
        }

        Matricula matricula = new Matricula(0, idAluno, idCurso);
        return matriculaDAO.inserir(matricula);
    }

    @Override
    public Matricula criar(Matricula matricula) {
        // Delega para o método principal
        return matricularAluno(matricula.getAluno(), matricula.getCurso());
    }

    @Override
    public Matricula buscarPorId(int id) {
        validarId(id, "ID Matrícula");
        return matriculaDAO.buscarPorId(id);
    }

    @Override
    public List<Matricula> listarTodos() {
        return matriculaDAO.listarTodos();
    }

    // ✅ Método para compatibilidade
    public List<Matricula> listarTodasMatriculas() {
        return matriculaDAO.listarTodos();
    }

    public List<Matricula> buscarPorAluno(int idAluno) {
        validarId(idAluno, "ID Aluno");
        return matriculaDAO.buscarPorAluno(idAluno);
    }

    public List<Matricula> buscarPorCurso(int idCurso) {
        validarId(idCurso, "ID Curso");
        return matriculaDAO.buscarPorCurso(idCurso);
    }

    @Override
    public boolean atualizar(Matricula matricula) {
        // Para matrícula, atualização normalmente não é permitida
        throw new UnsupportedOperationException("Atualização de matrícula não permitida. Cancele e crie nova matrícula.");
    }

    @Override
    public boolean excluir(int id) {
        validarId(id, "ID Matrícula");
        return matriculaDAO.excluir(id);
    }

    public boolean cancelarMatricula(int idMatricula) {
        return excluir(idMatricula);
    }

    public int contarMatriculasPorCurso(int idCurso) {
        validarId(idCurso, "ID Curso");
        return matriculaDAO.contarMatriculasPorCurso(idCurso);
    }

    public int contarMatriculasPorAluno(int idAluno) {
        validarId(idAluno, "ID Aluno");
        return matriculaDAO.contarMatriculasPorAluno(idAluno);
    }

    public boolean verificarMatriculaExistente(int idAluno, int idCurso) {
        return matriculaDAO.existeMatricula(idAluno, idCurso);
    }

    // ✅ VALIDAÇÕES ESPECÍFICAS
    private void validarId(int id, String campo) {
        validacaoNumeroPositivo(id, campo);
    }
}