import DAO.ProdutosDao;
import DOMAIN.Produto;
import EXCEPTIONS.DaoException;
import EXCEPTIONS.ElementTypeNotFound_Exception;
import EXCEPTIONS.MoreThanOneRegister_Exception;
import EXCEPTIONS.TableException;
import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.List;

public class ProdutoDaoTestes {

    private final ProdutosDao produtosDao;

    public ProdutoDaoTestes() {
        this.produtosDao = new ProdutosDao();
    }

    @Test
    public void cadastrarProdutoTest() throws DaoException, MoreThanOneRegister_Exception, TableException, ElementTypeNotFound_Exception {

        Produto produto2 = new Produto();

        String nome = JOptionPane.showInputDialog(null, "Informe o nome do produto: ").trim();
        String descricao = JOptionPane.showInputDialog(null,"Informe a descrição do produto: ");
        String codigo = JOptionPane.showInputDialog(null,"Informe o código do produto: ").trim();
        String preco = JOptionPane.showInputDialog(null, "Informe o preço do produto: ");
        BigDecimal precoB = BigDecimal.valueOf(Long.parseLong(preco));

        produto2.setNome(nome);
        produto2.setDescricao(descricao);
        produto2.setCodigo(codigo);
        produto2.setValor(precoB);

        Assert.assertTrue(this.produtosDao.cadastrar(produto2));
    }

    @Test
    public void consultarProdutoTest() throws MoreThanOneRegister_Exception, TableException, ElementTypeNotFound_Exception, DaoException {

        Assert.assertNotNull(produtosDao.consultar("326"));
        Assert.assertNotNull(produtosDao.consultar("63255"));

        System.out.println(produtosDao.consultar("326"));
    }

    @Test(expected = DaoException.class)
    public void excluirProduto() throws DaoException {
        Assert.assertTrue(produtosDao.excluir("326"));
    }

    @Test
    public void buscarTodosTest() throws DaoException {
        Assert.assertNotNull(produtosDao.buscarTodos());

        List<Produto> produtos = produtosDao.buscarTodos();
        for ( Produto prod : produtos){
            System.out.println(prod.toString());
        }
    }

    @Test
    public void alterarProdutoTest() throws MoreThanOneRegister_Exception, TableException, ElementTypeNotFound_Exception, DaoException {
        Produto produtoC = produtosDao.consultar("326");

        produtoC.setNome("Shampoo Masculino Clear");
        produtoC.setDescricao("Shampoo anticaspa clear Men");

        produtosDao.alterar(produtoC);

        Assert.assertEquals("Shampoo anticaspa clear Men",produtoC.getDescricao());
    }

}
