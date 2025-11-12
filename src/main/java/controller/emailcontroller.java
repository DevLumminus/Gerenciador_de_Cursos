package controller;

import dao.InterfacesDAO.IEmailDAO;
import dao.EmailDAO;
import model.Email;
import java.util.List;

public class emailcontroller extends controllerbase<Email> {

    private final IEmailDAO emailDAO;

    // Construtor recebe DAO diretamente
    public emailcontroller(IEmailDAO emailDAO) {
        if (emailDAO == null) {
            throw new IllegalArgumentException("DAO de email não pode ser nulo");
        }
        this.emailDAO = emailDAO;
    }

    @Override
    public Email criar(Email email) {
        validarEmail(email);
        return emailDAO.inserir(email);
    }

    @Override
    public Email buscarPorId(int id) {
        validarId(id);
        return emailDAO.buscarPorId(id);
    }

    public Email buscarEmailPorEndereco(String enderecoEmail) {
        validarEmail(enderecoEmail);
        return emailDAO.buscarPorEndereco(enderecoEmail);
    }

    @Override
    public List<Email> listarTodos() {
        return emailDAO.listarTodos();
    }

    public List<Email> listarTodasporEmails() {
        return emailDAO.listarTodos();
    }

    @Override
    public boolean atualizar(Email email) {
        validarEmail(email);
        return emailDAO.atualizar(email);
    }

    @Override
    public boolean excluir(int id) {
        validarId(id);
        return emailDAO.excluir(id);
    }

    // VALIDAÇÕES ESPECÍFICAS
    private void validarEmail(Email email) {
        if (email == null) {
            throw new IllegalArgumentException("Email não pode ser nulo");
        }
        validarEmail(email.getEmail());
    }

    private void validarEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Email inválido: " + email);
        }
    }

    private void validarId(int id) {
        validacaoNumeroPositivo(id, "ID Email");
    }
}
