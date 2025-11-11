package controller.InterfacesServico;

import model.Matricula;
import java.util.List;

public interface IMatriculaServico {
    void criar(Matricula matricula);
    void atualizar(Matricula matricula);
    void remover(int idMatricula);
    Matricula buscarPorId(int idMatricula);
    List<Matricula> listarTodos();
    List<Matricula> listarTodasMatriculas();
}
