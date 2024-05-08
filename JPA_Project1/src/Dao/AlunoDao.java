package Dao;

import Interfaces.IAlunoDao;
import domain.Aluno;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AlunoDao implements IAlunoDao {

    @Override
    public Aluno cadastrar(Aluno aluno) {

        EntityManagerFactory entityManagerFac = Persistence.createEntityManagerFactory("ExemploJPA");
        EntityManager entityManager = entityManagerFac.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(aluno);
        entityManager.getTransaction().commit();
        entityManager.close();

        return aluno;
    }
}
