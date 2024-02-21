package Services;

import Dao.ClienteMapDAO;
import domain.Cliente;
import domain.IClienteDao;
import generic.IGenericDAO;

import java.util.Collection;

public class ClienteServices implements IGenericDAO<Cliente> {

    private final ClienteMapDAO clienteMapDAO = new ClienteMapDAO();

    public ClienteServices(ClienteMapDAO clienteDao) {

    }

    @Override
    public Boolean cadastrar(Cliente entity) {
        return clienteMapDAO.cadastrar(entity); //Ok;
    }

    @Override
    public void excluir(Long valor) {
        clienteMapDAO.excluir(valor);
    }

    @Override
    public void alterar(Cliente entity) {
        clienteMapDAO.alterar(entity);
    }

    @Override
    public Cliente consultar(Long valor) {
        return clienteMapDAO.consultar(valor);
    }

    @Override
    public Collection<Cliente> buscarTodos() {
        return null;
    }

}
