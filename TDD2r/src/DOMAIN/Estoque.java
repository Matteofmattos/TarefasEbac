package DOMAIN;

import ANOTATIONS.ColunaTabela;
import ANOTATIONS.Tabela;
import ANOTATIONS.TypeKey;
import GENERICS.Persistence;

import java.math.BigDecimal;

@Tabela(value = "tb_estoque")
public class Estoque implements Persistence {

    @ColunaTabela(db_nome = "id", java_nomeSet = "setID")
    private Long id;

    @TypeKey(value = "getCodigo")
    @ColunaTabela(db_nome = "codigo", java_nomeSet = "setCodigoProduto")
    private String codigoProduto;

    @ColunaTabela(db_nome = "nome", java_nomeSet = "setNomeProduto")
    private String nomeProduto;

    @ColunaTabela(db_nome = "descricao", java_nomeSet = "setDescricaoProduto")
    private String descricaoProduto;

    @ColunaTabela(db_nome = "valor", java_nomeSet = "setValorProduto")
    private BigDecimal valorProduto;

    @ColunaTabela(db_nome = "quantidade", java_nomeSet = "setQuantidade")
    private Integer quantidade;

    public Estoque() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public BigDecimal getValorProduto() {
        return valorProduto;
    }

    public void setValorProduto(BigDecimal valorProduto) {
        this.valorProduto = valorProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
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
        return "id:" + id +
                ", codigoProduto: " + codigoProduto + ", nomeProduto: " + nomeProduto + ", descricaoProduto: " + descricaoProduto +
                ", valorProduto: " + valorProduto +
                ", quantidade: " + quantidade;
    }
}
