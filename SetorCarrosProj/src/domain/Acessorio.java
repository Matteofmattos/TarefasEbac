package domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_ACESSORIO")
public class Acessorio {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="carro_seq")
    @SequenceGenerator(name="acc_seq", sequenceName="sq_acc", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "CODIGO", length = 10, nullable = false, unique = true)
    private String codigo;

    @Column(name = "DESCRICAO", length = 50, nullable = false)
    private String descricao;

    @Column(name = "COR", length = 10, nullable = false)
    private String cor;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tb_acessorio_marca",joinColumns = { @JoinColumn(name = "acessorio_fk")},
            inverseJoinColumns={@JoinColumn(name = "marca_fk")})
    private List<Marca> marcas;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tb_acessorio_carro",joinColumns = { @JoinColumn(name = "acessorio_fk")},
            inverseJoinColumns={@JoinColumn(name = "carro_fk")})
    private List<Carro> carros;

    public Acessorio() {
        this.carros = new ArrayList<>();
        this.marcas = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public List<Marca> getMarcas() {
        return marcas;
    }

    public void setMarcas(List<Marca> marcas) {
        this.marcas = marcas;
    }

    public List<Carro> getCarros() {
        return carros;
    }

    public void setCarros(List<Carro> carros) {
        this.carros = carros;
    }

    public void addMarca(Marca marca) {
        this.marcas.add(marca);
    }

    public void addCarro(Carro carro) {
        this.carros.add(carro);
    }
}
