package domain;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "tb_disciplina")
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "disc_seq")
    @SequenceGenerator(name = "disc_seq",sequenceName = "seq_disciplina",allocationSize = 1,initialValue = 1)
    private Long id;

    @Column(name = "nome",nullable = false)
    private String nome;

    @Column(name = "codigo",nullable = false)
    private String codigo;

    @Column(name = "carga_horaria",nullable = false)
    private Integer cargaHoraria;

    @ManyToMany
    @JoinTable(name = "tb_professor_professor",joinColumns = {@JoinColumn(name = "id_professor_fk")},
    inverseJoinColumns={@JoinColumn(name = "id_professor_fk")})
    private List<Professor> professores;

    public Disciplina() {
        this.professores = new ArrayList<>();
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public List<Professor> getProfessores() {
        return professores;
    }

    public void setProfessores(List<Professor> professores) {
        this.professores = professores;
    }

    public void addProfessor(Professor prof ){
        this.professores.add(prof);
    }
}
