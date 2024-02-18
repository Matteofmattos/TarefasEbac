package Services;

import Dao.ContractDao;

public class ContratotService {

    private ContractDao contractDao;

    public ContratotService(ContractDao contractDao){
        this.contractDao = contractDao;
    }

    public String saveContract() {
        return this.contractDao.saveContract();
    }
}
