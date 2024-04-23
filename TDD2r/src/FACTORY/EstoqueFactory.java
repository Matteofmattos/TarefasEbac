package FACTORY;

import DOMAIN.Cliente;
import DOMAIN.Estoque;
import GENERICS.IEstoqueDao;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EstoqueFactory extends Factory {

    public static Estoque convert(ResultSet resultSet) {
        Estoque estoque = new Estoque();
        try {
            estoque.setID(resultSet.getLong("id"));
            estoque.setNomeProduto(resultSet.getString("nome"));
            estoque.setValorProduto(resultSet.getBigDecimal("valor"));
            estoque.setCodigoProduto(resultSet.getString("codigo"));
            estoque.setDescricaoProduto(resultSet.getString("descricao"));
            estoque.setQuantidade(resultSet.getInt("quantidade"));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
            System.out.println(e.getErrorCode());
        }

        return estoque;
    }
    
}
