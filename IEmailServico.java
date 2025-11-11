package controller.InterfacesServico;

import java.util.List;
import model.Email;

public interface IEmailServico {
    void criar(Email email);
    void atualizar(Email email);
    void remover(int idEmail);
    Email buscarEmailPorEndereco(String enderecoEmail);
    List<Email> listarTodos();
    List<Email> listarTodaseEmails();
}
