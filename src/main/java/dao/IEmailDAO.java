package dao;

import model.Email;
import java.sql.Connection;
import java.sql.SQLException;

public interface IEmailDAO {
    Email inserir(Email email);
    Email buscarPorId(int id);
    Email buscarPorEndereco(String enderecoEmail);
    Email inserirComTransacao(Connection conn, Email email) throws SQLException;
    boolean atualizar(Email email);
    boolean excluir(int id);
}