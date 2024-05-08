import Dao.DisciplinaDao;
import Dao.ProfessorDao;
import domain.Disciplina;
import domain.Professor;
import org.junit.Assert;
import org.junit.Test;

public class ProfessorDaoTest {

    static DisciplinaDao disciplinaDao = new DisciplinaDao();
    static ProfessorDao professorDao = new ProfessorDao();

    @Test
    public void ProfessorDaoTeste(){

        Disciplina disc1 = criarDisciplina("A58","Português 2",35);
        Disciplina disc2 = criarDisciplina("A39","Português 1",25);
        Disciplina disc3 = criarDisciplina("A62","Inglês 2",35);

        Professor professor = new Professor();
        professor.setRa("5517B1");
        professor.setNome("Marcos");

        professor.addDisciplinas(disc1);
        professor.addDisciplinas(disc2);
        professor.addDisciplinas(disc3);

        Assert.assertNotNull(professorDao.cadastrar(professor));
    }

    private Disciplina criarDisciplina(String codigo, String nome, int cargaH) {

        Disciplina disciplina = new Disciplina();
        disciplina.setCodigo(codigo);
        disciplina.setNome(nome);
        disciplina.setCargaHoraria(cargaH);
        //return disciplina;
        return disciplinaDao.cadastrar(disciplina);
    }

}
