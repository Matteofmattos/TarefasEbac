package Dao;

import Interfaces.IMatriculaDao;
import domain.Matricula;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MatriculaDao implements IMatriculaDao {

    @Override
    public Matricula cadastrar(Matricula matricula) {

        EntityManagerFactory entityManagerFac = Persistence.createEntityManagerFactory("ExemploJPA");
        EntityManager entityManager = entityManagerFac.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(matricula);
        entityManager.getTransaction().commit();
        entityManager.close();

        return matricula;
    }
}
