import Dao.MarcaDao;
import domain.Marca;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class MarcaTestes {

    MarcaDao marcaDao;

    public MarcaTestes() {
        marcaDao = new MarcaDao();
    }

    @Test
    public void consultarMarca(){

        Marca marcaC = marcaDao.buscarMarca(1L);

        System.out.println(marcaC.getNome());

        Assert.assertNotNull(marcaC);
    }

    @Test
    public void ConsultarMarcaPorCodigoVeiculo(){

        Marca marcaC = marcaDao.buscarMarcaPorCodigoVeiculo("21875");

        Assert.assertNotNull(marcaC);

        System.out.println(marcaC.getNome());
    }


    @Test
    public void ConsultarMarcas_porAcessorio(){

        List<Marca> marcas = marcaDao.buscarMarcaPorCodigoAcessorio(1L);

        for (Marca marca : marcas) {
            System.out.println(marca.getNome());
        }

        Assert.assertNotNull(marcas);
    }
}
