package controller;

import dao.InterfacesDAO.ICursoDAO;
import dao.CursoDAO;
import model.Cursos;
import java.util.List;

public class cursocontroller extends controllerbase<Cursos> {

    private final ICursoDAO cursoDAO;

    // ✅ Construtor recebe DAO diretamente
    public cursocontroller(ICursoDAO cursoDAO) {
        if (cursoDAO == null) {
            throw new IllegalArgumentException("DAO de curso não pode ser nulo");
        }
        this.cursoDAO = cursoDAO;
    }

    @Override
    public Cursos criar(Cursos curso) {
        validarCurso(curso);
        return cursoDAO.inserir(curso);
    }

    @Override
    public Cursos buscarPorId(int id) {
        validarId(id);
        return cursoDAO.buscarPorId(id);
    }

    @Override
    public List<Cursos> listarTodos() {
        return cursoDAO.listarTodos();
    }

    // ✅ Método para compatibilidade
    public List<Cursos> listarTodasCurso() {
        return cursoDAO.listarTodos();
    }

    public List<Cursos> listarAtivos() {
        return cursoDAO.listarAtivos();
    }

    public List<Cursos> listarInativos() {
        return cursoDAO.listarInativos();
    }

    public List<Cursos> buscarPorTipo(String tipo) {
        validacaoStringNaoVazia(tipo, "Tipo");
        return cursoDAO.buscarPorTipo(tipo);
    }

    public Cursos buscarPorNome(String nome) {
        validacaoStringNaoVazia(nome, "Nome");
        return cursoDAO.buscarPorNome(nome);
    }

    @Override
    public boolean atualizar(Cursos curso) {
        validarCurso(curso);
        return cursoDAO.atualizar(curso);
    }

    @Override
    public boolean excluir(int id) {
        validarId(id);
        return cursoDAO.excluir(id);
    }

    public boolean ativarCurso(int id) {
        validarId(id);
        return cursoDAO.ativarCurso(id);
    }

    public boolean desativarCurso(int id) {
        validarId(id);
        return cursoDAO.desativarCurso(id);
    }

    public boolean validarCursoParaMatricula(int idCurso) {
        validarId(idCurso);
        Cursos curso = cursoDAO.buscarPorId(idCurso);
        return curso != null && curso.isAtivação();
    }

    public boolean cursoExiste(int idCurso) {
        validarId(idCurso);
        return cursoDAO.buscarPorId(idCurso) != null;
    }

    // ✅ VALIDAÇÕES ESPECÍFICAS
    private void validarCurso(Cursos curso) {
        if (curso == null) {
            throw new IllegalArgumentException("Curso não pode ser nulo");
        }
        validacaoStringNaoVazia(curso.getNome(), "Nome do curso");
        validacaoStringNaoVazia(curso.getTipo(), "Tipo do curso");

        if (curso.getNome().length() > 100) {
            throw new IllegalArgumentException("Nome do curso muito longo (max 100 caracteres)");
        }
    }

    private void validarId(int id) {
        validacaoNumeroPositivo(id, "ID Curso");
    }
}