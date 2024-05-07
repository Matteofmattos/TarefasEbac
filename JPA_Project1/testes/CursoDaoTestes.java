import Dao.CursoDao;
import domain.Curso;
import org.junit.Assert;
import org.junit.Test;

public class CursoDaoTestes {

    static CursoDao cursoDao = new CursoDao();

    @Test
    public void cadastrar(){

        Curso curso = new Curso();
        curso.setCodigo("A23");
        curso.setNome("Curso de Física.");
        curso.setDescricao("Curso de física básica 1");

        Curso cursoC = cursoDao.cadastrar(curso);

        Assert.assertNotNull(cursoC);
        Assert.assertNotNull(cursoC.getId());

        System.out.println("Id gerado: "+cursoC.getId());
    }
}
