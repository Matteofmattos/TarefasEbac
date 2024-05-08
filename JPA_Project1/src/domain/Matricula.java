package domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "tb_matricula")
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "matricula_seq")
    @SequenceGenerator(name = "matricula_seq",sequenceName = "seq_matricula",initialValue = 1,allocationSize = 1)
    private Long id;

    @Column(name = "codigo",unique = true,nullable = false)
    private String codigo;

    @Column(name = "data",length = 10,nullable = false)
    private Instant dataMaricula;

    @Column(name = "valor",length = 10,nullable = false)
    private BigDecimal valorMatricula;

    @Column(name = "status",nullable = false)
    private String status;

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    @OneToOne
    @JoinColumn(name = "id_aluno_fk",foreignKey = @ForeignKey(name = "fk_aluno_matricula")
            , referencedColumnName = "id",nullable = false)
    private Aluno aluno;

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @ManyToOne
    @JoinColumn(name = "id_curso_fk",foreignKey = @ForeignKey(name = "fk_curso_matricula")
    ,referencedColumnName = "id",nullable = false)
    private Curso curso;

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

    public Instant getDataMaricula() {
        return dataMaricula;
    }

    public void setDataMaricula(Instant dataMaricula) {
        this.dataMaricula = dataMaricula;
    }

    public BigDecimal getValorMatricula() {
        return valorMatricula;
    }

    public void setValorMatricula(BigDecimal valorMatricula) {
        this.valorMatricula = valorMatricula;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
