package DAO;

import DOMAIN.Cliente;
import GENERICS.IClienteDao;
import java.sql.PreparedStatement;
import java.time.format.SignStyle;

import GENERICS.*;

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
        return "INSERT INTO tb_Clientes (ID, NOME, CPF, TEL, ENDERECO, NUMERO, CIDADE, ESTADO) "+
                "VALUES (nextval('sq_cliente'),?,?,?,?,?,?,?)";
    }

    @Override
    protected String getQueryExclusion() {
        return "DELETE FROM tb_Clientes WHERE CPF = ?";
    }
    
    @Override
    protected String getQueryUpdate() {
        return "UPDATE tb_Clientes SET NOME=?, CPF=?, TEL=?, ENDERECO=?, NUMERO=?, CIDADE=?, ESTADO=? WHERE CPF = ?";
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
}

