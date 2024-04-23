import DAO.ClientesDao;
import DOMAIN.Cliente;
import DOMAIN.Produto;
import EXCEPTIONS.DaoException;
import EXCEPTIONS.ElementTypeNotFound_Exception;
import EXCEPTIONS.MoreThanOneRegister_Exception;
import EXCEPTIONS.TableException;
import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;
import java.util.List;

public class ClienteDaoTestes {

    private final ClientesDao clientesDao;

    public ClienteDaoTestes() {
        this.clientesDao = new ClientesDao();
    }

    @Test
    public void CadastrarClienteTest() throws DaoException, MoreThanOneRegister_Exception, TableException, ElementTypeNotFound_Exception {

        Cliente cliente2 = new Cliente();

        String nome = JOptionPane.showInputDialog(null, "Informe o nome do cliente: ").trim();
        String endereco = JOptionPane.showInputDialog(null,"Informe o endereço: ");
        String cpf = JOptionPane.showInputDialog(null,"Informe o cpf:").trim();
        String numero = JOptionPane.showInputDialog(null, "Informe o número da residência: ");
        String estado = JOptionPane.showInputDialog(null, "Informe o estado (UF):").trim();
        String cidade = JOptionPane.showInputDialog(null, "Informe a cidade:");
        String telefone = JOptionPane.showInputDialog(null, "Informe o telefone:").trim();

        cliente2.setNome(nome);
        cliente2.setEnd(endereco);
        cliente2.setCpf(cpf);
        cliente2.setNumero(numero);
        cliente2.setEstado(estado);
        cliente2.setCidade(cidade);
        cliente2.setTel(telefone);

        Assert.assertTrue(this.clientesDao.cadastrar(cliente2));
    }

    @Test
    public void consultarClienteTest() throws MoreThanOneRegister_Exception, TableException, ElementTypeNotFound_Exception, DaoException {

        Assert.assertNotNull(clientesDao.consultar("09645206642"));

        System.out.println(clientesDao.consultar("09645206642"));
    }

    @Test(expected = DaoException.class)
    public void excluirCliente() throws DaoException {
        Assert.assertTrue(clientesDao.excluir("09645206642"));
    }

    @Test
    public void buscarTodosTest() throws DaoException {
        Assert.assertNotNull(clientesDao.buscarTodos());

        List<Cliente> clientes = clientesDao.buscarTodos();
        for ( Cliente cliente : clientes){
            System.out.println(cliente.toString());
        }
    }

    @Test
    public void alterarClienteTest() throws MoreThanOneRegister_Exception, TableException, ElementTypeNotFound_Exception, DaoException {
        Cliente clienteC = clientesDao.consultar("09645206642");
        clienteC.setNome("Matheus Felipe O. Mattos");
        clienteC.setCidade("Ituporanga");
        clienteC.setEstado("SC");
        clienteC.setNumero("84");

        Assert.assertTrue(clientesDao.alterar(clienteC));

        Assert.assertEquals("Matheus Felipe O. Mattos",clientesDao.consultar("09645206642").getNome());
    }

}
