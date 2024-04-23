package DOMAIN;


import ANOTATIONS.ColunaTabela;
import ANOTATIONS.Tabela;
import ANOTATIONS.TypeKey;
import GENERICS.Persistence;

import java.math.BigDecimal;

@Tabela(value = "tb_Produtos")
public class Produto implements Persistence {

    @ColunaTabela(db_nome = "id", java_nomeSet = "setID")
    private Long id;

    @TypeKey(value = "getCodigo")
    @ColunaTabela(db_nome = "codigo", java_nomeSet = "setCodigo")
    private String codigo;

    @ColunaTabela(db_nome = "nome", java_nomeSet = "setNome")
    private String nome;

    @ColunaTabela(db_nome = "descricao", java_nomeSet = "setDescricao")
    private String descricao;

    @ColunaTabela(db_nome = "valor", java_nomeSet = "setValor")
    private BigDecimal valor;

    public Produto(String codigo, String nome, String descricao, BigDecimal valor) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
    }

    public Produto( ) {

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

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    

    @Override
    public Long getID() {
        return this.id;
    }

    @Override
    public void setID(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "id: " + id +
                ", codigo: " + codigo + ", nome: " + nome + ", descricao: "
                + descricao + ", valor: " + valor;
    }
}

