package Dao;

import com.sun.xml.bind.v2.model.core.ID;
import domain.Acessorio;
import domain.Carro;
import domain.Marca;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class CarroDao {
    
    public Carro cadastrar(Carro carro) {

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("ExemploJPA");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(carro);
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();


        return carro;
    }


    public Carro buscarPorCodigo(long idCarro) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ExemploJPA");

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Carro carro = em.find(Carro.class,idCarro);

        em.getTransaction().commit();
        em.close();
        emf.close();

        return carro;
    }

    public List<Carro> buscarCarro_PorCodigoAcessorio(long IDAcessorio) {

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("ExemploJPA");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        Acessorio acessorioC = entityManager.find(Acessorio.class, IDAcessorio);

        List<Carro> carros = entityManager.createQuery(

                        "SELECT c FROM Carro c JOIN c.acessorios a WHERE a = :acessorio", Carro.class)

                .setParameter("acessorio", acessorioC)

                .getResultList();

        entityManager.getTransaction();
        entityManager.close();

        return carros;
    }

}
