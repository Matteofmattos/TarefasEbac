import Dao.AcessorioDao;
import domain.Acessorio;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class AcessorioTestes {

    AcessorioDao acessorioDao;

    public AcessorioTestes() {
        acessorioDao = new AcessorioDao();
    }

    @Test
    public void buscarAcessorioUnico(){

        Acessorio acessorio = acessorioDao.buscarAcessorio("B15");
        Assert.assertNotNull(acessorio);

        System.out.println(acessorio.getDescricao());
    }


    @Test
    public void buscarAcessorio_idCarro(){

        List<Acessorio> acessorios = acessorioDao.consultarPor_idCarro(2L);//id worng type

        Assert.assertNotNull(acessorios);

        for ( Acessorio acessorio: acessorios){
            System.out.println(acessorio.getDescricao());
        }
    }

    @Test
    public void buscarAcessorio_CodigoMarca(){

        List<Acessorio> acessorios = acessorioDao.consultarPor_CodigoMarca(1L);

        Assert.assertNotNull(acessorios);

        for ( Acessorio acessorio: acessorios){
            System.out.println(acessorio.getDescricao());
        }
    }
}
