import Dao.ClienteDaoMock;
import Dao.ClienteMapDAO;
import Services.ClienteServices;
import domain.Cliente;
import domain.IClienteDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ClienteServiceTest {

    private ClienteMapDAO clienteDao;
    private ClienteServices clienteServices;
    private Cliente cliente;

    public ClienteServiceTest() {
        this.clienteDao = new ClienteMapDAO();
        this.clienteServices = new ClienteServices(clienteDao);
    }

    @Before
    public void pesquisarCliente(){
        cliente = new Cliente();
        cliente.setNome("Matheus Felipe OLiveira Mattos. ");
        cliente.setCpf(Long.valueOf("0964506642"));
        cliente.setCidade("Ituporanga");
        cliente.setEnd("Rua Vereador Paulo Felber.");
        cliente.setEstado("SC");
        cliente.setTel(47999628040L);
        cliente.setNumero(205);
    }

    @Test
    public void TesteClienteService(){

        Assert.assertTrue(clienteServices.cadastrar(cliente));

        Assert.assertNotNull(clienteServices.consultar(cliente.getCpf()));

        clienteServices.alterar(cliente);

        Assert.assertEquals("Matheus Felipe OLiveira Mattos. ",cliente.getNome());

        clienteServices.excluir(cliente.getCpf());
    }
}
