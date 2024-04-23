package FACTORY;

import DOMAIN.ProdutoQuantidade;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoQuantidadeFactory{
    public static ProdutoQuantidade convert(ResultSet resultSet) {
        ProdutoQuantidade produtoQuantidade = new ProdutoQuantidade();
        try {
            produtoQuantidade.setId(resultSet.getLong("id_produto_quantidade"));
            produtoQuantidade.setQuantidade(resultSet.getInt("quantidade"));
            produtoQuantidade.setValorTotal(resultSet.getBigDecimal("valor_total"));
            produtoQuantidade.setProduto(ProdutoFactory.convert(resultSet));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return produtoQuantidade;
    }
}



