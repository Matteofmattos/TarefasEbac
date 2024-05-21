import Dao.CarroDao;
import domain.Acessorio;
import domain.Carro;
import domain.Marca;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class CarroTestes {

    static CarroDao carroDao;

    public CarroTestes() {
        carroDao = new CarroDao();
    }

    @Test
    public void buscarCarro(){

        Carro carroC = carroDao.buscarPorCodigo(2); //id worng type

        Assert.assertNotNull(carroC);
        System.out.println(carroC.getCodigo());
    }

    @Test
    public void buscarCarro_PorCodigoAcessorio(){

        List<Carro> carros = carroDao.buscarCarro_PorCodigoAcessorio(1);//id worng type

        Assert.assertNotNull(carros);

        for ( Carro carro: carros){
            System.out.println(carro.getAno());
        }
    }

}
