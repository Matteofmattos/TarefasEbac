package FACTORY;

import GENERICS.Persistence;
import DOMAIN.Produto;

import javax.swing.*;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoFactory implements PersistenceFactory {

    public static Produto convert(ResultSet resultSet) {
        Produto produto = new Produto();
        try {
            produto.setID(resultSet.getLong("id_PRODUTO"));
            produto.setNome(resultSet.getString("nome"));
            produto.setValor(resultSet.getBigDecimal("valor_unitario"));
            produto.setCodigo(resultSet.getString("codigo_produto"));
            produto.setDescricao(resultSet.getString("descricao"));
            produto.setValidade(resultSet.getString("validade"));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
            System.out.println(e.getErrorCode());
        }

        return produto;
    }

    @Override
    public Persistence criaObjeto() {
        String nome = JOptionPane.showInputDialog("Informe o nome do produto: ").trim();
        String codigo = JOptionPane.showInputDialog("Informe o codigo do produto: ").trim();
        String valor = JOptionPane.showInputDialog("Informe o valor do produto ").trim();
        String descricao = JOptionPane.showInputDialog("Informe a descrição do produto ").trim();
        BigDecimal valorDecimal = BigDecimal.valueOf(Long.parseLong(valor));
        String validade = JOptionPane.showInputDialog(null,"Informe a data de validade: ").trim();

        return new Produto(nome,codigo,descricao,valorDecimal,validade);
    }

}
