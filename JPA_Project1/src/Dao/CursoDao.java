package Dao;

import Interfaces.ICursoDao;
import domain.Curso;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CursoDao implements ICursoDao {

    @Override
    public Curso cadastrar(Curso curso) {

        EntityManagerFactory entityManagerFac = Persistence.createEntityManagerFactory("ExemploJPA");
        EntityManager entityManager = entityManagerFac.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(curso);
        entityManager.getTransaction().commit();
        entityManager.close();

        return curso;
    }
}
