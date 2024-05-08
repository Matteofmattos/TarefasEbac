import Dao.AlunoDao;
import Dao.CursoDao;
import Dao.MatriculaDao;
import domain.Aluno;
import domain.Curso;
import domain.Matricula;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;

public class MatriculaDaoTestes {

    static CursoDao cursoDao = new CursoDao();

    static AlunoDao alunoDao = new AlunoDao();

    static MatriculaDao matriculaDao = new MatriculaDao();

    @Test
    public void cadastrarMatricula(){

        Aluno aluno = criarAluno();
        Curso curso = criarCurso();

        Matricula matricula = new Matricula();

        matricula.setCodigo("73490");
        matricula.setValorMatricula(new BigDecimal("180.49"));
        matricula.setDataMaricula(Instant.now());
        matricula.setStatus("Concluída");
        matricula.setCurso(curso);
        matricula.setAluno(aluno);

        Matricula matriculaC = matriculaDao.cadastrar(matricula);

        Assert.assertNotNull(matriculaC);

        Assert.assertNotNull(matriculaC.getId());
    }

    private Curso criarCurso() {
        Curso curso = new Curso();
        curso.setCodigo("A23");
        curso.setNome("Curso de Física.");
        curso.setDescricao("Curso de física básica 1");
        return cursoDao.cadastrar(curso);
    }

    private Aluno criarAluno() {

        Aluno aluno = new Aluno();
        aluno.setNome("Fernando Augusto");
        aluno.setCpf("1587151");
        return alunoDao.cadastrar(aluno);
    }
}
