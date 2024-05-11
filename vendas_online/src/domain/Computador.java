package domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_COMPUTADOR")
public class Computador {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="computador_seq")
    @SequenceGenerator(name="computador_seq", sequenceName="sq_computador", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "CODIGO", length = 10, nullable = false, unique = true)
    private String codigo;

    @Column(name = "MODELO", length = 50, nullable = false)
    private String modelo;

    @Column(name = "DESCRICAO", length = 100, nullable = false)
    private String descricao;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tb_aluno_computador",joinColumns = { @JoinColumn(name = "computador_fk")},
            inverseJoinColumns={@JoinColumn(name = "aluno_fk")})
    private List<Aluno> alunos;

    public Computador() {
        this.alunos = new ArrayList<>();
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

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public void add(Aluno aluno) {
        this.alunos.add(aluno);
    }
}
