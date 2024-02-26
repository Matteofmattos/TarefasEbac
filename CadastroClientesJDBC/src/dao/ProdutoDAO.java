package dao;

import domain.Produto;
import jdbc.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO implements IProdutoDao {

    @Override
    public Integer cadastrar(Produto produto) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlInsert();
            statement = connection.prepareStatement(sql);
            adicionarParametrosInsert(statement,produto);
            return statement.executeUpdate();

        }catch (Exception e){
            throw  new RuntimeException(e);

        } finally {
            closeConnection(connection,statement,null);
        }
    }

    private void closeConnection(Connection connection, PreparedStatement statement, ResultSet rs) throws SQLException {

        try {

            if (connection != null && !connection.isClosed()){
                connection.close();
            }
            if (statement != null && !statement.isClosed()){
                statement.close();
            }
            if (rs != null && !rs.isClosed()){
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void adicionarParametrosInsert(PreparedStatement statement, Produto produto) throws SQLException {
        statement.setString(1, produto.getNome());
        statement.setString(2, produto.getCodigo());
    }

    private String getSqlInsert() {
        StringBuilder str = new StringBuilder();
        str.append("INSERT INTO tb_Produtos ( id,nome,codigo)");
        str.append(" VALUES (nextval('SQ_Produto'),?,?)");
        return str.toString();
    }

    @Override
    public Integer atualizar(Produto produto) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlUpdate();
            statement = connection.prepareStatement(sql);
            adicionarParametrosUpdate(statement,produto);
            return statement.executeUpdate();
        }catch (Exception e){
            throw  new RuntimeException(e);
        } finally {
            closeConnection(connection,statement,null);
        }
    }

    private void adicionarParametrosUpdate(PreparedStatement statement, Produto produto) throws SQLException {
        statement.setString(1,produto.getNome());
        statement.setString(2, produto.getCodigo());
        statement.setLong(3,produto.getId());
    }

    private String getSqlUpdate() {
        StringBuilder str = new StringBuilder();
        str.append("UPDATE tb_Produtos");
        str.append(" SET nome = ? , codigo = ? ");
        str.append(" WHERE id = ?");
        return str.toString();
    }

    @Override
    public Produto buscar(String codigo) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Produto produto = null;
        try {
           connection = ConnectionFactory.getConnection();
           String sql = getSqlBuscar();
           statement = connection.prepareStatement(sql);
           adicionarParametrosBuscar(statement,codigo);
           rs = statement.executeQuery();
                    
            if ( rs.next()){
                
               produto = new Produto();
               produto.setId(rs.getLong("id"));
               produto.setNome(rs.getString("nome"));
               produto.setCodigo(rs.getString("codigo"));
           }
           
        } catch (Exception e){
            throw new RuntimeException(e);
        } finally {
            closeConnection(connection,statement,rs);
        }
        return produto;
    }

    private void adicionarParametrosBuscar(PreparedStatement statement, String codigo) throws SQLException {
        statement.setString(1,codigo);
    }

    private String getSqlBuscar() {
        StringBuilder str = new StringBuilder();
        str.append("SELECT * FROM tb_Produtos");
        str.append(" WHERE codigo = ?");
        return str.toString();
    }

    @Override
    public List<Produto> buscarTodos() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Produto> produtos = new ArrayList<>();
        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlBuscarTodos();
            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery();
            while ( rs.next()){
                Produto produto = new Produto();
                produto.setId(rs.getLong("id"));
                produto.setNome(rs.getString("nome"));
                produto.setCodigo(rs.getString("codigo"));
                produtos.add(produto);
            }
            
        }catch (Exception e){
            throw  new RuntimeException(e);
        } finally {
            closeConnection(connection,statement,null);
        }
        return produtos;
    }

    private String getSqlBuscarTodos() {
        StringBuilder str = new StringBuilder();
        str.append("Select * from tb_Produtos");
        return str.toString();
    }

    @Override
    public Integer excluir(Produto produto) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlExcluir();
            statement = connection.prepareStatement(sql);
            adicionarParametrosExcluir(statement,produto);
            return statement.executeUpdate();

        }catch (Exception e){
            throw  new RuntimeException(e);
        } finally {
            closeConnection(connection,statement,null);
        }
    }

    private void adicionarParametrosExcluir(PreparedStatement statement, Produto produto) throws SQLException {
        statement.setString(1, produto.getCodigo());
    }

    private String getSqlExcluir() {
        StringBuilder str = new StringBuilder();
        str.append("DELETE FROM tb_Produtos");
        str.append(" WHERE codigo = ?");
        return str.toString();
    }
}
