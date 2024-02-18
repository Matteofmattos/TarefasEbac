package Services;

import Dao.*;

public class CientService {

    private IClientDao clientDAO;

    public CientService(IClientDao clientDAO) {
        this.clientDAO = clientDAO;
    }

    public String save(){
        clientDAO.save();

        return "Dados do cliente salvos com êxito.";
    }

    public String alter() {
        return "Dados do cliente alterados com êxito.";
    }

    public String remove() {
        return "Registro removido com sucesso.";
    }

    public String consultar() {
        return "Registro localizado com sucesso.";
    }

}
