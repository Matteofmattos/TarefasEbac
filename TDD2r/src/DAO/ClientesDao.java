package DAO;

import DOMAIN.Cliente;
import EXCEPTIONS.DaoException;
import GENERICS.IClienteDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.SignStyle;
import java.util.Locale;

import GENERICS.*;
import JDBC.ConnectionFactory;

import javax.swing.*;

public class ClientesDao extends GenericDao<Cliente> implements IClienteDao {

    public ClientesDao() {
        super();
    }

    @Override
    public Class<Cliente> getTipoClasse() {
        return Cliente.class;
    }

    @Override
    protected String getQueryInsertion() {
        return "INSERT INTO tb_Clientes (ID, NOME, CPF, TEL, ENDERECO, NUMERO, CIDADE, ESTADO,SITUACAO) "+
                "VALUES (nextval('sq_cliente'),?,?,?,?,?,?,?,?)";
    }

    @Override
    protected String getQueryExclusion() {
        return "DELETE FROM tb_Clientes WHERE CPF = ?";
    }
    
    @Override
    protected String getQueryUpdate() {
        return "UPDATE tb_Clientes SET NOME=?, CPF=?, TEL=?, ENDERECO=?, NUMERO=?, CIDADE=?, ESTADO=? WHERE CPF = ?,SITUACAO =?";
    }

    @Override
    protected void setValuesQueryInsertion(PreparedStatement st, Cliente entity)  {
        try {
            st.setString(1, entity.getNome());
            st.setString(2, entity.getCpf());
            st.setString(3, entity.getTel());
            st.setString(4, entity.getEnd());
            st.setString(5, entity.getNumero());
            st.setString(6, entity.getCidade());
            st.setString(7, entity.getEstado());
            st.setString(8,entity.getSituacao().name());

        } catch ( Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void setValuesQueryExclusion(PreparedStatement st, String codigo)  {
        try {
            st.setString(1,codigo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void setValuesQuerySelection(PreparedStatement st, String codigo) {
        try {
            st.setString(1,codigo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    protected void setValuesQueryUpdate(PreparedStatement st, Cliente entity) {
        try {
            st.setString(1,entity.getNome());
            st.setString(2,entity.getCpf());
            st.setString(3,entity.getTel());
            st.setString(4,entity.getEnd());
            st.setString(5,entity.getNumero());
            st.setString(6,entity.getCidade());
            st.setString(7,entity.getEstado());
            st.setString(8,entity.getCpf());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void alterarSituacaoCliente(String cpf,String situacao) throws DaoException {

        Cliente.Situacao situacaoByName = Cliente.Situacao.getSituacaoByName(situacao.toUpperCase().trim());

        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = connection.prepareStatement("UPDATE tb_clientes set situacao=? WHERE cpf =?");
            st.setString(1, String.valueOf(situacaoByName));
            st.setString(2,cpf);
            if (st.executeUpdate()>0){

                JOptionPane.showMessageDialog(null,"Situação do cliente atualizada!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Erro: "+e.getMessage());
            throw new DaoException();
        } finally {
            closeConnection(connection,st,null);
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
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }
}

