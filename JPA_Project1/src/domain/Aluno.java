package domain;

import javax.persistence.*;

@Entity(name = "tb_aluno")
public class Aluno {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "aluno_seq")
    @SequenceGenerator(name = "aluno_seq",sequenceName = "seq_aluno",initialValue = 1,allocationSize = 1)
    private Long id;

    @Column(name = "nome",nullable = false,length=20)
    private String nome;

    @Column(name = "cpf",unique = true,nullable = false,length=15)
    private String cpf;
    
    @OneToOne(mappedBy = "aluno")
    private Matricula matricula;

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }
}
