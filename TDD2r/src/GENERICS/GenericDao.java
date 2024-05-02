package GENERICS;

import ANOTATIONS.ColunaTabela;
import ANOTATIONS.Tabela;
import ANOTATIONS.TypeKey;
import DOMAIN.Cliente;
import EXCEPTIONS.DaoException;
import EXCEPTIONS.ElementTypeNotFound_Exception;
import EXCEPTIONS.MoreThanOneRegister_Exception;
import EXCEPTIONS.TableException;
import JDBC.ConnectionFactory;

import javax.swing.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static JDBC.ConnectionFactory.getConnection;

public abstract class GenericDao<T extends Persistence> implements IGenericDao<T> {

    public GenericDao() { }
    public abstract Class<T> getTipoClasse();

    protected abstract String getQueryInsertion();
    protected abstract String getQueryExclusion();
    protected abstract String getQueryUpdate();
    protected abstract void setValuesQueryInsertion(PreparedStatement st, T entity);
    protected abstract void setValuesQueryExclusion(PreparedStatement st,String codigo) throws UnsupportedOperationException;
    protected abstract void setValuesQuerySelection(PreparedStatement st,String codigo);
    protected abstract void setValuesQueryUpdate(PreparedStatement st, T entity) throws UnsupportedOperationException;

    @Override
    public boolean cadastrar(T entity) throws DaoException, MoreThanOneRegister_Exception, TableException, ElementTypeNotFound_Exception {
        Connection connection = getConnection();
        PreparedStatement stm = null;
        try {
            stm = connection.prepareStatement(getQueryInsertion());
            setValuesQueryInsertion(stm, entity);
            if(stm.executeUpdate() > 0) {
                try (ResultSet rs = stm.getGeneratedKeys()){
                    if (rs.next()) {
                        entity.setID(rs.getLong(1));
                        JOptionPane.showMessageDialog(null,"ID gerado:"+rs.getString(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        } finally {
            closeConnection(connection,stm,null);
        }

        return false;
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

    @Override
    public T consultar(String codigo) throws DaoException, TableException, MoreThanOneRegister_Exception, ElementTypeNotFound_Exception {

        try {
            validarMaisDeUmRegistro(codigo);
            Connection connection = getConnection();
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM " + getTableNome() + " WHERE " + getNomeCampoChave(getTipoClasse()) + " = ?");
            setValuesQuerySelection(stm, codigo);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                T entity = getTipoClasse().getConstructor(null).newInstance(null); /////
                Field[] fields = entity.getClass().getDeclaredFields();

                for (Field field : fields) {

                    if (field.isAnnotationPresent(ColunaTabela.class)) {
                        ColunaTabela coluna = field.getAnnotation(ColunaTabela.class);
                        String dbName = coluna.db_nome();
                        String javaSetName = coluna.java_nomeSet();
                        Class<?> classField = field.getType();

                        try {
                            Method method = entity.getClass().getMethod(javaSetName, classField);
                            setValuesByType( entity,  method,  rs,  dbName,  classField.getName());

                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                                 ElementTypeNotFound_Exception e) {
                            throw new DaoException("ERRO CONSULTANDO OBJETO ", e);
                        }
                    }
                }

                return entity;
            }

        } catch (SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            throw new DaoException("ERRO CONSULTANDO OBJETO ");
        }
        return null;

    }

    @Override
    public boolean excluir(String codigo) throws DaoException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(getQueryExclusion());
            setValuesQueryExclusion(statement,codigo);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("Erro ao excluir objeto: "+e.getMessage());
        } finally {
            closeConnection(connection,statement,null);
        }
    }

    @Override
    public boolean alterar(T entity) throws DaoException {
        Connection connection = ConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getQueryUpdate());
            setValuesQueryUpdate(preparedStatement,entity);
            if (preparedStatement.executeUpdate()> 0){
                return true;
            }
        } catch (SQLException e) {
            throw new DaoException("Erro ao alterar objeto: "+ e.getCause());
        }
        return false;
    }

    @Override
    public List<T> buscarTodos() throws DaoException {

        List<Persistence> listagem = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM "+getTableNome());
            resultSet = statement.executeQuery();

            while (resultSet.next()){
                Persistence persistence = getTipoClasse().getConstructor((Class<?>) null).newInstance((Object) null);//
                Field[] fields = persistence.getClass().getDeclaredFields();
                for ( Field field: fields){

                    if ( field.isAnnotationPresent(ColunaTabela.class)){
                        ColunaTabela annotation = field.getAnnotation(ColunaTabela.class);
                        String dataBase_nomeColuna = annotation.db_nome();
                        String javaSetMethod = annotation.java_nomeSet();
                        String fieldType = field.getType().getTypeName();
                        Method method = persistence.getClass().getMethod(javaSetMethod, field.getType());
                        setValuesByType(persistence,method,resultSet,dataBase_nomeColuna,fieldType);
                    }
                }

                listagem.add(persistence);
            }

        } catch ( Exception e) {
            throw new DaoException("ERRO LISTANDO OBJETOS ", e);
        } finally {
            closeConnection(connection,statement,resultSet);
        }

        return (List<T>) listagem;
    }

