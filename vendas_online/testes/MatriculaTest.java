import java.time.Instant;

import dao.AlunoDao;
import dao.ComputadorDao;
import dao.CursoDao;
import dao.MatriculaDao;
import domain.Aluno;
import domain.Computador;
import domain.Curso;
import domain.Matricula;
import interfaces.IAlunoDao;
import interfaces.IComputadorDao;
import interfaces.ICursoDao;
import interfaces.IMatriculaDao;
import org.junit.Assert;
import org.junit.Test;

public class MatriculaTest {

    private IMatriculaDao matriculaDao;
    private ICursoDao cursoDao;
    private IAlunoDao alunoDao;
    private IComputadorDao computadorDao;

    public MatriculaTest() {
        matriculaDao = new MatriculaDao();
        cursoDao = new CursoDao();
        alunoDao = new AlunoDao();
        computadorDao = new ComputadorDao();
    }

    @Test
    public void cadastrar() {

        Matricula mat = new Matricula();
        mat.setCodigo("A1");
        mat.setDataMatricula(Instant.now());
        mat.setStatus("ATIVA");
        mat.setValor(200d);
        mat.setCurso(criarCurso("A1"));
        mat.setAluno(criarAluno("555"));

        mat = matriculaDao.cadastrar(mat);

        Assert.assertNotNull(mat);
        Assert.assertNotNull(mat.getId());

    }

    private Computador criarComputador(String codigo) {

        Computador computador = new Computador();
        computador.setCodigo(codigo);
        computador.setDescricao("Descricação pc1");
        computador.setModelo("i5");
        return computador;
        //return computadorDao.cadastrar(computador);
    }

    private Curso criarCurso(String codigo){

        Curso curso = new Curso();
        curso.setCodigo(codigo);
        curso.setDescricao("CURSO TESTE");
        curso.setNome("Curso de Java Backend");

        return cursoDao.cadastrar(curso);
    }

    private Aluno criarAluno(String codigo){

        Aluno aluno = new Aluno();
        aluno.setCodigo(codigo);
        aluno.setNome("João");
        aluno.add(criarComputador("6646B"));
        aluno.add(criarComputador("4146C"));

        computadorDao.cadastrar(criarComputador("6646B"));
        computadorDao.cadastrar(criarComputador("4146C"));
        
        return alunoDao.cadastrar(aluno);
    }
}
