package dao.InterfacesDAO;

import model.Matricula;
import java.util.List;

public interface IMatriculaDAO {

    Matricula inserir(Matricula matricula);

    Matricula buscarPorId(int id);

    List<Matricula> listarTodos();
    List<Matricula> buscarPorAluno(int idAluno);
    List<Matricula> buscarPorCurso(int idCurso);

    boolean atualizar(Matricula matricula);
    boolean excluir(int id);

    boolean existeMatricula(int idAluno, int idCurso);
    int contarMatriculasPorCurso(int idCurso);
    int contarMatriculasPorAluno(int idAluno);
}
