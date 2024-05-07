package Dao;

import Interfaces.IProfessor;
import domain.Professor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ProfessorDao implements IProfessor {
    @Override
    public Professor cadastrar(Professor professor) {

        EntityManagerFactory entityManagerFac = Persistence.createEntityManagerFactory("ExemploJPA");
        EntityManager entityManager = entityManagerFac.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(professor);
        entityManager.getTransaction().commit();
        entityManager.close();

        return professor;
    }
}
