package Dao;

import domain.Acessorio;
import domain.Marca;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class MarcaDao{

    public Marca cadastrar(Marca marca) {

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("ExemploJPA");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(marca);
        entityManager.getTransaction();

        entityManager.close();
        entityManagerFactory.close();

        return marca;
    }

    public Marca buscarMarca(long idMarca){

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("ExemploJPA");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        Marca marcaC = entityManager.find(Marca.class, idMarca);

        entityManager.getTransaction();
        entityManager.close();

        return marcaC;
    }

    public Marca buscarMarcaPorCodigoVeiculo(String codigoCarro){

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("ExemploJPA");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        StringBuilder sb = new StringBuilder();
        sb.append("select m from Marca m inner join Carro c on c.marca = m");
        sb.append(" where c.codigo = :codigoCarro");

        entityManager.getTransaction().begin();

        TypedQuery<Marca> query = entityManager.createQuery(sb.toString(), Marca.class);
        query.setParameter("codigoCarro",codigoCarro);

        entityManager.getTransaction();
        entityManager.close();

        return query.getSingleResult();
    }

    public List<Marca> buscarMarcaPorCodigoAcessorio(long IDAcessorio) {

        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("ExemploJPA");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        Acessorio acessorioC = entityManager.find(Acessorio.class, IDAcessorio);

        List<Marca> marcas = entityManager.createQuery(

                        "SELECT m FROM Marca m JOIN m.acessorios c WHERE c = :acessorio", Marca.class)

                .setParameter("acessorio", acessorioC)

                .getResultList();

        entityManager.getTransaction();
        entityManager.close();

        return marcas;
    }
}
