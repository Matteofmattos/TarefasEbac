package Dao;

import domain.Cliente;
import domain.IClienteDao;

public class ClienteDaoMock implements IClienteDao {

    @Override
    public void atualizar(Cliente cliente) {
        cliente.setNome("Felipe Mattos");
    }

    @Override
    public Cliente consultar(Long cpf) {
        Cliente clienteMock = new Cliente();
        clienteMock.setCpf(cpf);
        return clienteMock;
    }

    @Override
    public Boolean cadastrar(Cliente cliente) {
        return true;
    }

    @Override
    public void excluir(Long cpf) {

    }
}
