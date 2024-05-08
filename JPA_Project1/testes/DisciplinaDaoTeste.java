import Dao.DisciplinaDao;
import Dao.ProfessorDao;
import domain.Disciplina;
import domain.Professor;
import org.junit.Assert;
import org.junit.Test;

public class DisciplinaDaoTeste {


    static DisciplinaDao disciplinaDao = new DisciplinaDao();
    static ProfessorDao professorDao = new ProfessorDao();

    @Test
    public void DisciplinaDaoTeste(){

        Professor prof = criarProfessor("A51B58","Júlio César");

        Disciplina disciplina = new Disciplina();
        disciplina.setCodigo("A58");
        disciplina.setNome("Matemática 1");
        disciplina.setCargaHoraria(44);

        Assert.assertNotNull(disciplinaDao.cadastrar(disciplina));

    }

    private Professor criarProfessor(String ra,String nome) {
        Professor professor = new Professor();

        professor.setRa(ra);
        professor.setNome(nome);

        return professorDao.cadastrar(professor);
    }
}
