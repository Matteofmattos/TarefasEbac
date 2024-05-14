package domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_ALUNO")
public class Aluno {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="aluno_seq")
    @SequenceGenerator(name="aluno_seq", sequenceName="sq_aluno", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "CODIGO", length = 10, nullable = false, unique = true)
    private String codigo;

    @Column(name = "NOME", length = 10, nullable = false)
    private String nome;

    @OneToOne(mappedBy = "aluno")
    private Matricula matricula;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tb_aluno_computador",joinColumns = { @JoinColumn(name = "aluno_fk")},
            inverseJoinColumns={@JoinColumn(name = "computador_fk")})
    private List<Computador> computadores;

    public Aluno() {
        this.computadores = new ArrayList<>();
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matriculas) {
        this.matricula = matriculas;
    }

    public List<Computador> getComputadores() {
        return computadores;
    }

    public void setComputadores(List<Computador> computadores) {
        this.computadores = computadores;
    }

    public void add(Computador computador) {
        this.computadores.add(computador);
    }

}
