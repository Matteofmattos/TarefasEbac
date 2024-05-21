package domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_CARRO")
public class Carro {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="carro_seq")
    @SequenceGenerator(name="carro_seq", sequenceName="sq_carro", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "CODIGO", length = 10, nullable = false, unique = true)
    private String codigo;

    @Column(name = "ANO_FABRIC", length = 10, nullable = false)
    private String ano;

    @Column(name = "MODELO", length = 20, nullable = false)
    private String modelo;

    @Column(name = "COR", length = 10, nullable = false)
    private String cor;

    @ManyToOne
    @JoinColumn(name = "marcas_fk",foreignKey = @ForeignKey(name = "fk_marca")
            ,referencedColumnName ="id",nullable = false)
    private Marca marca;

    @ManyToMany(mappedBy = "carros")
    private List<Acessorio> acessorios;

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Carro() {
        this.acessorios = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public List<Acessorio> getAcessorios() {
        return acessorios;
    }

    public void setAcessorios(List<Acessorio> acessorios) {
        this.acessorios = acessorios;
    }

    public void addAcessorios(Acessorio acessorio) {
        this.acessorios.add(acessorio);
    }
}
