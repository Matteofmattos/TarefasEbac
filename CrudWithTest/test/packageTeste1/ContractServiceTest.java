package packageTeste1;
import Dao.ContractDao;
import Services.ContratotService;
import org.junit.Assert;
import org.junit.Test;

public class ContractServiceTest {

    @Test
    public void testeContract(){
        ContractDao contractsDao = new ContractDao();
        ContratotService contratotService = new ContratotService(contractsDao);

        Assert.assertEquals("Sucess.",contratotService.saveContract());

    }

    @Test(expected = UnsupportedOperationException.class)
    public void testeContractWithErrorDatabase(){

        ContractDao contractsDao = new ContractDao();
        ContratotService contratotService = new ContratotService(contractsDao);

        Assert.assertEquals("Sucess.",contratotService.saveContract());
    }
}
