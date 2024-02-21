package Dao;

import domain.Produto;
import generic.IGenericDAO;

public interface IProdutoDAO extends IGenericDAO<Produto> {

    Class<Produto> getTipoClasse();

    void atualiarDados(Produto entity, Produto entityCadastrado);
}
