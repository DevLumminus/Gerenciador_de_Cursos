package dao.InterfacesDAO;

import model.Aluno;
import java.sql.SQLException;
import java.util.List;

public interface IAlunoDAO {

    Aluno inserir(Aluno aluno);
    Aluno inserirAlunoComEmail(Aluno aluno, String enderecoEmail) throws SQLException;

    Aluno buscarPorId(int id);
    Aluno buscarPorIdComEmail(int id);
    Aluno buscarPorCpf(String cpf);
    Aluno buscarPorCpfComEmail(String cpf);

    List<Aluno> listarTodos();
    List<Aluno> listarTodosComEmail();
    List<Aluno> buscarPorNome(String nome);

    boolean atualizar(Aluno aluno);
    boolean excluir(int id);

    String buscarEnderecoEmailPorAlunoId(int idAluno);
    boolean cpfExiste(String cpf);
}
