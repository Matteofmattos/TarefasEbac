
import Dao.AcessorioDao;
import Dao.CarroDao;
import Dao.MarcaDao;
import domain.Acessorio;
import domain.Carro;
import domain.Marca;
import org.junit.Assert;
import org.junit.Test;

public class testeCadastramentos {

    AcessorioDao acessorioDao;
    CarroDao carroDao;
    MarcaDao marcaDao;

    public testeCadastramentos() {
         acessorioDao = new AcessorioDao();
         carroDao = new CarroDao();
         marcaDao = new MarcaDao();
    }

    @Test
    public void cadastramento(){

        Carro carro1 = new Carro();
        carro1.setAno("1997");
        carro1.setModelo("Modelo1");
        carro1.setCodigo("57885");
        carro1.setCor("Preto");

        Carro carro2 = new Carro();
        carro2.setAno("1988");
        carro2.setModelo("Modelo2");
        carro2.setCodigo("21875");
        carro2.setCor("Prata");

        Marca marca = new Marca();
        marca.setNome("BMW");
        marca.setPaisOrgem("Alemanha");
        marca.addCarros(carro2);
        marca.addCarros(carro1);

        carro1.setMarca(marca);
        carro2.setMarca(marca);

        Acessorio acessorio = new Acessorio();
        acessorio.setCodigo("A1");
        acessorio.setCor("Preto");
        acessorio.setDescricao("Para-choque para BMW");

        acessorio.addCarro(carro1);
        acessorio.addCarro(carro2);
        acessorio.addMarca(marca);

        carro1.addAcessorios(acessorio);
        carro2.addAcessorios(acessorio);

        marca.addAcessorios(acessorio);

        Assert.assertNotNull(acessorioDao.cadastrar(acessorio));

    }

    @Test
    public void cadastramentoNovosVeiculos(){

        Carro carro3 = new Carro();
        carro3.setAno("2005");
        carro3.setModelo("Modelo3");
        carro3.setCodigo("95741");
        carro3.setCor("Branco");

        Marca marca2 = new Marca();
        marca2.setNome("HONDA");
        marca2.setPaisOrgem("Japão");
        marca2.addCarros(carro3);

        carro3.setMarca(marca2);
        marca2.addCarros(carro3);

        Acessorio acessorio2 = new Acessorio();
        acessorio2.setCodigo("A25");
        acessorio2.setCor("Branco");
        acessorio2.setDescricao("Para-choque para Honda-Civic");

        Acessorio acessorio3 = new Acessorio();
        acessorio3.setCodigo("B15");
        acessorio3.setCor("Preto");
        acessorio3.setDescricao("Retrovisor para Honda-civic");


        marca2.addAcessorios(acessorio2);
        marca2.addAcessorios(acessorio3);
        marca2.addCarros(carro3);

        carro3.addAcessorios(acessorio3);
        carro3.addAcessorios(acessorio2);

        acessorio3.addMarca(marca2);
        acessorio2.addMarca(marca2);

        acessorio2.addCarro(carro3);
        acessorio2.addCarro(carro3);

        acessorio3.addCarro(carro3);

        Assert.assertNotNull(acessorioDao.cadastrar(acessorio2));
    }

}
