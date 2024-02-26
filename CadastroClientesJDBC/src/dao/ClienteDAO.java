package dao;

import domain.Cliente;
import jdbc.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO implements IClienteDao{
    @Override
    public Integer cadastrar(Cliente cliente) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = ConnectionFactory.getConnection();
            String sql = getSqlInsert(); // COMANDOS DE INSERÇÃO PARA O DATABASE;
            statement = connection.prepareStatement(sql);
            adicionarParametrosInsert(statement,cliente);
            return statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(connection,statement,null);
        }
    }

    private void closeConnection(Connection connection, PreparedStatement statement, ResultSet rs) {
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


    private void adicionarParametrosInsert(PreparedStatement statement, Cliente cliente) throws SQLException {
        statement.setString(1,cliente.getNome());
        statement.setString(2, cliente.getCodigo());
    }


    private String getSqlInsert() {
        StringBuilder str = new StringBuilder();
        str.append("Insert into tb_cliente (id,nome,codigo)");
        str.append(" values (nextval('SQ_cliente'),?,?)");
        return str.toString();
    }

    @Override
    public Integer atualizar(Cliente cliente) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = ConnectionFactory.getConnection();
            String sql = getSqlUpdate(); // COMANDOS DE ATUALIZAÇÃO PARA O DATABASE;
            statement = connection.prepareStatement(sql);
            adicionarParametrosUpdate(statement,cliente);
            return statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(connection,statement,null);
        }
    }

    private void adicionarParametrosUpdate(PreparedStatement statement, Cliente cliente) throws SQLException {
        statement.setString(1,cliente.getNome());
        statement.setString(2,cliente.getCodigo());
        statement.setLong(3,cliente.getId());
    }

    private String getSqlUpdate() {
        StringBuilder str = new StringBuilder();
        str.append("update tb_cliente");
        str.append(" set nome = ?, codigo = ?");
        str.append(" where id = ?");
        return str.toString();
    }

    @Override
    public Cliente buscar(String codigo) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs;
        Cliente cliente = null;

        try{
            connection = ConnectionFactory.getConnection();
            String sql = getSqlBuscar();
            statement = connection.prepareStatement(sql);
            adicionarParametrosBuscar(statement,codigo);
            rs = statement.executeQuery();

            if (rs.next()){  //Verifica se houve um retorno do database;
                cliente = new Cliente();
                Long id = rs.getLong("id");
                String nome = rs.getString("nome");
                String codig = rs.getString("codigo");
                cliente.setId(id);
                cliente.setNome(nome);
                cliente.setCodigo(codig);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(connection,statement,null);
        }
        return cliente;
    }

    private String getSqlBuscar() {
        StringBuilder str = new StringBuilder();
        str.append("select * from tb_cliente");
        str.append(" where codigo = ?");
        return str.toString();
    }

    private void adicionarParametrosBuscar(PreparedStatement statement, String codigo) throws SQLException {
        statement.setString(1,codigo);
    }

    @Override
    public List<Cliente> buscarTodos() {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Cliente cliente = null;
        List<Cliente> clientes = new ArrayList<>();

        try{
            connection = ConnectionFactory.getConnection();
            String sql = getSqlBuscarTodos(); // COMANDOS DE INSERÇÃO PARA O DATABASE;
            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery();

            while (rs.next()){
                cliente = new Cliente();
                Long id = rs.getLong("id");
                String nome = rs.getString("nome");
                String codig = rs.getString("codigo");
                cliente.setId(id);
                cliente.setNome(nome);
                cliente.setCodigo(codig);
                clientes.add(cliente);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(connection,statement,null);
        }
        return clientes;
    }

    private String getSqlBuscarTodos() {
        StringBuilder str = new StringBuilder();
        str.append("select * from tb_cliente");
        return str.toString();
    }

    @Override
    public Integer excluir(Cliente cliente) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = ConnectionFactory.getConnection();
            String sql = getSqlDelete(); // COMANDOS DE INSERÇÃO PARA O DATABASE;
            statement = connection.prepareStatement(sql);
            adicionarParametrosExcluir(statement,cliente);
            return statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(connection,statement,null);
        }
    }

    private void adicionarParametrosExcluir(PreparedStatement statement, Cliente cliente) throws SQLException {
        statement.setString(1,cliente.getCodigo());
    }

    private String getSqlDelete() {
        StringBuilder str = new StringBuilder();
        str.append("delete from tb_cliente");
        str.append(" where codigo = ?");
        return str.toString();
    }
}
