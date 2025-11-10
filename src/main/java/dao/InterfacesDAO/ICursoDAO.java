package dao.InterfacesDAO;

import model.Cursos;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ICursoDAO {

    // Inserção
    Cursos inserir(Cursos curso);

    // Inserção dentro de uma transação (útil para operações compostas)
    default Cursos inserirComTransacao(Connection conn, Cursos curso) throws SQLException {
        throw new UnsupportedOperationException("Método não implementado");
    }

    // Buscas individuais
    Cursos buscarPorId(int id);
    Cursos buscarPorNome(String nome);

    // Buscas múltiplas
    List<Cursos> listarTodos();
    List<Cursos> listarAtivos();
    List<Cursos> listarInativos();
    List<Cursos> buscarPorTipo(String tipo);

    // Atualizações
    boolean atualizar(Cursos curso);
    boolean ativarCurso(int id);
    boolean desativarCurso(int id);

    // Exclusão
    boolean excluir(int id);
}
