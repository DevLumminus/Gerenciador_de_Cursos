package controller;

import java.util.List;

public abstract class controllerbase<T> {
    // Métodos comuns a todos os controllers
    public abstract T criar(T entidade);
    public abstract T buscarPorId(int id);
    public abstract List<T> listarTodos();
    public abstract boolean atualizar(T entidade);
    public abstract boolean excluir(int id);

    // Validações comuns otimizadas
    protected boolean validacaoStringNaoVazia(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " não pode ser vazio");
        }
        return true;
    }

    protected boolean validacaoNumeroPositivo(int numero, String campo) {
        if (numero <= 0) {
            throw new IllegalArgumentException(campo + " deve ser positivo");
        }
        return true;
    }
}