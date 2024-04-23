package DAO;

import DOMAIN.Estoque;
import EXCEPTIONS.DaoException;
import EXCEPTIONS.ElementTypeNotFound_Exception;
import EXCEPTIONS.MoreThanOneRegister_Exception;
import EXCEPTIONS.TableException;
import GENERICS.GenericDao;
import GENERICS.IEstoqueDao;
import JDBC.ConnectionFactory;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EstoqueDao extends GenericDao<Estoque> implements IEstoqueDao {

    @Override
    public Class<Estoque> getTipoClasse() {
        return Estoque.class;
    }

    @Override
    protected String getQueryInsertion() {
        return "INSERT INTO tb_estoque (ID,CODIGO,NOME,DESCRICAO,VALOR,QUANTIDADE) "+
                "VALUES (nextval('sq_idestoque'),?,?,?,?,?)";
    }

    @Override
    protected String getQueryExclusion() {
        return "DELETE FROM tb_estoque WHERE CODIGO = ?";
    }

    @Override
    protected String getQueryUpdate() {
        return "UPDATE tb_estoque SET QUANTIDADE = ? WHERE CODIGO = ?";
    }

    @Override
    protected void setValuesQueryInsertion(PreparedStatement st, Estoque entity) {
        try{
            st.setString(1,entity.getCodigoProduto());
            st.setString(2,entity.getNomeProduto());
            st.setString(3,entity.getDescricaoProduto());
            st.setBigDecimal(4,entity.getValorProduto());
            st.setInt(5,entity.getQuantidade());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public boolean subtrairQuantidade(String codigoProduto,Integer quantidade) throws MoreThanOneRegister_Exception, TableException, ElementTypeNotFound_Exception, DaoException, SQLException {

        Estoque estoque = this.consultar(codigoProduto);

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;

        if (estoque!=null && verificarEstoque(connection,quantidade,codigoProduto)){

            JOptionPane.showMessageDialog(null,"subtraindo quantidade ao estoque...");
            try {
                estoque.setQuantidade(estoque.getQuantidade() - quantidade);
                    statement = connection.prepareStatement(getQueryUpdate());
                    setValuesQueryUpdate(statement,estoque);

                if (statement.executeUpdate()>0){
                    JOptionPane.showMessageDialog(null,"Produto atualizado em estoque!");
                    return true;
                }

            } catch (SQLException e) {
                throw new DaoException(e.getMessage());
            }

        } else JOptionPane.showMessageDialog(null,
                "A quantidade informada é superior a contida em estoque! \n\n Estoque: "+estoque.getQuantidade()+"\n Quantidade solicitada: "+quantidade);
        return false;
    }

    public boolean adicionarQuantidade(String codigoProduto,Integer quantidade) throws MoreThanOneRegister_Exception, TableException, ElementTypeNotFound_Exception, DaoException {

        Estoque estoque = this.consultar(codigoProduto);
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = null;

        if (estoque!=null ){

            JOptionPane.showMessageDialog(null,"Adicionando quantidade ao estoque...");
            try {
                estoque.setQuantidade(estoque.getQuantidade() + quantidade);
                statement = connection.prepareStatement(getQueryUpdate());
                setValuesQueryUpdate(statement,estoque);

                if (statement.executeUpdate()>0){
                    JOptionPane.showMessageDialog(null,"Produto atualizado em estoque!");
                    return true;
                }

            } catch (SQLException e) {
                throw new DaoException(e.getMessage());
            }

        } else JOptionPane.showMessageDialog(null, "Ops...este produto já se encontra cadastrado!");
        return false;
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
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }

    @Override
    public boolean cadastrar(Estoque entity) throws DaoException, MoreThanOneRegister_Exception, TableException, ElementTypeNotFound_Exception {

        Estoque consultar = this.consultar(entity.getCodigoProduto());

        if (consultar==null){
            JOptionPane.showMessageDialog(null,"Cadastrando produto no estoque...");
            return super.cadastrar(entity);

        } else throw new MoreThanOneRegister_Exception("Ops...este produto já se encontra cadastrado!");
    }

    @Override
    protected void setValuesQueryExclusion(PreparedStatement st, String codigo) throws UnsupportedOperationException {

        try {
            st.setString(1,codigo);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void setValuesQuerySelection(PreparedStatement st, String codigo) {
        try {
            st.setString(1,codigo);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void setValuesQueryUpdate(PreparedStatement st, Estoque entity) throws UnsupportedOperationException {
        try{
            st.setInt(1,entity.getQuantidade());
            st.setString(2,entity.getCodigoProduto());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public boolean removerTodosDados() {
        String sql =  "Delete from tb_estoque;";
        Connection connection = ConnectionFactory.getConnection();;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            int i = statement.executeUpdate();
            if (i>0){
                JOptionPane.showMessageDialog(null,"Todos os dados foram removidos com êxito.");
                return true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Ocorreu um erro ao excluir os dados: "+e.getMessage());
        } finally {
            closeConnection(connection,statement,null);
        }
        return false;
    }

    @Override
    public void atualizarDados(Connection connection, Estoque estoque) {
        PreparedStatement preparedStatement=null;
        try {
            preparedStatement = connection.prepareStatement("UPDATE tb_estoque SET quantidade =? where codigo = ?");
            preparedStatement.setInt(1,estoque.getQuantidade());
            preparedStatement.setString(2,estoque.getCodigoProduto());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Ocorreu um erro ao atualizar dados no database. Erro: "+e.getMessage());
        }
    }

    public boolean verificarEstoque(Connection connection, int quantidade, String codigoProduto) throws SQLException, DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT * FROM tb_estoque WHERE CODIGO = ?");
            statement.setString(1,codigoProduto);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                if (resultSet.getString(2).equals(codigoProduto) ){
                        return resultSet.getInt(6) >= quantidade;
                }
            }

        } catch (SQLException | HeadlessException e) {
            throw new DaoException("Erro de busca: "+e.getMessage());
        }
        return false;
    }
}
