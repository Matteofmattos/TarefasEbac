import DAO.EstoqueDao;
import DOMAIN.Estoque;
import EXCEPTIONS.DaoException;
import EXCEPTIONS.ElementTypeNotFound_Exception;
import EXCEPTIONS.MoreThanOneRegister_Exception;
import EXCEPTIONS.TableException;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class EstoqueDaoTestes {

    public static final EstoqueDao estoqueDao = new EstoqueDao();

    @Test
    public void cadastroNovoEstoqueTest() throws MoreThanOneRegister_Exception, TableException, ElementTypeNotFound_Exception, DaoException {

        Estoque estoque = new Estoque();

        estoque.setNomeProduto("Shampoo");
        estoque.setDescricaoProduto("Shampoo Caseiro 200ml");
        estoque.setCodigoProduto("326");
        estoque.setValorProduto(BigDecimal.valueOf(13.49));
        estoque.setQuantidade(5);

        Assert.assertTrue(estoqueDao.cadastrar(estoque));
    }

    @Test
    public void consultarEstoque() throws MoreThanOneRegister_Exception, TableException, ElementTypeNotFound_Exception, DaoException {

        Estoque estoqueC = estoqueDao.consultar("32655");

        System.out.println(estoqueC.toString());
    }

    @Test(expected = MoreThanOneRegister_Exception.class)
    public void cadastroEstoqueExistenteTest() throws MoreThanOneRegister_Exception, TableException, ElementTypeNotFound_Exception, DaoException {

        Estoque estoque = new Estoque();

        estoque.setNomeProduto("Shampoo");
        estoque.setDescricaoProduto("Shampoo Caseiro 200ml");
        estoque.setCodigoProduto("326");
        estoque.setValorProduto(BigDecimal.valueOf(13.49));
        estoque.setQuantidade(5);

        estoqueDao.cadastrar(estoque);
    }

    @Test
    public void excluirProdutoEstoque() throws DaoException {

        Assert.assertTrue(estoqueDao.excluir("326"));

    }

    @Test
    public void RemoverTodosDados(){
        Assert.assertTrue(estoqueDao.removerTodosDados());
    }

    @Test
    public void ListarTodos() throws DaoException, MoreThanOneRegister_Exception, TableException, ElementTypeNotFound_Exception {

        Estoque prod1 = new Estoque();
        prod1.setNomeProduto("Arroz branco");
        prod1.setDescricaoProduto("Arroz agulha branco 5kg");
        prod1.setValorProduto(BigDecimal.valueOf(17.67));
        prod1.setCodigoProduto("63255");
        prod1.setQuantidade(2);

        Estoque prod2 = new Estoque();
        prod2.setNomeProduto("Feijão");
        prod2.setDescricaoProduto("Feijão preto tupiniquim 2kg");
        prod2.setValorProduto(BigDecimal.valueOf(11.70));
        prod2.setCodigoProduto("87484");
        prod2.setQuantidade(3);

        Estoque estoque = new Estoque();
        estoque.setNomeProduto("Shampoo");
        estoque.setDescricaoProduto("Shampoo Caseiro 200ml");
        estoque.setCodigoProduto("32655");
        estoque.setValorProduto(BigDecimal.valueOf(13.49));
        estoque.setQuantidade(5);

        Assert.assertTrue(estoqueDao.cadastrar(prod1));
        Assert.assertTrue(estoqueDao.cadastrar(prod2));
        Assert.assertTrue(estoqueDao.cadastrar(estoque));


        List<Estoque> estoques = estoqueDao.buscarTodos();
        for (Estoque estoq : estoques){
            System.out.println(estoq.toString());
        }
    }

    @Test
    public void adicionarQuantidadeProdutoTest() throws MoreThanOneRegister_Exception, TableException, ElementTypeNotFound_Exception, DaoException {

        Assert.assertTrue(estoqueDao.adicionarQuantidade("32655",5));
    }

    @Test
    public void SubtrairQuantidadeProdutoTest() throws MoreThanOneRegister_Exception, TableException, ElementTypeNotFound_Exception, DaoException, SQLException {
        Assert.assertTrue(estoqueDao.subtrairQuantidade("32655",5));
    }

    @Test
    public void SubtrairQuantidade_INSUFICIENTE_Test() throws MoreThanOneRegister_Exception, TableException, ElementTypeNotFound_Exception, DaoException, SQLException {
        Assert.assertTrue(estoqueDao.subtrairQuantidade("32655",6));
    }

}
