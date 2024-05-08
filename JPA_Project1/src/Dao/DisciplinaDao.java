package Dao;

import Interfaces.IDisciplinaDao;
import domain.Disciplina;
import domain.Matricula;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DisciplinaDao implements IDisciplinaDao {

    @Override
    public Disciplina cadastrar(Disciplina disciplina) {

        EntityManagerFactory entityManagerFac = Persistence.createEntityManagerFactory("ExemploJPA");
        EntityManager entityManager = entityManagerFac.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(disciplina);
        entityManager.getTransaction().commit();
        entityManager.close();

        return disciplina;
    }
}