    private void setValuesByType(Persistence persistence, Method method, ResultSet resultSet, String dataBaseNomeColuna, String fieldType) throws SQLException, InvocationTargetException, IllegalAccessException, ElementTypeNotFound_Exception {

        if (fieldType.equals(Integer.class.getTypeName())) {
            int val = resultSet.getInt(dataBaseNomeColuna);
            method.invoke(persistence,val);
        } else if (fieldType.equals(Long.class.getTypeName())) {
            long val = resultSet.getLong(dataBaseNomeColuna);
            method.invoke(persistence,val);
        } else if (fieldType.equals(Double.class.getTypeName())) {
            double val = resultSet.getDouble(dataBaseNomeColuna);
            method.invoke(persistence,val);
        } else if (fieldType.equals(Short.class.getTypeName())) {
            short val = resultSet.getShort(dataBaseNomeColuna);
            method.invoke(persistence,val);
        } else if (fieldType.equals(BigDecimal.class.getTypeName())) {
            BigDecimal val = resultSet.getBigDecimal(dataBaseNomeColuna);
            method.invoke(persistence,val);
        } else if (fieldType.equals(String.class.getTypeName())) {
            String val = resultSet.getString(dataBaseNomeColuna);
            method.invoke(persistence,val);
        } else if (fieldType.equals(Cliente.Situacao.class.getTypeName())) {
            String val = resultSet.getString(dataBaseNomeColuna);
            method.invoke(persistence, Cliente.Situacao.valueOf(val));
        } else {
            throw new ElementTypeNotFound_Exception("Elemento não conhecido.");
        }
    }

    public String getTableNome() throws TableException {

        if (getTipoClasse().isAnnotationPresent(Tabela.class)) {
            return getTipoClasse().getAnnotation(Tabela.class).value();
        } else throw new TableException("Tabela não encontrada! ");
    }

    private void validarMaisDeUmRegistro(String valor) throws DaoException, TableException, MoreThanOneRegister_Exception {

        Connection connection = getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        Long count = null;

        try {
            stm = connection.prepareStatement("SELECT count(*) FROM " + getTableNome() + " WHERE " + getNomeCampoChave(getTipoClasse()) + " = ?");
            setValuesQuerySelection(stm, valor);
            rs = stm.executeQuery();
            if (rs.next()) {
                count = rs.getLong(1);
                if (count > 1) throw new MoreThanOneRegister_Exception("Erro! Há registros com chave duplicada. ");
            }

        } catch (SQLException e) {
            throw new DaoException(e.getMessage(),e);
        } finally { closeConnection(connection, stm, rs); }
    }

    private String getNomeCampoChave(Class<T> tipoClasse) {

        Field[] fields = getTipoClasse().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(TypeKey.class)
                    && field.isAnnotationPresent(ColunaTabela.class)) {
                ColunaTabela coluna = field.getAnnotation(ColunaTabela.class);
                return coluna.db_nome();
            }
        }
        return null;
    }
}
