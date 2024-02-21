package domain;

public interface IClienteDao {

    void atualizar(Cliente cliente);
    Cliente consultar(Long cpf);
    Boolean cadastrar(Cliente cliente);
    void excluir(Long cpf);
}
