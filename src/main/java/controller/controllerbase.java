package controller;

import java.util.List;

/**
 * Controller base seguindo padrão GRASP Controller
 * Responsável por orquestrar operações entre a view e os DAOs
 */
public abstract class BaseController<T> {

    // Métodos comuns a todos os controllers
    public abstract T criar(T entidade);
    public abstract T buscarPorId(int id);
    public abstract List<T> listarTodos();
    public abstract boolean atualizar(T entidade);
    public abstract boolean excluir(int id);

    // Validações comuns
    protected boolean validarStringNaoVazia(String valor, String campo) {
        return valor != null && !valor.trim().isEmpty();
    }

    protected boolean validarNumeroPositivo(int numero, String campo) {
        return numero > 0;
    }
}