package DOMAIN;

import ANOTATIONS.ColunaTabela;
import ANOTATIONS.Tabela;
import ANOTATIONS.TypeKey;
import GENERICS.Persistence;
import javax.swing.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Tabela(value = "tb_Vendas")
public class Venda implements Persistence {

    public enum Status {
        INICIADA,CONCLUIDA,CANCELADA;

        public static Status getStatusByName(String valor){
            for (Status st: Status.values())
                if (st.name().equals(valor)) return st;
            return null;
        }
    }
    
    @ColunaTabela(db_nome = "id",java_nomeSet = "setID")
    private Long id;

    private Set<ProdutoQuantidade> produtosQtd = new HashSet<>();

    @ColunaTabela(db_nome = "Valor_totalVenda",java_nomeSet = "setValorTotalVenda")
    private BigDecimal valorTotalVenda;

    @ColunaTabela(db_nome = "Codigo",java_nomeSet = "setCodigo")
    @TypeKey(value = "getCodigo")
    private String codigo;

    @ColunaTabela(db_nome = "id_cliente_fk",java_nomeSet = "setCliente")
    private Cliente cliente;

    @ColunaTabela(db_nome = "Data_Venda",java_nomeSet = "setDataVenda")
    private Instant dataVenda;

    @ColunaTabela(db_nome = "Status",java_nomeSet = "setStatus")
    private Status status;

    private final Set<ProdutoQuantidade> newProdutos = new HashSet<>();

    public Venda() {}

    public Set<ProdutoQuantidade> getProdutosQtd() {
        return produtosQtd;
    }

    public BigDecimal getValorTotal() {
        return valorTotalVenda;
    }

    public void setValorTotalVenda(BigDecimal valorTotal) {
        this.valorTotalVenda = valorTotal;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setProdutosQtd(Set<ProdutoQuantidade> produtos) {
        this.produtosQtd = produtos;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Instant getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Instant dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Status getStatus() {
        return status;
    }

    public Set<ProdutoQuantidade> getNewProdutos() {
        return newProdutos;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    @Override
    public Long getID() {
        return this.id;
    }

    @Override
    public void setID(Long id) {
        this.id=id;
    }

    public void adicionarProduto(Produto produtoC, Integer quantidade) {
        validaStatus();
        Optional<ProdutoQuantidade> any = this.produtosQtd.stream().filter(s -> s.getProduto().getCodigo().equals(produtoC.getCodigo())).findAny();
        if (any.isPresent()){
            ProdutoQuantidade produtoQuantidade = any.get();
            JOptionPane.showMessageDialog(null,"Produto localizado! Alterando sua quantidade...");
            produtoQuantidade.adicionarQuantidade(quantidade);
        } else {
            ProdutoQuantidade produtoQuantidadeNEW = new ProdutoQuantidade();
            JOptionPane.showMessageDialog(null,"Adicionando produto...");
            produtoQuantidadeNEW.setProduto(produtoC);
            produtoQuantidadeNEW.setCodigo(produtoC.getCodigo());
            produtoQuantidadeNEW.setQuantidade(quantidade);
            produtoQuantidadeNEW.recalculaTotal();
            this.produtosQtd.add(produtoQuantidadeNEW);
            this.newProdutos.add(produtoQuantidadeNEW);
        }

        recalcularTotalVenda();
    }

    public void subtrairProduto(String codigo, int quantidade) {
        validaStatus();
        Optional<ProdutoQuantidade> any = this.getProdutosQtd().stream().filter(s -> s.getProduto().getCodigo().equals(codigo)).findAny();
        if (any.isPresent()){
            ProdutoQuantidade produtoQuantidade = any.get();
            if (produtoQuantidade.getQuantidade() >= quantidade) {
                JOptionPane.showMessageDialog(null,"Subtraindo quantidade...");
                produtoQuantidade.subtrairQuantidade(quantidade);
            } else JOptionPane.showMessageDialog(null,"A quantidade informada excede a disponível. ");
        }
        recalcularTotalVenda();
    }

    public void removerTodos(){
        validaStatus();
        this.produtosQtd.clear();
        this.valorTotalVenda = BigDecimal.ZERO;
    }

    public Integer getQuantidadeTotalProdutos() {
        // Soma a quantidade getQuantidade() de todos os objetos ProdutoQuantidade
        return produtosQtd.stream()
                .reduce(0, (partialCountResult, prod) -> partialCountResult + prod.getQuantidade(), Integer::sum);
    }


    private void recalcularTotalVenda() {

        BigDecimal totValue = BigDecimal.ZERO;

        for ( ProdutoQuantidade produtoQuantidade: produtosQtd){
            totValue = totValue.add(produtoQuantidade.getValorTotal());
        }

        this.valorTotalVenda = totValue;
    }


    public void validaStatus(){

        String messege = (this.status== Status.CONCLUIDA) ? "Não é possível alterar uma venda já finalizada." : "Não é possível alterar uma venda cancelada.";
        if (this.status== Status.CONCLUIDA || this.status==Status.CANCELADA) {
            throw new UnsupportedOperationException(messege);
        }
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", produtosQtd=" + produtosQtd +
                ", valorTotalVenda=" + valorTotalVenda +
                ", codigo='" + codigo + '\'' +
                ", cliente=" + cliente +
                ", dataVenda=" + dataVenda +
                ", status=" + status;
    }
}
