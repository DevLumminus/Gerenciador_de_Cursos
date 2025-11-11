package controller.InterfacesServico;

import java.util.List;
import model.Aluno;

public interface IAlunoServico {
   Aluno matricularAlunoComEmail(String nome, String telefone, String cpf, String enderecoEmail);
    void criar(Aluno aluno);
    void atualizar(Aluno aluno);
    void remover(int idAluno);
    Aluno buscarPorId(int idAluno);
    Aluno buscarAlunoPorCpf(String cpf);
    List<Aluno> listarTodos();
    boolean validarAlunoParaMatricula(int idAluno);
    String obterEmailAluno(int idAluno);
    List<Aluno> listarTodasAluno();
}
