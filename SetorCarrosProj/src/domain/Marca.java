package domain;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_MARCA")
public class Marca {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="marca_seq")
    @SequenceGenerator(name="marca_seq", sequenceName="sq_marca", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "NOME", length = 10, nullable = false, unique = true)
    private String nome;

    @Column(name = "PAIS_ORIGEM", length = 10, nullable = false, unique = true)
    private String paisOrgem;

    @OneToMany(mappedBy = "marca")
    private List<Carro> carros;

    @ManyToMany(mappedBy = "marcas")
    private List<Acessorio> acessorios;

    public Marca() {
        this.carros = new ArrayList<>();
        this.acessorios = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPaisOrgem() {
        return paisOrgem;
    }

    public void setPaisOrgem(String paisOrgem) {
        this.paisOrgem = paisOrgem;
    }

    public List<Carro> getCarros() {
        return carros;
    }

    public void setCarros(List<Carro> carros) {
        this.carros = carros;
    }

    public void addAcessorios(Acessorio acessorio) {
        this.acessorios.add(acessorio);
    }

    public void addCarros(Carro carro) {
        this.carros.add(carro);
    }
}
