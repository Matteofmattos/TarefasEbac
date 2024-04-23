package DOMAIN;

import ANOTATIONS.ColunaTabela;
import ANOTATIONS.Tabela;
import javax.swing.*;
import java.math.BigDecimal;

@Tabela(value = "tb_produto_quantidade")
public class ProdutoQuantidade {
    @ColunaTabela(db_nome = "id", java_nomeSet = "setId")
    private Long id;
    private Produto produto;
    @ColunaTabela(db_nome = "quantidade", java_nomeSet = "setQuantidade")
    private Integer quantidade;
    @ColunaTabela(db_nome = "valor_total", java_nomeSet = "setValorTotal")
    private BigDecimal valorTotal = BigDecimal.ZERO;
    private String codigo;

    public ProdutoQuantidade() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorTotal() {
        this.valorTotal=  this.getProduto().getValor().multiply(BigDecimal.valueOf(this.quantidade));
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void adicionarQuantidade(Integer quantidade) {
        this.quantidade += quantidade;
        recalculaTotal();
    }

    public void subtrairQuantidade(Integer quantidade){
        this.quantidade-=quantidade;
        recalculaTotal();
    }

    public void recalculaTotal() {
        this.valorTotal = this.produto.getValor().multiply(BigDecimal.valueOf(this.quantidade));
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {

        return "id: " + id +
                ",produto: " + produto +
                ",quantidade: " + quantidade +
                ",valorTotal: " + valorTotal;
    }
}
