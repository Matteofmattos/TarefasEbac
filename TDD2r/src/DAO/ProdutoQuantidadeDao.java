package DAO;

import DOMAIN.ProdutoQuantidade;
import DOMAIN.Venda;
import JDBC.ConnectionFactory;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class ProdutoQuantidadeDao {

    public static void atualizarDataBase(Venda venda) throws SQLException {

        Connection connection = ConnectionFactory.getConnection();;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        Set<ProdutoQuantidade> newProdutos = venda.getNewProdutos();

        if ( !newProdutos.isEmpty() ){
            for (ProdutoQuantidade produtoQuantidade : venda.getNewProdutos()) {
                try {
                    statement = connection.prepareStatement(getQueryProdutosQuantidadeInsertion());
                    setParametersQueryInsertion_ProdutoQuantidade(statement, venda, produtoQuantidade);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    JOptionPane.showInputDialog(null, e.getMessage());
                }
            }
            venda.getNewProdutos().clear();

        } else {

            statement = connection.prepareStatement("Select * from tb_produto_quantidade where id_venda_fk =? ");
            statement.setLong(1,venda.getID());
            resultSet = statement.executeQuery();

            while (resultSet.next()){
                long codigoProduto = resultSet.getLong(2);
                for (ProdutoQuantidade produtoQuantidade: venda.getProdutosQtd()){

                    if (produtoQuantidade.getProduto().getID().equals(codigoProduto)){
                        atualizarDadosDataBase(connection,produtoQuantidade,codigoProduto);
                        excluirProdutoQuantidadeZero(venda,connection);
                    }
                }

            }
        }

        closeConnection(connection,statement,resultSet);
    }

    private static void atualizarDadosDataBase(Connection connection, ProdutoQuantidade produtoQuantidade, long codigo) {
        PreparedStatement preparedStatement=null;

        try {
            preparedStatement = connection.prepareStatement("UPDATE tb_produto_quantidade SET quantidade =?, valor_total =? where id_produto_fk = ?");
            preparedStatement.setInt(1,produtoQuantidade.getQuantidade());
            preparedStatement.setBigDecimal(2,produtoQuantidade.getValorTotal());
            preparedStatement.setLong(3,codigo);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Ocorreu um erro ao atualizar dados no database. Erro: "+e.getMessage());
        }
    }

    private static void setParametersQueryInsertion_ProdutoQuantidade(PreparedStatement stm, Venda venda, ProdutoQuantidade prodQtd) {
        try {
            stm.setLong(1, prodQtd.getProduto().getID());
            stm.setLong(2, venda.getID());
            stm.setInt(3, prodQtd.getQuantidade());
            stm.setBigDecimal(4, prodQtd.getValorTotal());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage()); //
        }
    }

    private static String getQueryProdutosQuantidadeInsertion() {
        return "INSERT INTO tb_produto_quantidade (ID, ID_PRODUTO_FK, ID_VENDA_FK, QUANTIDADE, VALOR_TOTAL)"+
                "VALUES (nextval('sq_produto_quantidade'),?,?,?,?)";
    }

    public static boolean removerTodosDados(Venda vendaC) {

        String sql =  "Delete from tb_produto_quantidade where id_venda_fk = ?";
        Connection connection = ConnectionFactory.getConnection();;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1,vendaC.getID());
            int i = statement.executeUpdate();
            if (i>0){
                JOptionPane.showMessageDialog(null,"Todos os dados foram removidos com êxito.");
                return true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Ocorreu um erro ao excluir os dados: "+e.getMessage());
        }
        closeConnection(connection,statement,null);
        return false;
    }

    private static void excluirProdutoQuantidadeZero(Venda venda, Connection connection){

        for (ProdutoQuantidade produtoQuantidade: venda.getProdutosQtd()){
            if (produtoQuantidade.getQuantidade().equals(0)){
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement("delete from tb_produto_quantidade where quantidade=? ");
                    preparedStatement.setInt(1,0);
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null,"Erro: "+e.getMessage());
                }
            }
        }
    }

    private static void closeConnection(Connection connection, PreparedStatement statement, ResultSet rs) {
        try {
            if (rs!=null && !rs.isClosed()){
                rs.close();
            }
            if (statement!=null && !statement.isClosed()){
                statement.close();
            }
            if (connection!=null && !connection.isClosed()){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
