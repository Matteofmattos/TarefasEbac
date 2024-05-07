import Dao.MatriculaDao;
import domain.Matricula;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;

public class MatriculaDaoTestes {

    static MatriculaDao matriculaDao = new MatriculaDao();

    @Test
    public void cadastrar(){

        Matricula matricula = new Matricula();

        matricula.setCodigo("73490");
        matricula.setValorMatricula(new BigDecimal("180.49"));
        matricula.setDataMaricula(Instant.now());
        matricula.setStatus("Concluída");

        Matricula matriculaC = matriculaDao.cadastrar(matricula);
        Assert.assertNotNull(matriculaC);
        Assert.assertNotNull(matriculaC.getId());

        System.out.println("Id de matrícula gerado: "+matriculaC.getId());

    }
}
