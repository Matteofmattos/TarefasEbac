import dao.ProdutoDAO;
import domain.Produto;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ProdutoTest {

    @Test
    public void testeCadastramento() throws Exception {
        ProdutoDAO produtoDAO = new ProdutoDAO();
        Produto produto = new Produto();
        produto.setId(8748L);
        produto.setNome("Shampoo");
        produto.setCodigo("73244");
        Integer countCad = produtoDAO.cadastrar(produto);
        assertEquals(1, (int) countCad);

        Produto produtoDB = produtoDAO.buscar("73244");
        assertNotNull(produtoDB);
        assertEquals(produtoDB.getCodigo(),produto.getCodigo());
        assertEquals(produtoDB.getNome(),produto.getNome());

        Integer countDel = produtoDAO.excluir(produtoDB);
        assertEquals(1,(int) countDel);
    }

    @Test
    public void testeBuscarTodos() throws Exception {

        ProdutoDAO produtoDAO = new ProdutoDAO();
        Produto produto1 = new Produto();
        produto1.setId(8748L);
        produto1.setNome("Shampoo");
        produto1.setCodigo("73244");
        Integer countCad = produtoDAO.cadastrar(produto1);
        assertEquals(1, (int) countCad);

        Produto produto2 = new Produto();
        produto2.setId(3625L);
        produto2.setNome("Sabonete");
        produto2.setCodigo("21187");
        Integer countCad2 = produtoDAO.cadastrar(produto2);
        assertEquals(1, (int) countCad2);

        List<Produto> produtos = produtoDAO.buscarTodos();
        assertNotNull(produtos);
        assertEquals(2,produtos.size());

        produtos.forEach(produto -> {
            System.out.println("Id: "+produto.getId() + ", Nome: "+ produto.getNome() + ", Código: "+produto.getCodigo());
        });

        for ( Produto produto: produtos){
            produtoDAO.excluir(produto);
        }

        produtos.clear();
        assertEquals(0,produtos.size());

        List<Produto> produtos1 = produtoDAO.buscarTodos();
        assertEquals(0,produtos1.size());
    }

    @Test
    public void testeAtualizarDados() throws Exception {
        ProdutoDAO produtoDAO = new ProdutoDAO();
        Produto produto1 = new Produto();
        produto1.setId(8748L);
        produto1.setNome("Shampoo");
        produto1.setCodigo("73244");
        Integer countCad = produtoDAO.cadastrar(produto1);
        assertEquals(1, (int) countCad);

        Produto produtoDB = produtoDAO.buscar("73244");
        assertNotNull(produtoDB);

        produtoDB.setCodigo("555");
        produtoDB.setNome("Geladeira");
        int countUpdate = produtoDAO.atualizar(produtoDB);
        assertEquals(1,countUpdate);

        Produto produtoDB1 = produtoDAO.buscar("73244");
        assertNull(produtoDB1);

        Produto produtoDB2 = produtoDAO.buscar("555");
        assertNotNull(produtoDB2);

        assertEquals(produtoDB2.getNome(),produtoDB.getNome());
        assertEquals(produtoDB2.getId(),produtoDB.getId());
        assertEquals(produtoDB2.getCodigo(),produtoDB.getCodigo());

        List<Produto> produtos = produtoDAO.buscarTodos();

        produtos.forEach(produto -> {
            System.out.println("Id: "+produto.getId() + ", Nome: "+ produto.getNome() + ", Código: "+produto.getCodigo());
        });


        for ( Produto produto: produtos){
            produtoDAO.excluir(produto);
        }

        produtos.clear();
        assertEquals(0,produtos.size());

        List<Produto> produtos1 = produtoDAO.buscarTodos();
        assertEquals(0,produtos1.size());

    }
}
