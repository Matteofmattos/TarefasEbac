package domain;

import javax.persistence.*;

@Entity
@Table(name = "tb_cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "cliente_seq")
    @SequenceGenerator(name = "cliente_seq",sequenceName = "seq_cliente",initialValue = 1,allocationSize = 1)
    private Long id;

    @Column(name = "nome",nullable = false,length=20)
    private String nome;

    @Column(name = "cpf",unique = true,nullable = false,length=10)
    private String cpf;

}
