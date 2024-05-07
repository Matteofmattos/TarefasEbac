import Dao.ProfessorDao;
import domain.Professor;
import org.junit.Assert;
import org.junit.Test;

public class ProfessorDaoTestes {

    static ProfessorDao professorDao = new ProfessorDao();

    @Test
    public void cadastrar(){

        Professor prof = new Professor();

        prof.setRa("15766");
        prof.setNome("Carlos Augusto");
        prof.setDisciplina("Geografia");

        Professor professorC = professorDao.cadastrar(prof);

        Assert.assertNotNull(professorC);
        Assert.assertNotNull(professorC.getId());

        System.out.println("Id do professor: "+professorC.getId());

    }
}
