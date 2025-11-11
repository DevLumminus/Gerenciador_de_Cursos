package controller.InterfacesServico;


import java.util.List;
import model.Cursos;


public interface ICursoServico {
    Cursos criar(Cursos curso);
    void atualizar(Cursos curso);
    void remover(int idCurso);
    boolean ativarCurso(int id);
    boolean desativarCurso(int id);
    Cursos buscarPorId(int idCurso);
    List<Cursos> listarTodos();
    List<Cursos> listarTodasCurso();
}
