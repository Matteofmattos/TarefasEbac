package GENERICS;

import DOMAIN.Estoque;

import java.sql.Connection;

public interface IEstoqueDao extends IGenericDao<Estoque>{

    void atualizarDados(Connection connection, Estoque estoque);
}
