import dao.ClienteDAO;
import domain.Cliente;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ClienteTest {

    @Test
    public void testeCadastramento() throws Exception {
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = new Cliente();
        cliente.setId(8748L);
        cliente.setNome("Matheus Mattos");
        cliente.setCodigo("73244");
        Integer countCad = clienteDAO.cadastrar(cliente);
        assertEquals(1, (int) countCad);

        Cliente clienteDB = clienteDAO.buscar("73244");
        assertNotNull(clienteDB);
        assertEquals(clienteDB.getCodigo(),cliente.getCodigo());
        assertEquals(clienteDB.getNome(),cliente.getNome());

        Integer countDel = clienteDAO.excluir(clienteDB);
        assertEquals(1,(int) countDel);
    }

    @Test
    public void testeBuscarTodos() throws Exception {

        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente1 = new Cliente();
        cliente1.setId(8748L);
        cliente1.setNome("Matheus Mattos");
        cliente1.setCodigo("73244");
        Integer countCad = clienteDAO.cadastrar(cliente1);
        assertEquals(1, (int) countCad);

        Cliente cliente2 = new Cliente();
        cliente2.setId(3625L);
        cliente2.setNome("Felipe Braga");
        cliente2.setCodigo("21187");
        Integer countCad2 = clienteDAO.cadastrar(cliente2);
        assertEquals(1, (int) countCad2);

        List<Cliente> clientes = clienteDAO.buscarTodos();
        assertNotNull(clientes);
        assertEquals(2,clientes.size());

        clientes.forEach(cliente -> {
            System.out.println("Id: "+cliente.getId() + ", Nome: "+ cliente.getNome() + ", Código: "+cliente.getCodigo());
        });

        for ( Cliente cliente: clientes){
            clienteDAO.excluir(cliente);
        }

        clientes.clear();
        assertEquals(0,clientes.size());

        List<Cliente> clientes1 = clienteDAO.buscarTodos();
        assertEquals(0,clientes1.size());

    }

    @Test
    public void testeAtualizarDados() throws Exception {
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente1 = new Cliente();
        cliente1.setId(8748L);
        cliente1.setNome("Matheus Mattos");
        cliente1.setCodigo("73244");
        Integer countCad = clienteDAO.cadastrar(cliente1);
        assertEquals(1, (int) countCad);

        Cliente clienteDB = clienteDAO.buscar("73244");
        assertNotNull(clienteDB);

        clienteDB.setCodigo("555");
        clienteDB.setNome("Diego Alcântara");
        int countUpdate = clienteDAO.atualizar(clienteDB);
        assertEquals(1,countUpdate);

        Cliente clienteDB1 = clienteDAO.buscar("73244");
        assertNull(clienteDB1);

        Cliente clienteDB2 = clienteDAO.buscar("555");
        assertNotNull(clienteDB2);

        assertEquals(clienteDB2.getNome(),clienteDB.getNome());
        assertEquals(clienteDB2.getId(),clienteDB.getId());
        assertEquals(clienteDB2.getCodigo(),clienteDB.getCodigo());

        List<Cliente> clientes = clienteDAO.buscarTodos();

        clientes.forEach(cliente -> {
            System.out.println("Id: "+cliente.getId() + ", Nome: "+ cliente.getNome() + ", Código: "+cliente.getCodigo());
        });


        for ( Cliente cliente: clientes){
            clienteDAO.excluir(cliente);
        }

        clientes.clear();
        assertEquals(0,clientes.size());

        List<Cliente> clientes1 = clienteDAO.buscarTodos();
        assertEquals(0,clientes1.size());
    }

}
