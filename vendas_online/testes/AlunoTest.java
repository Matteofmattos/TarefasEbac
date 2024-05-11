import dao.AlunoDao;
import domain.Aluno;
import org.junit.Assert;
import org.junit.Test;

public class AlunoTest {

    AlunoDao alunoDao = new AlunoDao();

    public AlunoTest( ) {
        this.alunoDao = new AlunoDao();
    }

    @Test
    public void cadastramento(){
        Aluno aluno = new Aluno();

        aluno.setNome("João");
        aluno.setCodigo("555");

        Aluno alunoC = alunoDao.cadastrar(aluno);
        Assert.assertNotNull(alunoC);
        Assert.assertNotNull(alunoC.getId());
    }
}
