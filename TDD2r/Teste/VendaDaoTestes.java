import DAO.ClientesDao;
import DAO.ProdutoQuantidadeDao;
import DAO.ProdutosDao;
import DAO.VendaDao;
import DOMAIN.Cliente;
import DOMAIN.Produto;
import DOMAIN.Venda;
import EXCEPTIONS.DaoException;
import EXCEPTIONS.ElementTypeNotFound_Exception;
import EXCEPTIONS.MoreThanOneRegister_Exception;
import EXCEPTIONS.TableException;
import org.junit.Assert;
import org.junit.Test;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class VendaDaoTestes {

    private final VendaDao vendaDao;
    private final ClientesDao clientesDao;
    private final ProdutosDao produtosDao;
    private static Venda vendaC;

    public VendaDaoTestes() {

        this.vendaDao = new VendaDao();
        this.clientesDao = new ClientesDao();
        this.produtosDao = new ProdutosDao();
    }


    public Venda criarVenda(String codigoVenda,Cliente cliente,Produto prod,Integer quantidade){

        Venda venda = new Venda();

        venda.setCodigo(codigoVenda);
        venda.setDataVenda(Instant.now());
        venda.setCliente(cliente);
        venda.setStatus(Venda.Status.INICIADA);
        venda.adicionarProduto(prod,quantidade);

        return venda;
    }


    private void excluirClientes() throws DaoException {
        List<Cliente> clientes = clientesDao.buscarTodos();
        for (Cliente cliente : clientes){
            clientesDao.excluir(cliente.getCpf());
        }
    }

    private void excluirProdutos() throws DaoException {
        List<Produto> produtos = produtosDao.buscarTodos();
        for (Produto produto : produtos){
            produtosDao.excluir(produto.getCodigo());
        }
    }

    private void excluirVendas() {
        List<Venda> vendas = vendaDao.buscarTodos();
        for (Venda venda : vendas){
            VendaDao.limparDadosDataBase(venda.getCodigo());
        }
    }


    private Produto criarProduto() throws DaoException, MoreThanOneRegister_Exception, TableException, ElementTypeNotFound_Exception {

        Produto produto1 = new Produto();

        produto1.setNome("Shampoo");
        produto1.setDescricao("Shampoo Caseiro 200ml");
        produto1.setCodigo("326");
        produto1.setValor(BigDecimal.valueOf(13.49));

        return produto1;
    }


    private Cliente criarCliente() throws DaoException, MoreThanOneRegister_Exception, TableException, ElementTypeNotFound_Exception {

        Cliente cliente1 = new Cliente();

        cliente1.setNome("Matheus Mattos");
        cliente1.setEnd("Santos Dummont");
        cliente1.setCpf("09645206642");
        cliente1.setNumero("279");
        cliente1.setEstado("MG");
        cliente1.setCidade("Campo Belo");
        cliente1.setTel("35999628040");

        return cliente1;
    }

    @Test
    public void CadastramentosClienteProduto() throws MoreThanOneRegister_Exception, TableException, ElementTypeNotFound_Exception, DaoException {

        Produto produto1 = criarProduto();
        Cliente cliente1 = criarCliente();

        Integer quantidadeProduto1 = 4;
        String codigoVenda1 = "C23";

        assertTrue(this.produtosDao.cadastrar(produto1));
        assertTrue(this.clientesDao.cadastrar(cliente1));
    }

    @Test
    public void ConsultaClienteProduto() throws MoreThanOneRegister_Exception, TableException, ElementTypeNotFound_Exception, DaoException {

        Produto produtoC = produtosDao.consultar("326");
        Cliente clienteC = clientesDao.consultar("09645206642");

        Assert.assertNotNull(produtoC);
        Assert.assertNotNull(clienteC);

    }

    @Test
    public void cadastramentoVendaTest() throws MoreThanOneRegister_Exception, TableException, ElementTypeNotFound_Exception, DaoException {

        Produto produtoC = produtosDao.consultar("326");
        Cliente clienteC = clientesDao.consultar("09645206642");

        Venda venda = criarVenda("C23", clienteC, produtoC, 4);
        Assert.assertTrue(vendaDao.cadastrar(venda));
    }

    @Test
    public void consultaVenda() {
        Assert.assertNotNull(vendaDao.consultar("C23"));
    }

    @Test
    public void adicionarMaisProdutosVenda() throws DaoException, MoreThanOneRegister_Exception, TableException, ElementTypeNotFound_Exception, SQLException, SQLException {

        Produto prod1 = new Produto();
        prod1.setNome("Arroz branco");
        prod1.setDescricao("Arroz agulha branco 5kg");
        prod1.setValor(BigDecimal.valueOf(17.67));
        prod1.setCodigo("63255");

        Produto prod2 = new Produto();
        prod2.setNome("Feijão");
        prod2.setDescricao("Feijão preto tupiniquim 2kg");
        prod2.setValor(BigDecimal.valueOf(11.70));
        prod2.setCodigo("87484");

        this.produtosDao.cadastrar(prod1);
        this.produtosDao.cadastrar(prod2);

        Produto produtoC1 = produtosDao.consultar("63255");
        Produto produtoC2 = produtosDao.consultar("87484");

        vendaC = vendaDao.consultar("C23");

        vendaC.adicionarProduto(produtoC1, 2);
        vendaC.adicionarProduto(produtoC2,5);

        ProdutoQuantidadeDao.atualizarDataBase(vendaC);
        Assert.assertTrue(vendaDao.atualizarVenda(vendaC));
    }

    @Test
    public void adicionarProdutoExisteTest() throws SQLException, DaoException, MoreThanOneRegister_Exception, TableException, ElementTypeNotFound_Exception {

        Produto produtoC = produtosDao.consultar("326");
        vendaC = vendaDao.consultar("C23");

        vendaC.adicionarProduto(produtoC,5); //  Produto já cadastrado!

        ProdutoQuantidadeDao.atualizarDataBase(vendaC);
        Assert.assertTrue(vendaDao.atualizarVenda(vendaC));
    }

    @Test
    public void consultarTodosVendaTest() {
        List<Venda> vendas = vendaDao.buscarTodos();
        for (Venda venda: vendas){
            System.out.println(venda.toString());
        }
    }

    @Test
    public void consultarTodosProdutosTest() throws DaoException {
        List<Produto> produtos = produtosDao.buscarTodos();
        for (Produto produto: produtos){
            System.out.println(produto.toString());
        }
    }

    @Test
    public void consultarTodosClienteTest() throws DaoException {
        List<Cliente> clientes = clientesDao.buscarTodos();
        for (Cliente cliente: clientes){
            System.out.println(cliente.toString());
        }
    }

    @Test
    public void consultaUnicaVendaTest(){
        Venda vendac = vendaDao.consultar("C23");
        Assert.assertNotNull(vendac);
        System.out.println(vendac.toString());
    }

    @Test
    public void consultaUnicaClienteTest() throws MoreThanOneRegister_Exception, TableException, ElementTypeNotFound_Exception, DaoException {
        Cliente clienteC = clientesDao.consultar("09645206642");
        Assert.assertNotNull(clienteC);
        System.out.println(clienteC.toString());
    }

    @Test
    public void consultaUnicaProdutoTest() throws MoreThanOneRegister_Exception, TableException, ElementTypeNotFound_Exception, DaoException {
        Produto produtoC = produtosDao.consultar("326");
        Assert.assertNotNull(produtoC);
        System.out.println(produtoC.toString());
    }

    @Test
    public void excluirProdutoTest() throws SQLException, DaoException {

        vendaC = vendaDao.consultar("C23");

        vendaC.subtrairProduto("87484",2);
        vendaDao.atualizarVenda(vendaC);
        ProdutoQuantidadeDao.atualizarDataBase(vendaC);
    }

    @Test
    public void finalizarVenda(){

        vendaC = vendaDao.consultar("C23");
        Assert.assertTrue(vendaDao.finalizarVenda(vendaC));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void cadastrarProdutoVendaFinalizada() throws MoreThanOneRegister_Exception, TableException, ElementTypeNotFound_Exception, DaoException {

        Produto prodC = produtosDao.consultar("87484");
        vendaC = vendaDao.consultar("C23");
        vendaC.adicionarProduto(prodC,10);
    }

    @Test
    public void removerTodosProdutosVenda() {
        vendaC = vendaDao.consultar("C23");
        vendaC.removerTodos();
        Assert.assertTrue(ProdutoQuantidadeDao.removerTodosDados(vendaC));
        Assert.assertTrue(vendaDao.removerTodosDados(vendaC));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void removerTodosProdutosVendaFinalizada(){
        vendaC = vendaDao.consultar("C23");
        vendaC.removerTodos();
    }

    @Test(expected = DaoException.class)
    public void SalvarVendaCodigoRepetido() throws DaoException {
        vendaC = vendaDao.consultar("C23");
        vendaDao.cadastrar(vendaC);
    }

    @Test
    public void CancelarVenda(){
        vendaC = vendaDao.consultar("C23");
        Assert.assertTrue(vendaDao.cancelarVenda(vendaC));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void removerTodosProdutosVendaCancelada(){
        vendaC = vendaDao.consultar("C23");
        vendaC.removerTodos();
    }




}
