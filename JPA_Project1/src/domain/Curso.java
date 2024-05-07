package domain;

import javax.persistence.*;

@Entity
@Table(name = "tb_curso")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "curso_seq")
    @SequenceGenerator(name = "curso_seq",sequenceName = "seq_curso",initialValue = 1,allocationSize = 1)
    private Long id;

    @Column(name = "codigo",length = 10,nullable = false,unique = true)
    private String codigo;

    @Column(name = "nome",length = 20,nullable = false)
    private String nome;

    @Column(name = "descricao",length = 30)
    private String descricao;

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
