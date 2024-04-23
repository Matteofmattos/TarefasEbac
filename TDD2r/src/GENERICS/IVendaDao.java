package GENERICS;

import DOMAIN.Venda;

public interface IVendaDao extends IGenericDao<Venda> {
    boolean finalizarVenda(Venda venda);
    boolean cancelarVenda(Venda venda);
}
