package packageTeste1;

import Dao.ClientDAO;
import Dao.ClientDaoMock;
import Dao.IClientDao;
import Services.CientService;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CientServiceTest {

    @Test()
    public void save() {

        IClientDao clientDao = new ClientDAO();

        CientService cientService = new CientService(clientDao);

        Assert.assertEquals("Sucess.",cientService.save());
    }


    @Test
    public void saveMockTest() {

        IClientDao clientDaoMock = new ClientDaoMock();

        CientService cientService = new CientService(clientDaoMock);

        Assert.assertEquals("Dados do cliente salvos com êxito.",cientService.save());
    }

    @Test
    public void alterarMockTest() {

        IClientDao clientDaoMock = new ClientDaoMock();

        CientService cientService = new CientService(clientDaoMock);

        Assert.assertEquals("Dados do cliente alterados com êxito.",cientService.alter());
    }

    @Test
    public void ExcluirMockTest() {

        IClientDao clientDaoMock = new ClientDaoMock();

        CientService cientService = new CientService(clientDaoMock);

        Assert.assertEquals("Registro removido com sucesso.",cientService.remove());

    }

    @Test
    public void consultarMockTest() {

        IClientDao clientDaoMock = new ClientDaoMock();

        CientService cientService = new CientService(clientDaoMock);

        Assert.assertEquals("Registro localizado com sucesso.",cientService.consultar());

    }

}