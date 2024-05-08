package domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_professor")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "prof_seq")
    @SequenceGenerator(name = "prof_seq",sequenceName = "seq_prof",initialValue = 1,allocationSize = 1)
    private Long id;

    @Column(name = "nome",nullable = false,length=20)
    private String nome;

    @Column(name = "RA",unique = true,nullable = false,length=15)
    private String ra;

    @ManyToMany(mappedBy = "professores")
    private List<Disciplina> disciplinas;

    public Professor() {
        this.disciplinas = new ArrayList<>();
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
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

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public void addDisciplinas(Disciplina disciplina){
        this.disciplinas.add(disciplina);
    }
}
