package Dao;

import domain.Acessorio;
import domain.Carro;
import domain.Marca;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class AcessorioDao {

    public Acessorio buscarAcessorio(String CodigoAcessorio){

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("ExemploJPA");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        StringBuilder st = new StringBuilder();
        st.append("SELECT a FROM Acessorio a WHERE a.codigo=:codigoAcessorio");

        TypedQuery<Acessorio> query = entityManager.createQuery(st.toString(), Acessorio.class);
        query.setParameter("codigoAcessorio", CodigoAcessorio);

        Acessorio acessorio = query.getSingleResult();

        entityManager.getTransaction();
        entityManager.close();

        return acessorio;
    }

    public Acessorio cadastrar(Acessorio acessorio) {

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("ExemploJPA");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(acessorio);
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();

        return acessorio;
    }

    public Acessorio consultar_porCodigoMarca(String codigoMarca){

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ExemploJPA");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT m FROM Acessorio m ");
        sql.append("INNER JOIN Marca c on c = m.marca ");
        sql.append("WHERE c.codigo = :codigo");

        entityManager.getTransaction().begin();
        TypedQuery<Acessorio> query =
                entityManager.createQuery(sql.toString(), Acessorio.class);

        query.setParameter("codigo", codigoMarca);
        Acessorio acessorio = query.getSingleResult();

        entityManager.close();
        entityManagerFactory.close();
        return acessorio;
    }

    public List<Acessorio> consultarPor_idCarro(long idCarro) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ExemploJPA");

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Carro carro = em.find(Carro.class,idCarro);

        List<Acessorio> acessorios = em.createQuery(

                        "SELECT a FROM Acessorio a JOIN a.carros c WHERE c = :carro",Acessorio.class)

                .setParameter("carro",carro)
                .getResultList();

        em.getTransaction().commit();
        em.close();
        emf.close();

        return acessorios;
    }

    public List<Acessorio> consultarPor_CodigoMarca(long idMarca) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ExemploJPA");

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Marca marcaC = em.find(Marca.class, idMarca);

        List<Acessorio> acessorios = em.createQuery(
                "SELECT a FROM Acessorio a " +
                        "JOIN a.marcas m where m=:marca",Acessorio.class)

                .setParameter("marca",marcaC)
                .getResultList();

        em.getTransaction().commit();
        em.close();
        emf.close();

        return acessorios;
    }
}
